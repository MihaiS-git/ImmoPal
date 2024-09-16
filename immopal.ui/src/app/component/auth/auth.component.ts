import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';
import { catchError, of, tap } from 'rxjs';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent {
  authForm: FormGroup;
  submitAttempt = false;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.authForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{7,}$/), Validators.minLength(7)]]
    });
  }

  onSubmit() {
    const email = this.authForm.value.email;
    const password = this.authForm.value.password;

    this.submitAttempt = true;

    if (this.authForm.valid) {
      this.authService.login(email, password).pipe(
        catchError((err) => {
          alert(`Login failed. Invalid credentials.`);
          return of(null);
        }),
        tap(resData => {
          if (resData) {
            alert("Login successful! Enjoy your stay!")
            this.router.navigate(['']);
          }
        })
      ).subscribe();

      this.authForm.reset();
    }
  }

  close() {
    this.router.navigate(['']);
  }
}
