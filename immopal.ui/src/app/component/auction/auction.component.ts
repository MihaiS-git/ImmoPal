import { Component, OnInit } from '@angular/core';
import { AuctionRoom } from '../../model/auctionRoom.model';
import { User } from '../../model/user.model';

@Component({
  selector: 'app-auction',
  templateUrl: './auction.component.html',
  styleUrl: './auction.component.css'
})
export class AuctionComponent implements OnInit{
  auctionRooms: AuctionRoom[] = [];
  user: User;

  constructor() { }

  ngOnInit(): void {
    this.loadUserData();
  }

  loadUserData(): void {
    const userData = localStorage.getItem('userData');
    if (userData) {
      const parsedUserData = JSON.parse(userData);
      this.user = parsedUserData.user;
    } else {
      console.error('User data not found in localStorage');
    }
  }
}
