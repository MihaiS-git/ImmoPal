import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AgenciesDetailComponent } from './agencies-detail.component';

describe('AgenciesDetailComponent', () => {
  let component: AgenciesDetailComponent;
  let fixture: ComponentFixture<AgenciesDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AgenciesDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AgenciesDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
