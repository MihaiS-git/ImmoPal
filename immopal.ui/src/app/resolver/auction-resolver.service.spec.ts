import { TestBed } from '@angular/core/testing';

import { AuctionResolverService } from './auction-resolver.service';

describe('AuctionResolverService', () => {
  let service: AuctionResolverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuctionResolverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
