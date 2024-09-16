import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { AuctionService } from '../service/auction.service';
import { catchError, Observable, of } from 'rxjs';
import { AuctionRoomDto } from '../dto/auctionRoomDto.model';

@Injectable({
  providedIn: 'root'
})
export class AuctionResolverService implements Resolve<any>{

  constructor(private auctionService: AuctionService) { }

  resolve(): Observable<AuctionRoomDto[]> {
    const cachedRooms = this.auctionService.getAuctionRooms();

    if (cachedRooms.length > 0) {
      return of(cachedRooms);
    } else {
      return this.auctionService.fetchAuctions().pipe(
        catchError(error => {
          console.error('Error fetching auction rooms:', error);
          return of([]);
        })
      );
    }
  }

}
