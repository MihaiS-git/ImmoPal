import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgencyPropertyComponent } from './agency-property.component';

describe('AgencyPropertyComponent', () => {
  let component: AgencyPropertyComponent;
  let fixture: ComponentFixture<AgencyPropertyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AgencyPropertyComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgencyPropertyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
