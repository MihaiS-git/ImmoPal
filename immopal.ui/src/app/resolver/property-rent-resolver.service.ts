import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { PropertyService } from '../service/property.service';
import { map, Observable, of } from 'rxjs';
import { PropertyDto } from '../dto/propertyDto.model';

@Injectable({
  providedIn: 'root'
})
export class PropertyRentResolverService implements Resolve<any> {

  constructor(private propertyService: PropertyService) { }

  resolve(): Observable<PropertyDto[]> {
    const rentProperties = this.propertyService.getRentProperties();
    if (rentProperties.length === 0) {
      return this.propertyService.fetchProperties().pipe(
        map(properties => properties.filter(p => p.transactionType === 'RENT'))
      );
    } else {
      return of(rentProperties);
    }
  }

}
