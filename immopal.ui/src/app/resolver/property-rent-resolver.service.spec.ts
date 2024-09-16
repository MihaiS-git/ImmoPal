import { TestBed } from '@angular/core/testing';

import { PropertyRentResolverService } from './property-rent-resolver.service';

describe('PropertyRentResolverService', () => {
  let service: PropertyRentResolverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PropertyRentResolverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
