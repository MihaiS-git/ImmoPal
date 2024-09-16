import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuctionRoomDto } from '../../../dto/auctionRoomDto.model';
import { AuctionService } from '../../../service/auction.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-rooms-list',
  templateUrl: './rooms-list.component.html',
  styleUrls: ['./rooms-list.component.css']
})
export class RoomsListComponent implements OnInit {
  auctionRoomsSubscription: Subscription | null = null;
  auctionRoomDtos: AuctionRoomDto[] = [];
  auctionRoom: AuctionRoomDto | null = null;

  constructor(private auctionService: AuctionService, private router: Router, private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.auctionRoomsSubscription = this.auctionService.auctionRoomsChanged.subscribe(
      (rooms: AuctionRoomDto[]) => {
        this.auctionRoomDtos = rooms;
        if (rooms.length === 0) {
          console.warn("No auction rooms available.");
        }
      },
      (error) => {
        console.error("Error in auctionRoomsChanged subscription:", error);
      }
    );

    this.auctionService.fetchAuctions().subscribe(
      (rooms: AuctionRoomDto[]) => {
        this.auctionRoomDtos = rooms;
        if (rooms.length === 0) {
          console.warn("No auction rooms found.");
        }
      },
      (error) => {
        console.error("Error fetching auction rooms:", error);
      }
    );

    this.auctionRoomsSubscription = this.auctionService.auctionRoomsChanged.subscribe(
      (rooms: AuctionRoomDto[]) => {
        try {
          this.auctionRoomDtos = rooms;
        } catch (error) {
          console.log("No rooms found.");
        }
        this.cdr.detectChanges();
      }
    );
  }

  onSelectRoom(room: AuctionRoomDto): void {
    this.auctionRoom = room;
    this.router.navigate(['/auctions/rooms-list', room.id], { state: { auctionRoom: this.auctionRoom } });
  }

}
