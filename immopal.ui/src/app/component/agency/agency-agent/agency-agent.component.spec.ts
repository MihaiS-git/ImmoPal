import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgencyAgentComponent } from './agency-agent.component';

describe('AgencyAgentComponent', () => {
  let component: AgencyAgentComponent;
  let fixture: ComponentFixture<AgencyAgentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AgencyAgentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgencyAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
