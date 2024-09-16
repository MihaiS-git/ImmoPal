import { Injectable, OnDestroy } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { environment } from '../../environments/environment.prod';
import { catchError, map, Observable, of, Subject, switchMap, tap, throwError, timer } from 'rxjs';
import { ChatMessage } from '../model/chatMesage.model';
import { ChatNotification } from '../model/chatNotification.model';
import { ChatUser } from '../model/chatUser.model';
import { HttpClient } from '@angular/common/http';
import { ChatRoom } from '../model/chatRoom.model';
import { SendMessageRequest } from '../dto/sendMessageRequest.model';



@Injectable({
  providedIn: 'root'
})
export class ChatWsService implements OnDestroy {
  baseUrlUsers = 'api/chat/chatUsers';
  private baseUrlRoom = 'api/chat/chatRoom';
  private chatStompClient: any | null = null;
  private messageSubject: Subject<ChatMessage> = new Subject<ChatMessage>();
  private notificationSubject: Subject<ChatNotification> = new Subject<ChatNotification>();
  private chatUsersSubject: Subject<ChatUser> = new Subject<ChatUser>();
  private subscriptions: { [key: string]: any } = {};
  private hasSubscribed = false;
  private hasSubscribedUsers = false;
  private reconnecting = false;
  private reconnectDelay = 5000;
  private openChatRooms: Set<string> = new Set();
  currentChatRoomId = '';
  senderId = '';
  recipientId = '';
  chatId = '';
  chatRoom: ChatRoom = null;
  chatUsers: ChatUser[] = [];


  constructor(private http: HttpClient) { }

  initializeWebSocketConnection(): void {
    if (this.isConnected()) {
      return;
    }

    const chatServerUrl = environment.websocketUrls.chatWSUrl;
    const chatws = new SockJS(chatServerUrl);
    this.chatStompClient = Stomp.over(() => chatws);

    this.chatStompClient.configure({
      reconnectDelay: this.reconnectDelay,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: (frame: any) => this.onConnected(frame),
      onStompError: (frame: any) => this.onError(frame),
      onWebSocketClose: (evt: CloseEvent) => this.onWebSocketClose(evt),
      onWebSocketError: (evt: Event) => this.onWebSocketError(evt)
    });

    this.chatStompClient.activate();
  }

  private onConnected(frame: any): void {
    console.log('Connected:', frame);
    this.subscribeToNotifications().subscribe();
    this.subscribeToChatUsers();
  }

  private onError(frame: any): void {
    console.error('Broker reported error:', frame.headers['message']);
    console.error('Additional details:', frame.body);
    this.reconnect();
  }

  private onWebSocketClose(evt: CloseEvent): void {
    console.error('WebSocket closed.', evt);
    if (localStorage.getItem('userData')) {
      this.reconnect();
    }
  }

  private onWebSocketError(evt: Event): void {
    console.error('WebSocket error. Reconnecting...', evt);
    this.reconnect();
  }

  private reconnect() {
    console.log("Reinitializing WebSocket STOMP connection...");
    if (!this.reconnecting) {
      this.reconnecting = true;
      timer(this.reconnectDelay).pipe(
        switchMap(() => {
          this.initializeWebSocketConnection();
          return this.subscribeToNotifications();
        }),
        tap(() => this.reconnecting = false),
        catchError(err => {
          console.error("Reconnection error:", err);
          return of(null);
        })
      ).subscribe();
    }
  }

  isConnected(): boolean {
    return this.chatStompClient && this.chatStompClient.connected;
  }

  setChatRoomId(senderId: string, recipientId: string): Observable<string> {
    this.recipientId = recipientId;
    this.senderId = senderId;

    return this.http.get<ChatRoom>(`${this.baseUrlRoom}/${senderId}/${recipientId}`).pipe(
      map((res) => {
        this.chatId = res.chatId;
        this.chatRoom = res;
        return this.chatId;
      }),
      catchError((error) => {
        console.error('Error fetching chat room:', error);
        return of('');
      })
    );
  }

  subscribeToMessages(recipientId: string): Observable<ChatMessage> {
    if (!this.chatStompClient) return this.messageSubject.asObservable();

    if (this.subscriptions[`messages/${recipientId}`]) {
      this.subscriptions[`messages/${recipientId}`].unsubscribe();
    }

    this.subscriptions[`messages/${recipientId}`] =
      this.chatStompClient.subscribe(`/topic/chat/${recipientId}/queue/messages`, message => {
        const chatMessage = JSON.parse(message.body) as ChatMessage;
        this.messageSubject.next(chatMessage);
      });

    return this.messageSubject.asObservable();
  }

