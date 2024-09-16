import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { PropertyService } from '../service/property.service';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AgentPropertyResolverService implements Resolve<any> {
  constructor(private propertyService: PropertyService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {
    const page = 0;
    const pageSize = 8;
    const agentId = route.paramMap.get('agentId');
    return this.propertyService.getPropertiesByAgentIdPaginate(page, pageSize, +agentId);
  }
}
