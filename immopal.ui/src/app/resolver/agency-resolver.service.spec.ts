import { TestBed } from '@angular/core/testing';

import { AgencyResolverService } from './agency-resolver.service';

describe('AgencyResolverService', () => {
  let service: AgencyResolverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AgencyResolverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
