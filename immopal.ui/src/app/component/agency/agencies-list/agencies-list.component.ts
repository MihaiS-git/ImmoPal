import { Component, OnDestroy, OnInit } from '@angular/core';
import { Agency } from '../../../model/agency.model';
import { Subscription } from 'rxjs';
import { AgencyService } from '../../../service/agency.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-agencies-list',
  templateUrl: './agencies-list.component.html',
  styleUrls: ['./agencies-list.component.css']
})
export class AgenciesListComponent implements OnInit, OnDestroy {
  agencySubscription: Subscription | null = null;
  agencies: Agency[] = [];

  constructor(private agencyService: AgencyService, private router: Router) { }

  ngOnInit(): void {
    this.agencySubscription = this.agencyService.agenciesChanged.subscribe(
      (agencies: Agency[]) => {
        this.agencies = agencies;
      }
    );

    this.agencyService.fetchAgencies().subscribe(
      (agencies: Agency[]) => {
        this.agencies = agencies;
      },
      (error) => {
        console.error('Error fetching agencies:', error);
      }
    );
  }

  ngOnDestroy(): void {
    if (this.agencySubscription) {
      this.agencySubscription.unsubscribe();
    }
  }

}
