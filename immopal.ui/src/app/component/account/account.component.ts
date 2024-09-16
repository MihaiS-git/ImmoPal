import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { catchError, forkJoin, map, Observable, of, Subscription, tap } from 'rxjs';
import { Person } from '../../model/person.model';
import { Role } from '../../model/role.enum';
import { User } from '../../model/user.model';
import { AccountService } from '../../service/account.service';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';
import { Appointment } from '../../model/appointment.modet';
import { AppointmentService } from '../../service/appointment.service';
import { PropertyService } from '../../service/property.service';
import { AppointmentResponseDto } from '../../dto/appointmentResponseDto.model';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css'],
  providers: [DatePipe]
})
export class AccountComponent implements OnInit {
  editPersonalDataForm: FormGroup;
  appointment: Appointment;
  appointments: Appointment[] = [];
  appointmentsForm: FormGroup;
  appointmentsSubscription: Subscription = null;
  isLoggedIn = false;
  person: Person = {} as Person;
  personId: number;
  role: Role;
  user: User;
  errorMessage = '';

  constructor(
    private accountService: AccountService,
    private authService: AuthService,
    private appointmentService: AppointmentService,
    private propertyService: PropertyService,
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.initForms();

    this.authService.user.subscribe(user => {
      this.isLoggedIn = !!user;
      if (user) {
        this.user = user;
        this.role = user.role;
        const personId = user.personId;
        this.loadPersonDetails(personId).subscribe(() => {
          this.loadCurrentUserAppointments(personId);
        });
      }
    });

  }

