import { Component, Input } from '@angular/core';
import { PropertyDto } from '../../../../dto/propertyDto.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-agency-property-list',
  templateUrl: './agency-property-list.component.html',
  styleUrl: './agency-property-list.component.css'
})
export class AgencyPropertyListComponent {
  @Input() properties: PropertyDto[] = [];

  constructor(private router: Router) { }

  navigateToProperty(propertyId: number) {
    this.router.navigate(['/properties', propertyId]);
  }
}
