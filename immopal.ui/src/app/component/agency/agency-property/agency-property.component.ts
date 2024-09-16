import { Component, OnInit } from '@angular/core';
import { PropertyDto } from '../../../dto/propertyDto.model';
import { ActivatedRoute } from '@angular/router';
import { AgencyService } from '../../../service/agency.service';

@Component({
  selector: 'app-agency-property',
  templateUrl: './agency-property.component.html',
  styleUrl: './agency-property.component.css'
})
export class AgencyPropertyComponent implements OnInit{
  properties: PropertyDto[] = [];
  agencyId!: number;

  constructor(private route: ActivatedRoute, private agencyService: AgencyService) { }

  ngOnInit(): void {
    this.route.parent?.paramMap.subscribe(param => {
      if (param) {
        this.agencyId = +param.get('agencyId')!;

        if (this.agencyId) {
          this.agencyService.getPropertiesByAgencyId(this.agencyId).subscribe(properties => {
            this.properties = properties;
          });
        }
      } else {
        console.log("The agencyId is not found in the activated route params");
      }
    });
  }

}
