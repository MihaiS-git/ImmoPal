import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../../model/user.model';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-recover',
  templateUrl: './recover.component.html',
  styleUrl: './recover.component.css'
})
export class RecoverComponent  implements OnInit {
  recoverForm: FormGroup;
  user: User;
  error: string;
  newPassword: string;
  confirmationPassword: string;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.recoverForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      newPassword: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/), Validators.minLength(8)]],
      confirmationPassword: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/), Validators.minLength(8)]]
    });
  }

  onSubmit() {
    if (this.recoverForm.value.newPassword !== this.recoverForm.value.confirmationPassword) {
      alert("New password and confirm password do not match!");
      return;
    }

    this.newPassword = this.recoverForm.value.newPassword;
    this.confirmationPassword = this.recoverForm.value.confirmationPassword;
    const email = this.recoverForm.value.email;
    const userPass = {
      newPassword: this.newPassword,
      confirmationPassword: this.confirmationPassword
    }
    localStorage.setItem('passData', JSON.stringify(userPass));

    this.authService.changePassword(email).subscribe(
      (response: any) => {
        alert(JSON.stringify(response.message));
        this.router.navigate(['']);
      },
      (error: any) => {
        console.error("Error changing password: ", error);
      }
    );
  }

  close() {
    this.router.navigate(['']);
  }

}
