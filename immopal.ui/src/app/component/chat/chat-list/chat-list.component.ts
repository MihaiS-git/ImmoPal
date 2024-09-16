import { ChangeDetectionStrategy, ChangeDetectorRef, Component, NgZone, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ChatUser } from '../../../model/chatUser.model';
import { ChatService } from '../../../service/chat.service';
import { User } from '../../../model/user.model';
import { ChatWsService } from '../../../service/chat-ws.service';
import { Subscription } from 'rxjs';
import { UserMessageCount } from '../../../dto/userMessageCount.model';
import { AccountService } from '../../../service/account.service';
import { AuthService } from '../../../service/auth.service';

@Component({
  selector: 'app-chat-list',
  templateUrl: './chat-list.component.html',
  styleUrls: ['./chat-list.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ChatListComponent implements OnInit, OnDestroy {
  chatUsers: ChatUser[] = [];
  recipient: ChatUser | undefined;
  senderId: string | null = null;
  user: User;
  searchText: string = '';
  private subscriptions: Subscription = new Subscription();
  unreadCounts: { [email: string]: number } = {};

  constructor(
    private router: Router,
    private chatService: ChatService,
    private chatWsService: ChatWsService,
    private accountService: AccountService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
    private ngZone: NgZone
  ) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('userData') || '{}').user;
    this.senderId = this.user.email;

    this.accountService.getPersonById(this.user.personId).subscribe(
      (person) => {
        this.chatWsService.connectUser(this.senderId, person.firstName + ' ' + person.lastName, 'ONLINE', person.pictureUrl).subscribe(
          user => {
            this.updateUserList(user);
          },
          error => console.error('Connection error:', error)
        );
      }
    );

    this.initializeWebSocketConnectionCL();
    this.loadChatUsersList();
    this.fetchUnreadCounts();
  }

  initializeWebSocketConnectionCL(): void {
    if (this.authService.checkLoginStatus()) {
      this.chatWsService.initializeWebSocketConnection();
    } else {
      return;
    }

    const userSubscription = this.chatWsService.subscribeToChatUsers().subscribe(
      (updatedUser: ChatUser) => {
        this.updateUserList(updatedUser);
      },
      (error) => {
        console.error('Error receiving user updates:', error);
      }
    );

    this.subscriptions.add(userSubscription);
  }

  updateUserList(updatedUser: ChatUser): void {
    const index = this.chatUsers.findIndex(user => user.email === updatedUser.email);
    if (index === -1) {
      this.chatUsers.push(updatedUser);
    } else {
      this.chatUsers[index] = updatedUser;
    }
    this.cdr.detectChanges();
  }

  loadChatUsersList(): void {
    this.chatService.fetchChatUsers().subscribe(users => {
      this.chatUsers = users.filter(user => user.email !== this.senderId) || [];
      if (this.chatUsers.length === 0) {
        console.error('No chat users available.');
        return;
      }
      this.cdr.detectChanges();
    }, error => {
      console.error('Error fetching chat users:', error);
    });
  }

  fetchUnreadCounts(): void {
    this.chatService.fetchUnreadCounts().subscribe(
      (umcList: UserMessageCount[]) => {
        console.log('Fetched unread counts:', umcList);
        this.ngZone.run(() => {
          this.unreadCounts = umcList.reduce((acc, umc) => {
            acc[umc.senderId] = umc.unreadCount;
            return acc;
          }, {});
          console.log('Updated unreadCounts:', this.unreadCounts);
          this.cdr.detectChanges();
        });
      },
      error => {
        console.error('Error fetching unread counts:', error);
      }
    );
  }

  resetUnreadCount(senderId: string, recipientId: string): void {
    this.chatService.resetUserUnreadCounter(senderId, recipientId)
      .subscribe(
        () => {
          // Clone the unreadCounts object to ensure Angular detects the change
          this.unreadCounts = { ...this.unreadCounts, [recipientId]: 0 };
          this.cdr.detectChanges();
        },
        error => {
          console.error('Error resetting unread count:', error);
        }
      );
  }

  openChat(recipient: ChatUser): void {
    if (this.senderId && recipient.email) {
      this.resetUnreadCount(this.senderId, recipient.email);
      this.router.navigate([`/chat/${this.senderId}/${recipient.email}`]);
    }
  }

  searchUser() {
    const searchedUser: ChatUser | undefined = this.chatUsers.find(u => u.fullName.toLowerCase().includes(this.searchText.toLowerCase()));
    if (searchedUser) {
      this.router.navigate([`/chat/${this.senderId}/${searchedUser.email}`]);
    } else {
      console.error('User not found.');
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
    this.chatWsService.disconnectChatList();
  }
}
