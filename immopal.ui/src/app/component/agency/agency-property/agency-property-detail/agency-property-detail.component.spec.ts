import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgencyPropertyDetailComponent } from './agency-property-detail.component';

describe('AgencyPropertyDetailComponent', () => {
  let component: AgencyPropertyDetailComponent;
  let fixture: ComponentFixture<AgencyPropertyDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AgencyPropertyDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgencyPropertyDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