  subscribeToNotifications(): Observable<ChatNotification> {
    if (!this.isConnected()) {
      console.error('WebSocket is not connected.');
      return throwError(() => new Error('WebSocket is not connected.'));
    }

    if (this.hasSubscribed) {
      console.log('Already subscribed to notifications.');
      return this.notificationSubject.asObservable();
    }

    if (localStorage.getItem('userData')) {
      const currentUserEmail = JSON.parse(localStorage.getItem('userData')).user.email;

      return this.http.get<ChatUser[]>(`${this.baseUrlUsers}/users`).pipe(
        tap(users => {
          const validUsers = users.filter(u => u.email !== currentUserEmail);
          validUsers.forEach((chatUser) => {
            const recipientId = chatUser.email;
            if (!this.subscriptions[`notifications/${recipientId}`]) {
              this.subscriptions[`notifications/${recipientId}`] =
                this.chatStompClient.subscribe(`/topic/chat/${recipientId}/queue/notifications`, message => {
                  try {
                    const notification = JSON.parse(message.body) as ChatNotification;
                    if (notification.recipientId === currentUserEmail) {
                      this.notificationSubject.next(notification);
                    }
                  } catch (error) {
                    console.error('Error parsing notification message:', error);
                  }
                });
            }
          });
          this.hasSubscribed = true;
        }),
        catchError((error) => {
          console.error('Error fetching chat users:', error);
          return throwError(error);
        }),
        switchMap(() => this.notificationSubject.asObservable())
      );
    }
  }

  subscribeToChatUsers(): Observable<ChatUser> {
    if (!this.isConnected()) {
      console.error('WebSocket is not connected.');
      return throwError(() => new Error('WebSocket is not connected.'));
    }

    if (this.hasSubscribedUsers) {
      console.log('Already subscribed to chat users list.');
      return this.chatUsersSubject.asObservable();
    }

    return this.http.get<ChatUser[]>(`${this.baseUrlUsers}/users`).pipe(
      tap((users) => {
        users.forEach((chatUser) => {
          if (!this.subscriptions['users/updates']) {
            this.subscriptions['users/updates'] =
              this.chatStompClient.subscribe('/topic/user/updates', message => {
                try {
                  const chatUser = JSON.parse(message.body) as ChatUser;
                  this.chatUsersSubject.next(chatUser);
                } catch (error) {
                  console.error('Error parsing notification message:', error);
                }
              });
          }
        })
      }),
      catchError((error) => {
        console.error('Error fetching chat users:', error);
        return throwError(error);
      }),
      switchMap(() => {
        this.hasSubscribedUsers = true;
        return this.chatUsersSubject.asObservable();
      })
    );
  }

  isChatRoomOpen(chatId: string): Observable<boolean> {
    return of(this.openChatRooms.has(chatId));
  }

  openChatRoom(chatId: string): void {
    this.openChatRooms.add(chatId);
  }

  closeChatRoom(chatId: string): void {
    this.openChatRooms.delete(chatId);
  }

  connectUser(email: string, fullName: string, status: string, pictureUrl: string): Observable<ChatUser> {
    const connectUserRequest = { email, fullName, status, pictureUrl };
    return this.http.post<ChatUser>(`${this.baseUrlUsers}/connectUser`, connectUserRequest).pipe(
      tap(user => {
        console.log('User connected:', user);
        this.subscribeToNotifications().subscribe();
      }),
      catchError(error => {
        console.error('Error connecting user:', error);
        return throwError(error);
      })
    );
  }

  sendMessage(message: SendMessageRequest): void {
    if (!this.chatStompClient || !this.chatStompClient.connected || !message.chatId) {
      console.error('STOMP client is not connected or message chatId is missing.');
      return;
    }
    this.chatStompClient.send(`/app/sendMessage`, {}, JSON.stringify(message));
  }

  disconnectChatList(): void {
    if (this.subscriptions['users/updates']) {
      this.subscriptions['users/updates'].unsubscribe();
    }
  }

  disconnectChatRoom(chatId: string): void {
    if (this.subscriptions[`messages/${chatId}`]) {
      this.subscriptions[`messages/${chatId}`].unsubscribe();
    }
    this.closeChatRoom(chatId);
  }

  disconnectNotifications(): void {
    for (const key in this.subscriptions) {
      if (this.subscriptions.hasOwnProperty(key)) {
        if (key.startsWith('notifications/')) {
          this.subscriptions[key].unsubscribe();
          delete this.subscriptions[key];
        }
      }
    }
  }

  cleanupOnLogout(): void {
    if (this.chatStompClient) {
      for (const subscription of Object.values(this.subscriptions)) {
        subscription.unsubscribe();
      }
      this.subscriptions = {};
      this.chatStompClient.deactivate();
      this.chatStompClient = null;
      this.hasSubscribed = false;
      this.hasSubscribedUsers = false;
    }
  }

  ngOnDestroy() {
    if (this.chatStompClient) {
      for (const subscription of Object.values(this.subscriptions)) {
        subscription.unsubscribe();
      }
      this.subscriptions = {}
      this.chatStompClient.deactivate();
      this.chatStompClient = null;
    }
  }
}
