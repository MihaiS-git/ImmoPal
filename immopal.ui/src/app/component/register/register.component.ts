import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  providers: [DatePipe]
})
export class RegisterComponent implements OnInit {
  registerForm = this.fb.group({
    firstName: ['', [Validators.required, Validators.minLength(2)]],
    lastName: ['', [Validators.required, Validators.minLength(2)]],
    email: ['', [Validators.required, Validators.pattern(/^[\w-]+(\.[\w-]+)*@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)*(\.[a-zA-Z]{2,})$/)]],
    password: ['', [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/), Validators.minLength(8)]],
    phoneNumber: ['', [Validators.required, Validators.minLength(10), Validators.pattern(/^[\d.()+]+$/)]],
    dateOfBirth: ['', Validators.required],
    address: ['', Validators.required],
  });;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router, private datePipe: DatePipe) { }

  ngOnInit(): void {

  }

  register() {
    const userToCreate = {
      firstName: this.registerForm.value.firstName,
      lastName: this.registerForm.value.lastName,
      email: this.registerForm.value.email,
      password: this.registerForm.value.password,
      phoneNumber: this.registerForm.value.phoneNumber,
      dateOfBirth: this.datePipe.transform(new Date(this.registerForm.value.dateOfBirth), 'yyyy-MM-dd'),
      address: this.registerForm.value.address,
    };
    const userToSave = null;
    this.authService.register(userToCreate).subscribe(
      (res) => {
        alert('Registration successful!!!');
        this.router.navigate(['/auth']);
      },
      (error) => {
        alert('Registration failed.');
      }
    );
  }

  close() {
    this.router.navigate(['']);
  }
}
