import { Component, OnInit } from '@angular/core';
import { Role } from '../../model/role.enum';
import { AuthService } from '../../service/auth.service';
import { Person } from '../../model/person.model';
import { User } from '../../model/user.model';
import { catchError, Observable, of, tap } from 'rxjs';
import { AccountService } from '../../service/account.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  isLoggedIn = false;
  role: Role;
  person: Person;
  user: User;

  constructor(private authService: AuthService, private accountService: AccountService) { }

  ngOnInit(): void {
    this.authService.user.subscribe(user => {
      this.isLoggedIn = !!user;
      if (user) {
        this.user = user;
        this.role = user.role;
        const personId = user.personId;
        this.loadPersonDetails(personId).subscribe();
      }
    });

    const userData = localStorage.getItem('userData');
    if (userData) {
      this.isLoggedIn = true;
    }
  }

  private loadPersonDetails(personId: number): Observable<any> {
    return this.accountService.getPersonById(personId).pipe(
      catchError(err => {
        console.error('Error fetching person details:', err);
        return of(null);
      }),
      tap(resData => {
        if (resData) {
          this.person = resData;
        } else {
          console.error('Person details not found.');
        }
      })
    );
  }

  logout() {
    this.authService.logout();
  }
}
