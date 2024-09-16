import { AfterViewChecked, ChangeDetectorRef, Component, ElementRef, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ChatMessage } from '../../../model/chatMesage.model';
import { ChatUser } from '../../../model/chatUser.model';
import { Person } from '../../../model/person.model';
import { User } from '../../../model/user.model';
import { ChatService } from '../../../service/chat.service';
import { SendMessageRequest } from '../../../dto/sendMessageRequest.model';
import { ChatWsService } from '../../../service/chat-ws.service';
import { of, Subscription, switchMap } from 'rxjs';


@Component({
  selector: 'app-chat-room',
  templateUrl: './chat-room.component.html',
  styleUrls: ['./chat-room.component.css']
})
export class ChatRoomComponent implements OnInit, OnDestroy, AfterViewChecked {
  chatMessages: ChatMessage[] = [];
  chatMessagesSubscription: Subscription;
  newMessage = '';
  person: Person | undefined;
  recipient: ChatUser | undefined;
  recipientId: string | null = null;
  sender: ChatUser | undefined;
  senderId: string | null = null;
  user: User | undefined;
  chatId = '';
  defaultPictureUrl = '/user/user_icon.png';
  @ViewChild('scrollMe') private myScrollContainer: ElementRef;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private chatService: ChatService,
    private chatWsService: ChatWsService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.senderId = params['senderId'];
      this.recipientId = params['recipientId'];

      if (this.senderId && this.recipientId) {
        this.loadUserData();
      }
    });
  }

  private loadUserData(): void {
    this.chatService.getChatUserById(this.senderId!).pipe(
      switchMap(sender => {
        this.sender = sender;
        return this.chatService.getChatUserById(this.recipientId!);
      }),
      switchMap(recipient => {
        this.recipient = recipient;
        return this.chatWsService.setChatRoomId(this.senderId!, this.recipientId!);
      }),
      switchMap(chatId => {
        this.chatId = chatId;
        if (this.chatId) {
          this.chatWsService.openChatRoom(this.chatId);
          this.loadOldChatMessages(this.chatId);
          return this.chatWsService.subscribeToMessages(this.recipientId!);
        }
        return of(null);
      })
    ).subscribe(
      (message: ChatMessage | null) => {
        if (message) {
          this.chatMessages.push(message);
          this.cdr.detectChanges();
        }
      },
      (error) => {
        console.error('Error in chat room setup:', error);
      }
    );
  }

  loadOldChatMessages(chatId: string): void {
    this.chatService.getMessagesByChatId(chatId).subscribe(
      (messages: ChatMessage[]) => {
        if (messages) {
          this.chatMessages = messages;
        } else {
          this.chatMessages = [];
        }
      },
      (error) => {
        if (error.status === 404) {
          console.info('No old messages found for this chat room.');
          this.chatMessages = [];
        } else {
          console.error('Error fetching chat room messages:', error);
        }
      });
  }

  sendMessage(): void {
    const users: string[] = [];
    if (this.senderId && this.recipientId) {
      users.push(this.senderId, this.recipientId);
      users.sort();
      this.chatId = users[0] + '_' + users[1];
    }
    if (!this.newMessage || !this.chatId) {
      console.error("Failed to send message.");
      return;
    }
    const chatMessage: SendMessageRequest = {
      chatId: this.chatId,
      senderId: this.senderId,
      content: this.newMessage,
    };
    this.chatWsService.sendMessage(chatMessage);
    this.newMessage = '';
  }

  onClose(recipient: ChatUser) {
    this.chatService.resetUserUnreadCounter(this.senderId, recipient.email).subscribe();
    this.router.navigate([`/chat`, this.senderId]);
    if (this.chatMessagesSubscription) {
      this.chatMessagesSubscription.unsubscribe();
    }
    this.chatWsService.disconnectChatRoom(this.chatId);
  }

  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  scrollToBottom(): void {
    try {
      this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
    } catch (err) { }
  }

  getPictureUrl(senderId: string): string {
    if (senderId === this.senderId) {
      return this.sender?.pictureUrl || this.defaultPictureUrl;
    } else {
      return this.recipient?.pictureUrl || this.defaultPictureUrl;
    }
  }

  ngOnDestroy(): void {
    if (this.chatId) {
      this.chatWsService.disconnectChatRoom(this.chatId);
    }
  }
}
