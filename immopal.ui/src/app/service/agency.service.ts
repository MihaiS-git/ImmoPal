import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, Subject, tap, throwError } from 'rxjs';
import { Agency } from '../model/agency.model';
import { AgentDto } from '../dto/agentDto.model';
import { PropertyDto } from '../dto/propertyDto.model';

@Injectable({
  providedIn: 'root'
})
export class AgencyService {
  private baseUrl = '/api/agencies'
  agenciesChanged = new Subject<Agency[]>();
  agentsChanged = new Subject<AgentDto[]>();
  private agencies: Agency[] = [];
  private agents: AgentDto[] = [];

  constructor(private http: HttpClient) { }

  fetchAgencies(): Observable<Agency[]> {
    return this.http.get<Agency[]>(`${this.baseUrl}`).pipe(
      tap((agencies) => {
        this.setAgencies(agencies);
      }),
      catchError((error) => {
        console.error('Error fetching agencies:', error);
        return throwError(error);
      })
    );
  }

  setAgencies(agencies: Agency[]) {
    this.agencies = agencies;
    this.agenciesChanged.next(this.agencies.slice());
  }

  getAgencies() {
    return this.agencies.slice();
  }

  searchAgency(id: number): Agency {
    return this.agencies.find(
      (a) => a.id === id
    ) as Agency;
  }

  getAgentsbyAgencyId(id: number): Observable<AgentDto[]> {
    return this.http.get<AgentDto[]>(`${this.baseUrl}/${id}/agents`).pipe(
      tap((agents) => {
        this.setAgents(agents);
      }),
      catchError((error) => {
        console.error('Error fetching agents:', error);
        return throwError(error);
      })
    );
  }

  setAgents(agents: AgentDto[]) {
    this.agents = agents;
    this.agentsChanged.next(this.agents.slice());
  }

  getAgents() {
    return this.agents.slice();
  }

  getPropertiesByAgencyId(id: number): Observable<PropertyDto[]> {
    return this.http.get<PropertyDto[]>(`${this.baseUrl}/${id}/properties`);
  }
}
