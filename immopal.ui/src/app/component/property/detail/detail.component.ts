import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyService } from '../../../service/property.service';
import { PropertyDto } from '../../../dto/propertyDto.model';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrl: './detail.component.css'
})
export class DetailComponent implements OnInit {
  @Input() property: PropertyDto;

  constructor(private route: ActivatedRoute, private propertyService: PropertyService, private router: Router) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const propertyId = +params.get('id');
      if (propertyId) {
        this.property = this.findPropertyById(propertyId);
      }
    });
  }

  findPropertyById(propertyId: number): PropertyDto {
    return this.propertyService.getProperties().find(p => p.id === propertyId);
  }

  navigateToPropertyPage(propertyId: number): void {
    this.router.navigate(['/properties', propertyId]);
  }

}
