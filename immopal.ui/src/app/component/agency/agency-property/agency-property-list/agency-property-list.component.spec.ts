import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgencyPropertyListComponent } from './agency-property-list.component';

describe('AgencyPropertyListComponent', () => {
  let component: AgencyPropertyListComponent;
  let fixture: ComponentFixture<AgencyPropertyListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AgencyPropertyListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgencyPropertyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
