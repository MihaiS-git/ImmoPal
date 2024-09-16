import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve } from '@angular/router';
import { PropertyService } from '../service/property.service';
import { Observable } from 'rxjs';
import { PropertyDto } from '../dto/propertyDto.model';

@Injectable({
  providedIn: 'root'
})
export class PropertyResolverService implements Resolve<any> {

  constructor(private propertyService: PropertyService) { }

  resolve(route: ActivatedRouteSnapshot): Observable<PropertyDto> {
    const propertyId = +route.paramMap.get('propertyId');
    return this.propertyService.getPropertyById(propertyId);
  }
}
