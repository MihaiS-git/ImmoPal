import { Injectable } from '@angular/core';
import { Observable, of, Subject, Subscription, timer } from 'rxjs';
import { Message, Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { environment } from '../../environments/environment.prod';
import { BidMessage } from '../model/bidMessage.model';

interface RoomSubscription {
  roomId: string;
  subscription: Subscription;
}

@Injectable({
  providedIn: 'root'
})
export class AuctionWsService {
  private stompClient: any;
  private messageSubject: Subject<BidMessage> = new Subject<BidMessage>();
  private auctionRoomId: string | null = null;
  private isSubscribed = false;
  private subscriptions: RoomSubscription[] = [];
  private reconnecting = false;
  private reconnectDelay = 5000;

  constructor() { }

  public setAuctionRoomId(roomId: string): void {
    if (this.auctionRoomId !== roomId) {
      this.auctionRoomId = roomId;
      if (this.isConnected()) {
        this.unsubscribeAll();
      }
      this.initializeWebSocketConnection();
    }
  }

  private initializeWebSocketConnection(): void {

    if (!this.auctionRoomId) {
      console.error('Auction room ID is not set.');
      return;
    }

    if (this.stompClient) {
      this.unsubscribeAll()
      this.stompClient.deactivate();
      this.stompClient = null;
    }

    const serverUrl = environment.websocketUrls.auctionWSUrl;
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(() => ws);

    this.stompClient.configure({
      reconnectDelay: this.reconnectDelay,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: (frame: any) => this.onConnected(frame),
      onStompError: (frame: any) => this.onError(frame),
      onWebSocketClose: (evt: CloseEvent) => this.onWebSocketClose(evt),
      onWebSocketError: (evt: Event) => this.onWebSocketError(evt)
    });

    this.stompClient.activate();
  }

  private unsubscribeAll() {
    this.subscriptions.forEach(({ subscription }) => {
      if (subscription && typeof subscription.unsubscribe === 'function') {
        subscription.unsubscribe();
      }
    });
    this.subscriptions = [];
    this.isSubscribed = false;
  }


  private onConnected(frame: any): void {
    this.updateSubscription();
  }

  private onError(frame: any): void {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
    this.reconnect();
  }

  private onWebSocketClose(evt: CloseEvent): void {
    console.error('WebSocket closed.', evt);
  }

  private onWebSocketError(evt: Event): void {
    console.error('WebSocket error. Reconnecting...', evt);
    this.reconnect();
  }

  isConnected(): boolean {
    return this.stompClient && this.stompClient.connected;
  }

  private reconnect() {
    if (!this.isConnected() && localStorage.getItem('userData')) {
      console.log("Reinitialize WebSocket STOMP connection...");
      if (!this.reconnecting) {
        this.reconnecting = true;
        timer(this.reconnectDelay).subscribe(() => {
          this.initializeWebSocketConnection();
          this.reconnecting = false;
        });
      }
    } else {
      console.log("Websocket is not reconnecting.");

    }
  }

  private updateSubscription(): void {
    if (this.isConnected() && this.auctionRoomId && !this.isSubscribed) {
      const existingSubscription = this.subscriptions.find(sub => sub.roomId === this.auctionRoomId);
      if (existingSubscription) {
        console.log('Subscription for this room already exists.');
        return;
      }

      const subscription = this.stompClient.subscribe(
        `/topic/auction/${this.auctionRoomId}/bids`, (message: Message) => {
        try {
          const parsedMessage = JSON.parse(message.body) as BidMessage;
          this.messageSubject.next(parsedMessage);
        } catch (error) {
          console.error('Error parsing message body:', error);
        }
      });

      this.subscriptions.push({ roomId: this.auctionRoomId, subscription });
      this.isSubscribed = true;
    }
  }

  getMessage(): Observable<BidMessage> {
    return this.messageSubject.asObservable();
  }

  sendMessage(message: BidMessage): void {
    if (!this.isConnected()) {
      console.error('STOMP client is not connected.');
      this.reconnect();
      return;
    }
    this.stompClient.send(`/app/auction/${this.auctionRoomId}/bid`, {}, JSON.stringify(message));
  }

  public disconnect(): Observable<void> {
    if (this.stompClient) {
      this.unsubscribeAll();
      this.stompClient.deactivate();
      this.stompClient = null;
      this.isSubscribed = false;
    }
    return of(undefined);
  }
}
