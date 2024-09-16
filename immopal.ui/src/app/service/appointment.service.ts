import { Injectable } from '@angular/core';
import { AppointmentItem } from '../model/appointmentItem.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { AppointmentRequestDto } from '../dto/appointment-request-dto.model';
import { AppointmentResponseDto } from '../dto/appointmentResponseDto.model';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  baseUrl = '/api/appointments';
  appointmentItem: AppointmentItem | null = null;

  constructor(private http: HttpClient) {}

  addToAppointment(theAppointmentItem: AppointmentItem) {
    this.appointmentItem = theAppointmentItem;
  }

  getAppointmentItem() {
    return this.appointmentItem;
  }

  removeAppointmentItem() {
    this.appointmentItem = null;
  }

  getAppointmentById(id: number): Observable<AppointmentResponseDto> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  addAppointment(appointmentRequestDto: AppointmentRequestDto): Observable<AppointmentRequestDto> {
    return this.http.post<AppointmentRequestDto>(`${this.baseUrl}`, appointmentRequestDto);
  }

  updateAppointmentById(id: number, updatedAppointment: any): Observable<AppointmentRequestDto> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, updatedAppointment);
  }

  deleteAppointmentById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
