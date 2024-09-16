import { TestBed } from '@angular/core/testing';

import { PropertySaleResolverService } from './property-sale-resolver.service';

describe('PropertySaleResolverService', () => {
  let service: PropertySaleResolverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PropertySaleResolverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
