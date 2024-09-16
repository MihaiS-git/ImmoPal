import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../service/auth.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrl: './reset-password.component.css'
})
export class ResetPasswordComponent  implements OnInit {
  token = '';
  error: string;
  newPassword: string;
  confirmationPassword: string;

  constructor(private authService: AuthService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.token = params['token'];
    });
  }

  getPasswords() {
    const passData = localStorage.getItem('passData');
    if (passData) {
      const parsedPassData = JSON.parse(passData);
      this.newPassword = parsedPassData.newPassword;
      this.confirmationPassword = parsedPassData.confirmationPassword;
    }
  }

  onAgree() {
    this.getPasswords();
    if (!this.token) {
      this.error = "Invalid token. Please check the URL.";
    } else {
      this.authService.verifyTokenAndChangePassword(this.token, this.newPassword, this.confirmationPassword).subscribe(
        (response) => {
          alert(JSON.stringify(response.message));
          this.router.navigate(['/auth']);
        },
        (error) => {
          console.error('Error verifying token:', error);
        }
      );
    }
    localStorage.removeItem('passData');
  }

  close() {
    this.router.navigate(['']);
  }
}
