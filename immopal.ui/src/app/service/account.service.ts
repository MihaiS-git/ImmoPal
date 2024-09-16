import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Person } from '../model/person.model';
import { Observable } from 'rxjs';
import { PersonDto } from '../dto/personDto.model';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private baseUrl = '/api/persons'

  constructor(private http: HttpClient) { }

  getAllPersons(): Observable<Person[]> {
    return this.http.get<Array<Person>>(`${this.baseUrl}`);
  }

  getPersonById(id: number): Observable<Person> {
    return this.http.get<any>(`${this.baseUrl}/id/${id}`);
  }

  updatePerson(id: number, person: PersonDto): Observable<Person> {
    return this.http.put<Person>(`${this.baseUrl}/${id}`, person);
  }
}
