import { ChangeDetectionStrategy, ChangeDetectorRef, Component, NgZone, OnInit } from '@angular/core';
import { ChatNotification } from '../../../model/chatNotification.model';
import { Router } from '@angular/router';
import { ChatWsService } from '../../../service/chat-ws.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-chat-notification',
  templateUrl: './chat-notification.component.html',
  styleUrls: ['./chat-notification.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ChatNotificationComponent implements OnInit {
  currentNotifications: ChatNotification[] = [];
  private notificationSubscription: Subscription;
  currentUserEmail = '';

  constructor(
    private chatWsService: ChatWsService,
    private router: Router,
    private ngZone: NgZone,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    console.log('ChatNotificationComponent ngOnInit() called');
    const userData = localStorage.getItem('userData');
    if (userData) {
      this.currentUserEmail = JSON.parse(userData).user.email;
    }

    this.chatWsService.initializeWebSocketConnection();

    if (this.chatWsService.isConnected()) {
      this.notificationSubscription = this.chatWsService.subscribeToNotifications().subscribe(
        (notification: ChatNotification) => {
          console.log('Notification received:', notification);
          const chatId = [notification.senderId, notification.recipientId].sort().join("_");
          this.chatWsService.isChatRoomOpen(chatId).subscribe(isOpen => {
            if (!isOpen) {
              this.ngZone.run(() => {
                console.log(`[${this.currentUserEmail}] Notification added for chatId ${chatId}`);
                this.currentNotifications.push(notification);
                this.cdr.detectChanges();
                /* this.cdr.markForCheck(); */
              });
            } else {
              console.log(`[${this.currentUserEmail}] Chat room ${chatId} is already open, notification not added.`);
            }
          });
        },
        (error) => {
          console.error(`[${this.currentUserEmail}] Error receiving notifications:`, error);
        }
      );
    }
  }

  onNotificationClick(): void {
    this.currentNotifications = [];
    this.router.navigate(['/chat', this.currentUserEmail]);
    this.cdr.detectChanges();
    /* this.cdr.markForCheck(); */
  }

}
