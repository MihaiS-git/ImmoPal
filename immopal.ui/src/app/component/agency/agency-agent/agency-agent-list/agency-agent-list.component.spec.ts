import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgencyAgentListComponent } from './agency-agent-list.component';

describe('AgencyAgentListComponent', () => {
  let component: AgencyAgentListComponent;
  let fixture: ComponentFixture<AgencyAgentListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AgencyAgentListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgencyAgentListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
