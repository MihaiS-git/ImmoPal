import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, Subject, tap, throwError } from 'rxjs';
import { PropertyDto } from '../dto/propertyDto.model';

@Injectable({
  providedIn: 'root'
})
export class PropertyService {
  baseUrl = '/api/properties'
  properties: PropertyDto[] = [];
  propertiesChanged = new Subject<PropertyDto[]>();

  constructor(private http: HttpClient) { }

  fetchProperties() {
    return this.http.get<PropertyDto[]>(`${this.baseUrl}`).pipe(
      tap((data) => this.setProperties(data)),
      catchError((error) => {
        console.error('Error fetching properties', error);
        return throwError(error);
      })
    );
  }

  setProperties(data: PropertyDto[]) {
    this.properties = data;
    this.propertiesChanged.next(this.properties.slice());
  }

  getProperties() {
    if (this.properties) {
      return this.properties.slice();
    } else {
      this.fetchProperties();
      return this.properties.slice();
    }
  }

  getSaleProperties() {
    return this.properties.filter(p => p.transactionType === 'SALE').slice();
  }

  getRentProperties() {
    return this.properties.filter(p => p.transactionType === 'RENT').slice();
  }

  getPropertyById(propertyId: number): Observable<PropertyDto> {
    const property = this.properties.find(p => p.id === propertyId);
    if (property) {
      return of(property);
    } else {
      return this.http.get<PropertyDto>(`${this.baseUrl}/${propertyId}`).pipe(
        catchError(error => {
          console.error("Error fetching property by Id", error);
          return throwError(error);
        })
      );
    }
  }

  getPropertiesByAgentIdPaginate(thePage: number, thePageSize: number, agentId: number) {
    const searchUrl = `${this.baseUrl}/search/findAllWithImagesByAgentIdPaginated?agentId=${agentId}` +
      `&page=${thePage}&size=${thePageSize}`;
    return this.http.get<GetResponseProperties>(searchUrl);
  }
}

interface GetResponseProperties {
  _embedded: {
    properties: PropertyDto[];
  };
  page: {
    size: number;
    totalElements: number;
    totalPages: number;
    number: number;
  };
}

