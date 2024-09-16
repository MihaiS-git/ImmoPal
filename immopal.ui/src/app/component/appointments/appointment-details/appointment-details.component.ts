import { Component, OnInit } from '@angular/core';
import { AppointmentRequestDto } from '../../../dto/appointment-request-dto.model';
import { Status } from '../../../model/status.enum';
import { AppointmentItem } from '../../../model/appointmentItem.model';
import { User } from '../../../model/user.model';
import { catchError, of, tap } from 'rxjs';
import { AccountService } from '../../../service/account.service';
import { AppointmentService } from '../../../service/appointment.service';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-appointment-details',
  templateUrl: './appointment-details.component.html',
  styleUrls: ['./appointment-details.component.css'],
  providers: [DatePipe]
})
export class AppointmentDetailsComponent implements OnInit {
  appointmentItem: AppointmentItem;
  totalPrice: number = 0.0;
  startDate: '';
  personId: number;
  agentId: number;
  user: User;

  constructor(
    private accountService: AccountService,
    private appointmentService: AppointmentService,
    private datePipe: DatePipe,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.user = JSON.parse(localStorage.getItem('userData')).user;
    this.loadPersonDetails(this.user.personId);
    this.appointmentItem = this.appointmentService.getAppointmentItem();
  }

  loadPersonDetails(personId: number) {
    this.accountService
      .getPersonById(personId)
      .pipe(
        catchError((err) => {
          console.error('Error fetching person details:', err);
          return of(null);
        }),
        tap((resData) => {
          if (resData) {
            this.personId = resData.id;
          } else {
            console.error('Person details not found.');
          }
        })
      )
      .subscribe();
  }

  book() {
    const appointmentRequestDto: AppointmentRequestDto = {
      customerId: this.user.personId,
      agentId: this.appointmentItem.agentId,
      propertyId: this.appointmentItem.id,
      startDateTime: this.datePipe.transform(this.startDate, 'yyyy-MM-dd HH:mm:ss'),
      approvalStatus: Status.PENDING,
    };

    this.appointmentService
      .addAppointment(appointmentRequestDto)
      .subscribe({
        next: (res) => {
          if (res) {
            alert('Appointment created successfully.');
          }
        },
        error: (err) => {
          console.error('Failed to create Appointment', err);
          alert(
            'Not able to create an appointment with the selected data.'
          );
        },
      });
    this.router.navigate(['/agencies']);
  }

  close() {
    this.appointmentService.removeAppointmentItem();
    this.router.navigate(['/agencies']);
  }
}
