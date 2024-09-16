import { Component, OnInit } from '@angular/core';
import { Agency } from '../../../model/agency.model';
import { ActivatedRoute, Router } from '@angular/router';
import { AgencyService } from '../../../service/agency.service';

@Component({
  selector: 'app-agencies-detail',
  templateUrl: './agencies-detail.component.html',
  styleUrls: ['./agencies-detail.component.css']
})
export class AgenciesDetailComponent implements OnInit {
  agency: Agency;
  agencyId: number;

  constructor(private route: ActivatedRoute, private agencyService: AgencyService, private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.agencyId = +params['agencyId'];
      this.agency = this.agencyService.searchAgency(this.agencyId);
    });
  }

  navigateToAgents() {
    this.router.navigate(['/agencies', this.agency.id, 'agents']);
  }

  navigateToProperties() {
    this.router.navigate(['/agencies', this.agency.id, 'properties']);
  }
}
