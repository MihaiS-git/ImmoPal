import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { map, Observable, of } from 'rxjs';
import { PropertyService } from '../service/property.service';
import { PropertyDto } from '../dto/propertyDto.model';

@Injectable({
  providedIn: 'root'
})
export class PropertySaleResolverService implements Resolve<any> {

  constructor(private propertyService: PropertyService) { }

  resolve(): Observable<PropertyDto[]> {
    const saleProperties = this.propertyService.getSaleProperties();
    if (saleProperties.length === 0) {
      return this.propertyService.fetchProperties().pipe(
        map(properties => properties.filter(p => p.transactionType === 'SALE'))
      );
    } else {
      return of(saleProperties);
    }
  }
}
