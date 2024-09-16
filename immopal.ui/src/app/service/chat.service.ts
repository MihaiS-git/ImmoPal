import { ChatUser } from './../model/chatUser.model';
import { Injectable } from '@angular/core';
import { ChatRoomDto } from '../dto/chatRoomDto.model';
import { Observable, Subject, tap, catchError, throwError, map, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ChatMessage } from '../model/chatMesage.model';
import { ChatWsService } from './chat-ws.service';
import { UserMessageCount } from '../dto/userMessageCount.model';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  baseUrlUsers = 'api/chat/chatUsers';
  baseUrlMessages = 'api/chat/chatMessages';
  baseUrlUnreadCounts = 'api/chat/unreadCounts';
  chatRoomsChanged = new Subject<ChatRoomDto[]>();
  chatUsers: ChatUser[] = [];
  chatUsersChanged = new Subject<ChatUser[]>();
  chatMessages: ChatMessage[] = [];
  chatMessagesChanged = new Subject<ChatMessage[]>();
  private connectionStatusSubject = new Subject<void>();
  private unreadCounts: { [email: string]: number } = {};

  constructor(private http: HttpClient, private chatWsService: ChatWsService) { }

  fetchChatUsers(): Observable<ChatUser[]> {
    return this.http.get<ChatUser[]>(`${this.baseUrlUsers}/users`).pipe(
      tap((users) => {
        this.setChatUsers(users);
      }),
      catchError((error) => {
        console.error('Error fetching chat users:', error);
        return throwError(error);
      })
    );
  }

  setChatUsers(users: ChatUser[]) {
    this.chatUsers = users;
    this.chatUsersChanged.next(this.chatUsers.slice());
  }

  getChatUserById(id: string): Observable<ChatUser> {
    if (this.chatUsers.length === 0) {
      return this.fetchChatUsers().pipe(
        map(users => users.find(u => u.email === id)),
        tap(user => {
          if (!user) {
            throw new Error(`User not found: ${id}`);
          }
        }),
        catchError(error => {
          console.error('Error finding user:', error);
          throw error;
        })
      );
    } else {
      const user = this.chatUsers.find(u => u.email === id);
      if (user) {
        return of(user);
      } else {
        return throwError(() => new Error(`User not found: ${id}`));
      }
    }
  }

  getMessagesByChatId(chatId: string): Observable<ChatMessage[]> {
    return this.http.get<ChatMessage[]>(`${this.baseUrlMessages}/${chatId}`);
  }

  handleConnectChatStatus(chatUser: ChatUser): Observable<void> {
    return this.http.post<void>(`${this.baseUrlUsers}/connectUser`, {
      email: chatUser.email,
      fullName: chatUser.fullName,
      status: chatUser.status,
      pictureUrl: chatUser.pictureUrl
    }).pipe(
      tap(() => {
        if (!this.chatWsService.isConnected() && localStorage.getItem('userData')) {
          this.chatWsService.initializeWebSocketConnection();
        } else {
          return;
        }
        this.connectionStatusSubject.next();
      })
    );
  }

  getConnectionStatus(): Observable<void> {
    return this.connectionStatusSubject.asObservable();
  }

  handleDisconnectChatStatus(): Observable<void> {
    this.chatWsService.disconnectNotifications();
    const email = JSON.parse(localStorage.getItem('userData')).user.email;
    return this.http.post<void>(`${this.baseUrlUsers}/disconnectUser`, email);
  }

  fetchUnreadCounts(): Observable<UserMessageCount[]> {
    return this.http.get<UserMessageCount[]>(`${this.baseUrlUnreadCounts}`);
  }

  resetUserUnreadCounter(senderId: string, recipientId: string): Observable<UserMessageCount> {
    return this.http.post<UserMessageCount>(`${this.baseUrlUnreadCounts}/${senderId}/${recipientId}/reset`, {})
    .pipe(
      catchError((error) => {
        console.error('Error resetting unread count:', error);
        return throwError(error);
      })
    );
  }

  getUnreadCounts(): { [email: string]: number } {
    return this.unreadCounts;
  }
}
