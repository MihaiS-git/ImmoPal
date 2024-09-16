import { Component, Input } from '@angular/core';
import { PropertyDto } from '../../../../dto/propertyDto.model';

@Component({
  selector: 'app-agency-property-detail',
  templateUrl: './agency-property-detail.component.html',
  styleUrl: './agency-property-detail.component.css'
})
export class AgencyPropertyDetailComponent {
  @Input() property: PropertyDto;

}
