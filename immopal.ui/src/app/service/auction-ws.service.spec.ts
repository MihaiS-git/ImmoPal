import { TestBed } from '@angular/core/testing';

import { AuctionWsService } from './auction-ws.service';

describe('AuctionWsService', () => {
  let service: AuctionWsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuctionWsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
