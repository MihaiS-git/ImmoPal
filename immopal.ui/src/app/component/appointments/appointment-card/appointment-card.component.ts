import { Component } from '@angular/core';
import { Appointment } from '../../../model/appointment.modet';

@Component({
  selector: 'app-appointment-card',
  templateUrl: './appointment-card.component.html',
  styleUrl: './appointment-card.component.css'
})
export class AppointmentCardComponent {
  appointment: Appointment;
}
