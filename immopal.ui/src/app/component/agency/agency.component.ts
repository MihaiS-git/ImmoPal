import { Component, OnInit, Output } from '@angular/core';
import { Agency } from '../../model/agency.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-agency',
  templateUrl: './agency.component.html',
  styleUrls: ['./agency.component.css']
})
export class AgencyComponent implements OnInit {
  @Output() agencies: Agency[];

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.data.subscribe(data => {
      this.agencies = data['agencies'];
    });
  }
}