  private initForms(): void {
    this.editPersonalDataForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      phoneNumber: ['', [Validators.required, Validators.minLength(10), Validators.pattern(/^[\d.()+]+$/)]],
      dateOfBirth: ['', Validators.required],
      address: ['', Validators.required]
    });

    this.appointmentsForm = this.fb.group({
      appointmentsFA: this.fb.array([])
    });

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
          this.editPersonalDataForm.patchValue({
            firstName: this.person.firstName,
            lastName: this.person.lastName,
            phoneNumber: this.person.phoneNumber,
            dateOfBirth: this.person.dateOfBirth,
            address: this.person.address,
            pictureUrl: this.person.pictureUrl
          });
        } else {
          console.error('Person details not found.');
        }
      })
    );
  }

  private loadCurrentUserAppointments(personId: number): void {
    this.accountService.getPersonById(personId).pipe(
      catchError(err => {
        console.error('Error fetching person details:', err);
        return of(null);
      }),
      tap(resData => {
        if (resData) {
          this.person = resData;
          this.editPersonalDataForm.patchValue({
            firstName: this.person.firstName,
            lastName: this.person.lastName,
            phoneNumber: this.person.phoneNumber,
            dateOfBirth: this.person.dateOfBirth,
            address: this.person.address,
            pictureUrl: this.person.pictureUrl
          });

          this.loadAppointments(this.person.appointmentIds);
        } else {
          console.error('Person details not found.');
        }
      })
    ).subscribe();
  }

  private loadAppointments(appointmentIds: number[]): void {
    const appointmentObservables = appointmentIds.map(id =>
      this.appointmentService.getAppointmentById(id).pipe(
        catchError(err => {
          console.error('Error fetching appointment:', err);
          return of(null);
        })
      )
    );

    forkJoin(appointmentObservables).pipe(
      map(appointments => appointments.filter(a => a !== null)),
      tap(appointments => {
        this.populateAppointments(appointments as AppointmentResponseDto[]);
      }),
      catchError(err => {
        console.error('Error fetching appointments:', err);
        this.errorMessage = 'Error fetching appointments';
        return of([]);
      })
    ).subscribe();
  }

  private populateAppointments(appointments: AppointmentResponseDto[]): void {
    const observables = appointments.map(appointment =>
      forkJoin({
        appointment: of(appointment),
        property: this.propertyService.getPropertyById(appointment.propertyId).pipe(catchError(() => of(null))),
        customer: this.accountService.getPersonById(appointment.customerId).pipe(catchError(() => of(null))),
        agent: this.accountService.getPersonById(appointment.agentId).pipe(catchError(() => of(null)))
      })
    );

    forkJoin(observables).pipe(
      tap(results => {
        const populatedAppointments = results.map(({ appointment, property, customer, agent }) => ({
          ...appointment,
          property,
          customer,
          agent
        }));
        this.setAppointmentsFormArray(populatedAppointments);
      }),
      catchError(err => {
        this.errorMessage = 'Error populating appointments';
        console.error('Error populating appointments:', err);
        return of([]);
      })
    ).subscribe();
  }


  private setAppointmentsFormArray(appointments: any[]): void {
    const appointmentFormGroups = appointments.map(appointment =>
      this.fb.group({
        appointmentId: [appointment.id || null, Validators.required],
        customerFirstName: [appointment.customer?.firstName || '', Validators.required],
        customerLastName: [appointment.customer?.lastName || '', Validators.required],
        customerPhoneNumber: [appointment.customer?.phoneNumber || '', Validators.required],
        startDateTime: [appointment.startDateTime || null, Validators.required],
        endDateTime: [appointment.endDateTime || null, Validators.required],
        dateCreated: [new Date(appointment.dateCreated) || null, Validators.required],
        approvalStatus: [appointment.approvalStatus || null, Validators.required],
        agentFirstName: [appointment.agent?.firstName || '', Validators.required],
        agentLastName: [appointment.agent?.lastName || '', Validators.required],
        agentPhoneNumber: [appointment.agent?.phoneNumber || '', Validators.required],
        transactionType: [appointment.property?.transactionType || ''],
        propertyCategory: [appointment.property?.propertyCategory || ''],
        country: [appointment.property?.address.country || ''],
        city: [appointment.property?.address.city || ''],
        price: [appointment.property?.price || ''],

        customerId: [appointment.customerId || null],
        agentId: [appointment.agentId || null],
        propertyId: [appointment.propertyId || null, Validators.required]
      })
    );

    const appointmentFormArray = this.fb.array(appointmentFormGroups);
    this.appointmentsForm.setControl('appointmentsFA', appointmentFormArray);
  }


  onSubmitPDF(): void {
    if (this.editPersonalDataForm.valid) {
      const updatedPerson = { ...this.editPersonalDataForm.value, dateOfBirth: this.datePipe.transform(this.editPersonalDataForm.value.dateOfBirth, 'yyyy-MM-dd') };
      updatedPerson.personId = this.person.id;

      this.accountService.updatePerson(updatedPerson.personId, updatedPerson).subscribe(
        response => {
          alert('Person details updated successfully.');
          this.router.navigate(['']);
        },
        error => {
          console.error('Error updating person:', error);
          alert('Error updating person.');
        }
      );
    }
  }

  updateAppointment(index: number): void {
    const appointmentGroup = (this.appointmentsForm.get('appointmentsFA') as FormArray).at(index) as FormGroup;

    if (!appointmentGroup) {
      console.error('Error: Appointment group is undefined.');
      alert('Error updating appointment.');
      return;
    }

    const appointmentId = appointmentGroup.get('appointmentId')?.value;

    if (!appointmentId) {
      console.error('Error: Appointment ID control is undefined.');
      alert('Error updating appointment.');
      return;
    }

    const updatedAppointment: any = {
      customerId: appointmentGroup.get('customerId')?.value,
      startDateTime: this.datePipe.transform(new Date(appointmentGroup.get('startDateTime')?.value), 'yyyy-MM-dd HH:mm:ss'),
      approvalStatus: appointmentGroup.get('approvalStatus')?.value,
      agentId: appointmentGroup.get('agentId')?.value,
      propertyId: appointmentGroup.get('propertyId')?.value
    };

    this.appointmentService.updateAppointmentById(appointmentId, updatedAppointment).subscribe(
      () => {
        alert('Appointment updated successfully.');
      },
      error => {
        console.error('Error updating appointment:', error);
        alert('Error updating appointment.');
      }
    );
  }


  deleteAppointment(index: number): void {
    const appointmentsArray = this.appointmentsForm.get('appointmentsFA') as FormArray;
    const appointmentGroup = appointmentsArray.at(index) as FormGroup;

    if (appointmentGroup) {
      const appointmentId = appointmentGroup.get('appointmentId')?.value;
      if (appointmentId) {
        this.appointmentService.deleteAppointmentById(appointmentId).subscribe(
          () => {
            appointmentsArray.removeAt(index);
            alert('Appointment deleted successfully.');
          },
          error => {
            console.error('Error deleting appointment:', error);
            alert('Error deleting appointment.');
          }
        );
      } else {
        console.error('Error: Appointment ID control is undefined.');
        alert('Error deleting appointment.');
      }
    } else {
      console.error('Error: Appointment group is undefined.');
      alert('Error deleting appointment.');
    }
  }

  isModified(index: number): boolean {
    const appointmentsArray = this.appointmentsForm.get('appointmentsFA') as FormArray;
    const appointmentGroup = appointmentsArray.at(index) as FormGroup;
    const startDateTimeControl = appointmentGroup.get('startDateTime');
    const approvalStatusControl = appointmentGroup.get('approvalStatus');

    return startDateTimeControl.dirty || startDateTimeControl.touched || approvalStatusControl.dirty || approvalStatusControl.touched;
  }
}
