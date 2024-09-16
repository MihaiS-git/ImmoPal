import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, Subject, tap, throwError } from 'rxjs';
import { AuctionRoomDto } from '../dto/auctionRoomDto.model';
import { ParticipantDto } from '../dto/participantDto.model';
import { AuctionWsService } from './auction-ws.service';

@Injectable({
  providedIn: 'root'
})
export class AuctionService {
  baseUrl = '/api/auctionRooms';
  auctionRooms: AuctionRoomDto[] = [];
  auctionRoomsChanged = new Subject<AuctionRoomDto[]>();

  constructor(private http: HttpClient, private auctionWsService: AuctionWsService) { }

  fetchAuctions(): Observable<AuctionRoomDto[]> {
    return this.http.get<AuctionRoomDto[]>(`${this.baseUrl}`).pipe(
      tap((rooms) => {
        this.setRooms(rooms);
      }),
      catchError((error) => {
        console.error('Error fetching auction rooms:', error);
        return throwError(error);
      })
    );
  }

  setRooms(rooms: AuctionRoomDto[]) {
    this.auctionRooms = rooms;
    this.auctionRoomsChanged.next(this.auctionRooms.slice());
  }

  getAuctionRooms() {
    return this.auctionRooms.slice();
  }

  getAuctionRoomById(roomId: string): Observable<AuctionRoomDto> {
    return this.http.get<AuctionRoomDto>(`${this.baseUrl}/${roomId}`);
  }

  createNewRoom(roomToCreate: { propertyId: string; startDate: string; }) {
    return this.http.post<any>(`${this.baseUrl}`, roomToCreate);
  }

  getParticipantByEmail(email: string): Observable<ParticipantDto> {
    return this.http.get<any>(`${this.baseUrl}/participant/${email}`);
  }

  handleDisconnectAuction(): Observable<void> {
    return this.auctionWsService.disconnect();
  }
}
