import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuctionRoomDto } from '../../../dto/auctionRoomDto.model';
import { AuctionService } from '../../../service/auction.service';
import { Router } from '@angular/router';
import { ParticipantDto } from '../../../dto/participantDto.model';
import { User } from '../../../model/user.model';

@Component({
  selector: 'app-favourites',
  templateUrl: './favourites.component.html',
  styleUrl: './favourites.component.css'
})
export class FavouritesComponent implements OnInit, OnDestroy {
  auctionRoomsSubscription: Subscription | null = null;
  auctionRoomDtos: AuctionRoomDto[] = [];
  auctionRoom: AuctionRoomDto | null = null;
  participant: ParticipantDto;
  user: User;

  constructor(private auctionService: AuctionService, private router: Router) { }

  ngOnInit(): void {
    this.loadParticipantData();
  }

  loadParticipantData(): void {
    const userData = localStorage.getItem('userData');
    if (userData) {
      const parsedUserData = JSON.parse(userData);
      this.user = parsedUserData.user;
      this.auctionService.getParticipantByEmail(this.user.email).subscribe(
        (data) => {
          this.participant = data;
          this.fetchAuctionRooms();
        },
        (error) => {
          console.error("Participant not found ", error);
        }
      );
    } else {
      console.error('User data not found in localStorage');
    }
  }

  fetchAuctionRooms() {
    this.auctionRoomsSubscription = this.auctionService.auctionRoomsChanged.subscribe(
      (rooms: AuctionRoomDto[]) => {
        this.auctionRoomDtos = rooms.filter(r => this.participant.auctions.includes(r.id) && r.auctionStatus==='ACTIVE');
      }
    );

    this.auctionService.fetchAuctions().subscribe(
      (rooms: AuctionRoomDto[]) => {
        this.auctionRoomDtos = rooms.filter(r => this.participant.auctions.includes(r.id) && r.auctionStatus==='ACTIVE');
      },
      (error) => {
        console.error("Error fetching auction rooms:", error);
      }
    );
  }

  onSelectRoom(room: AuctionRoomDto): void {
    this.auctionRoom = room;
    this.router.navigate(['/auctions/rooms-list', room.id], { state: { auctionRoom: this.auctionRoom } });
  }

  ngOnDestroy(): void {
    if (this.auctionRoomsSubscription) {
      this.auctionRoomsSubscription.unsubscribe();
    }
  }
}
