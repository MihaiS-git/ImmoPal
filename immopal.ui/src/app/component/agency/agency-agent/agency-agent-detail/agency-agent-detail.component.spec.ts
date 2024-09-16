import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgencyAgentDetailComponent } from './agency-agent-detail.component';

describe('AgencyAgentDetailComponent', () => {
  let component: AgencyAgentDetailComponent;
  let fixture: ComponentFixture<AgencyAgentDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AgencyAgentDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgencyAgentDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
