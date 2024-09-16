import { BidMessage } from './../../../model/bidMessage.model';
import { ParticipantDto } from './../../../dto/participantDto.model';
import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { AuctionRoomDto } from '../../../dto/auctionRoomDto.model';
import { ActivatedRoute } from '@angular/router';
import { AuctionService } from '../../../service/auction.service';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { AuctionWsService } from '../../../service/auction-ws.service';
import { MessageType } from '../../../model/messageType.enum';
import { User } from '../../../model/user.model';
import { AccountService } from '../../../service/account.service';
import { Subscription } from 'rxjs';
import { Person } from '../../../model/person.model';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent implements OnInit, OnDestroy {
  customOptions: OwlOptions = {
    loop: true,
    mouseDrag: true,
    touchDrag: false,
    pullDrag: false,
    mergeFit: true,
    nav: true,
    navText: ['<<', '>>'],
    center: true,
    dots: true,
    autoplay: true,
    autoplayTimeout: 5000,
    autoplayHoverPause: true,
    navSpeed: 700,
    responsive: {
      0: { items: 1 },
      400: { items: 1 },
      740: { items: 1 },
      940: { items: 1 }
    },
  };

  auctionRoom: AuctionRoomDto;
  auctionRoomId: string;
  bidAmount: number;
  user: User;
  email: string;
  personId: number;
  person: Person;
  participant: ParticipantDto;
  private messageSubscription: Subscription = new Subscription();
  private routeSubscription: Subscription;

  constructor(
    private route: ActivatedRoute,
    private auctionWsService: AuctionWsService,
    private auctionService: AuctionService,
    private accountService: AccountService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.routeSubscription = this.route.params.subscribe(params => {
      this.auctionRoomId = params['roomId'];
      if (this.auctionRoomId) {
        this.auctionWsService.setAuctionRoomId(this.auctionRoomId);
        this.fetchAuctionRoomDetails();
        this.setupMessageSubscription();
      }
    });

    this.loadUserData();
  }

  loadUserData(): void {
    const userData = localStorage.getItem('userData');
    if (userData) {
      const parsedUserData = JSON.parse(userData);
      this.user = parsedUserData.user;
      this.email = this.user.email;
      this.personId = this.user.personId;
      this.loadParticipantData();
    } else {
      console.error('User data not found in localStorage');
    }
  }

  loadParticipantData(): void {
    this.accountService.getPersonById(this.personId).subscribe(
      (data) => {
        this.person = data;
        this.participant = {
          id: null,
          personId: this.person.id,
          firstName: this.person.firstName,
          lastName: this.person.lastName,
          phoneNumber: this.person.phoneNumber,
          dateOfBirth: this.person.dateOfBirth,
          address: this.person.address,
          pictureUrl: this.person.pictureUrl,
          userId: this.person.userId,
          email: this.email,
          auctions: []
        };
      },
      (error) => {
        console.error('Failed to load participant data', error);
      }
    );
  }

  fetchAuctionRoomDetails(): void {
    this.auctionService.getAuctionRoomById(this.auctionRoomId).subscribe(
      (room: AuctionRoomDto) => {
        this.auctionRoom = room;
        if (this.auctionRoom.bids) {
          this.auctionRoom.bids = this.auctionRoom.bids.slice(-10);
        } else {
          this.auctionRoom.bids = [];
        }
      },
      (error) => {
        console.error('Error fetching auction room data:', error);
      }
    );
  }

  setupMessageSubscription(): void {
    this.messageSubscription = this.auctionWsService.getMessage().subscribe(
      (message: BidMessage) => {
        if (this.auctionRoom) {
          if (!this.auctionRoom.bids) {
            this.auctionRoom.bids = [];
          }
          this.auctionRoom.bids.push(message);
          if (this.auctionRoom.bids.length > 10) {
            this.auctionRoom.bids = this.auctionRoom.bids.slice(-10);
          }
          this.cdr.detectChanges();
          this.cdr.markForCheck();
        }
      },
      (error) => {
        console.error('WebSocket error:', error);
      }
    );
  }

  placeBid(): void {
    if (!this.auctionRoomId || !this.bidAmount || this.bidAmount <= this.auctionRoom?.maxBidAmount) {
      console.error('Auction Room ID or Bid Amount is missing or invalid');
      alert("Your bid is under the last bid value. Try again!");
      return;
    }

    if (!this.participant) {
      console.error('Participant data not loaded');
      alert("You are not allowed to place a bid. Please register first!");
      return;
    }

    const bidMessage: BidMessage = {
      id: null,
      auctionRoomId: this.auctionRoomId,
      type: MessageType.BID,
      sender: this.participant,
      amount: this.bidAmount,
      timestamp: new Date()
    };

    this.auctionWsService.sendMessage(bidMessage);
    this.bidAmount = null;
  }

  ngOnDestroy(): void {
    if (this.messageSubscription) {
      this.messageSubscription.unsubscribe();
    }
    if (this.routeSubscription) {
      this.routeSubscription.unsubscribe();
    }
  }
}
