import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Agency } from '../model/agency.model';
import { AgencyService } from '../service/agency.service';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AgencyResolverService implements Resolve<any>{

  constructor(private agencyService: AgencyService) { }

  resolve(): Observable<Agency[]> {
    const agencies = this.agencyService.getAgencies();
    if (agencies.length === 0) {
      return this.agencyService.fetchAgencies();
    } else {
      return of(agencies);
    }
  }
}
