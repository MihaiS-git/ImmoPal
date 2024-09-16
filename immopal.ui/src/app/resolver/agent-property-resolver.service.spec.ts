import { TestBed } from '@angular/core/testing';

import { AgentPropertyResolverService } from './agent-property-resolver.service';

describe('AgentPropertyResolverService', () => {
  let service: AgentPropertyResolverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AgentPropertyResolverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
