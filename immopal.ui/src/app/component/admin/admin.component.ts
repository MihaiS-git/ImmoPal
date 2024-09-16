import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../service/user.service';
import { catchError, of, tap } from 'rxjs';
import { User } from '../../model/user.model';
import { Person } from '../../model/person.model';
import { UserDto } from '../../dto/userDto.model';
import { PersonDto } from '../../dto/personDto.model';
import { AccountService } from '../../service/account.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
  providers: [DatePipe]
})
export class AdminComponent implements OnInit {
  editUserForm: FormGroup;
  editPersonForm: FormGroup;

  constructor(private fb: FormBuilder,
    private userService: UserService,
    private accountService: AccountService,
    private datePipe: DatePipe) {
    this.editUserForm = this.fb.group({
      usersFA: this.fb.array([]),
    });

    this.editPersonForm = this.fb.group({
      personsFA: this.fb.array([]),
    });
  }

  ngOnInit(): void {
    this.loadUsers();
    this.loadPersons();

    this.editUserForm.valueChanges.subscribe(value => {
      console.log('editUserForm value changes:', value);
    });

    this.editPersonForm.valueChanges.subscribe(value => {
      console.log('editPersonForm value changes:', value);
    });
  }

  loadUsers(): void {
    const usersArray = this.editUserForm.get('usersFA') as FormArray;

    this.userService.getAllUsers().pipe(
      catchError((err) => {
        console.error('Error fetching user details:', err);
        return of(null);
      }),
      tap(resData => {
        if (resData) {
          resData.forEach(user => {
            usersArray.push(this.createUserFormGroup(user));
          });
        }
      })
    ).subscribe();
  }

  createUserFormGroup(user: User): FormGroup {
    return this.fb.group({
      userId: [user.userId, Validators.required],
      email: [user.email, Validators.required],
      role: [user.role, Validators.required],
      personId: [user.personId, Validators.required],
      accountNonExpired: [user.accountNonExpired, Validators.required],
      accountNonLocked: [user.accountNonLocked, Validators.required],
      credentialsNonExpired: [user.credentialsNonExpired, Validators.required],
      enabled: [user.enabled, Validators.required]
    });
  }

  loadPersons(): void {
    const personsArray = this.editPersonForm.get('personsFA') as FormArray;
    this.accountService.getAllPersons().pipe(
      catchError((err) => {
        console.error('Error fetching person details:', err);
        return of(null);
      }),
      tap(resData => {
        if (resData) {
          resData.forEach(person => {
            personsArray.push(this.createPersonFormGroup(person));
          });
        } else {
          console.error('Person details not found.');
        }
      })
    ).subscribe();
  }

  createPersonFormGroup(person: Person): FormGroup {
    return this.fb.group({
      personIdPF: [person.id, Validators.required],
      firstName: [person.firstName, Validators.required],
      lastName: [person.lastName, Validators.required],
      phoneNumber: [person.phoneNumber, Validators.required],
      dateOfBirth: [person.dateOfBirth, Validators.required],
      address: [person.address, Validators.required],
      pictureUrl: [person.pictureUrl],
      userIdPF: [person.userId]
    });
  }

  updateUser(index: number): void {
    const userFormGroup = (this.editUserForm.get('usersFA') as FormArray).at(index);

    const updatedUserData: UserDto = {
      userId: userFormGroup.get('userId').value,
      email: userFormGroup.get('email').value,
      role: userFormGroup.get('role').value,
      personId: userFormGroup.get('personId').value,
      accountNonExpired: userFormGroup.get('accountNonExpired').value,
      accountNonLocked: userFormGroup.get('accountNonLocked').value,
      credentialsNonExpired: userFormGroup.get('credentialsNonExpired').value,
      enabled: userFormGroup.get('enabled').value,
    };

    this.userService.updateUser(updatedUserData.userId, updatedUserData).subscribe(
      () => {
        alert('User updated successfully.');
      },
      (error) => {
        console.error('Error updating user:', error);
        alert('Error updating user.');
      }
    );
  }

  deleteUser(index: number): void {
    const usersFormArray = this.editUserForm.get('usersFA') as FormArray;
    const userFormGroup = usersFormArray.at(index) as FormGroup;
    const userId = userFormGroup.get('userId').value;

    this.userService.deleteUser(userId).subscribe(
      () => {
        usersFormArray.removeAt(index);
        alert('User deleted successfully.');
      },
      (error) => {
        console.error('Error deleting user:', error);
        alert('Error deleting user.');

      }
    );
  }

  updatePerson(index: number): void {
    const personFormGroup = (this.editPersonForm.get('personsFA') as FormArray).at(index);
    const updatedPersonData: PersonDto = {
      personId: personFormGroup.get('personIdPF').value,
      firstName: personFormGroup.get('firstName').value,
      lastName: personFormGroup.get('lastName').value,
      phoneNumber: personFormGroup.get('phoneNumber').value,
      dateOfBirth: this.datePipe.transform(new Date(personFormGroup.get('dateOfBirth').value), 'yyyy-MM-dd'),
      address: personFormGroup.get('address').value,
      pictureUrl: personFormGroup.get('pictureUrl').value,
    };

    this.accountService.updatePerson(updatedPersonData.personId, updatedPersonData).subscribe(
      () => {
        alert('User updated successfully.');
      },
      (error) => {
        console.error('Error updating user:', error);
        alert('Error updating user.');
      }
    );
  }

  deletePerson(index: number): void {
    const personsFormArray = this.editPersonForm.get('personsFA') as FormArray;
    const personFormGroup = personsFormArray.at(index) as FormGroup;
    const userId = personFormGroup.get('userIdPF').value;

    this.userService.deleteUser(userId).subscribe(
      () => {
        personsFormArray.removeAt(index);
        alert("Person deleted successfully.");
      },
      (error) => {
        console.error('Error deleting person:', error);
        alert("Error deleting person.");
      }
    );
  }

}
