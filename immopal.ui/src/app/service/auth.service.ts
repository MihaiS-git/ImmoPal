import { AuctionWsService } from './auction-ws.service';
import { Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject, catchError, Observable, switchMap, tap, throwError } from 'rxjs';
import { User } from '../model/user.model';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { UserService } from './user.service';
import { ChatService } from './chat.service';
import { AccountService } from './account.service';
import { ChatStatus } from '../model/chatStatus.enum';
import { ChatUser } from '../model/chatUser.model';
import { ChatWsService } from './chat-ws.service';
import { AuctionService } from './auction.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService implements OnDestroy {
  private baseUrl = "/api/auth";
  user = new BehaviorSubject<User>(null);
  _user: User;
  _email: string;
  connectedUser: User;
  chatUser: ChatUser;

  constructor(
    private http: HttpClient,
    private router: Router,
    private userService: UserService,
    private chatService: ChatService,
    private chatWsService: ChatWsService,
    private accountService: AccountService,
    private auctionService: AuctionService,
    private auctionWsService: AuctionWsService
  ) {
    this.checkLoginStatus();
  }

  register(user: any) {
    this._user = user;
    return this.http.post<any>(`${this.baseUrl}/register`, user)
      .pipe(
        catchError(this.handleError),
        tap(resData => {
          this.handleRegistration(resData.token);
        })
      );
  }

  handleRegistration(token: any) {
    const userData = {
      sToken: token,
      user: this._user
    };
    localStorage.setItem('userData', JSON.stringify(userData));
  }

  login(email: string, password: string) {
    this._email = email;
    return this.http.post<any>(`${this.baseUrl}/login`, { email, password })
      .pipe(
        catchError(this.handleError),
        tap(resData => {
          this.handleAuthentication(resData.token, email);
        }),
      );
  }

  handleAuthentication(token: any, email: string) {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    this.userService.getUserByEmail(email, headers)
      .pipe(
        catchError(this.handleError),
        tap(resData => {
          this._user = resData;
          this.user.next(this._user);
          const userData = {
            sToken: token,
            user: this._user
          };
          localStorage.setItem('userData', JSON.stringify(userData));
          this.handleConnectChatStatus();
        })
      ).subscribe();
  }

  handleConnectChatStatus(): void {
    this.accountService.getPersonById(this._user.personId).pipe(
      tap((person) => {
        this.chatUser = {
          email: this._user.email,
          fullName: `${person.firstName} ${person.lastName}`,
          status: ChatStatus.ONLINE,
          pictureUrl: person.pictureUrl
        }
        localStorage.setItem('chatUserData', JSON.stringify(this.chatUser));
        this.chatService.handleConnectChatStatus(this.chatUser).subscribe();
      }),
      catchError((error) => {
        console.error("Failed to fetch person data: ", error);
        return throwError(error);
      })
    ).subscribe();
  }

  handleError(errorRes: HttpErrorResponse) {
    let errorMessage = errorRes.message;
    console.error('Auth Service: ', errorMessage);
    return throwError(errorMessage);
  }

  getToken(): string | null {
    const userData = localStorage.getItem('userData');
    if (userData) {
      const parsedUserData = JSON.parse(userData);
      return parsedUserData.sToken || null;
    }
    return null;
  }

  changePassword(email: string) {
    return this.http.patch<any>(`http://localhost:8080/api/auth/recover-password/${email}`, {});
  }

  checkLoginStatus(): boolean {
    const userData = localStorage.getItem('userData');
    if (userData) {
      const parsedUserData = JSON.parse(userData);
      this.user.next(parsedUserData.user);
      return true;
    } else {
      this.user.next(null);
      return false;
    }
  }

  verifyTokenAndChangePassword(token: string, newPassword: string, confirmationPassword: string): Observable<any> {
    return this.http.patch<any>(`${this.baseUrl}/reset-password?token=${token}`, { newPassword, confirmationPassword });
  }

  logout() {
    this.auctionService.handleDisconnectAuction().pipe(
      switchMap(() => this.chatService.handleDisconnectChatStatus()),
      tap(() => {
        this.user.next(null);
        localStorage.removeItem('userData');
        localStorage.removeItem('chatUserData');
        this.chatWsService.cleanupOnLogout();
        this.router.navigate(['/auth']);
      }),
      catchError((error) => {
        console.error("Failed to logout: ", error);
        return throwError(error);
      })
    ).subscribe();
  }

  ngOnDestroy(): void {
    this.auctionWsService.disconnect().subscribe();
    this.chatService.handleDisconnectChatStatus().pipe(
      tap(() => {
        this.user.next(null);
        this.router.navigate(['/auth']);
        localStorage.removeItem('userData');
      }),
      catchError((error) => {
        console.error("Failed to logout: ", error);
        return throwError(error);
      })
    ).subscribe();
  }
}
