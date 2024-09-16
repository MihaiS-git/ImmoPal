import { PropertyService } from './../../../service/property.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OwlOptions } from 'ngx-owl-carousel-o';
import { AppointmentItem } from '../../../model/appointmentItem.model';
import { AppointmentService } from '../../../service/appointment.service';
import { PropertyDto } from '../../../dto/propertyDto.model';


@Component({
  selector: 'app-property-page',
  templateUrl: './property-page.component.html',
  styleUrls: ['./property-page.component.css'],
})
export class PropertyPageComponent implements OnInit {
  customOptions: OwlOptions = {
    loop: true,
    mouseDrag: true,
    touchDrag: false,
    pullDrag: false,
    mergeFit: true,
    nav: true,
    navText: ['<<', '>>'],
    center: true,
    dots: true,
    autoplay: true,
    autoplayTimeout: 5000,
    autoplayHoverPause: true,
    navSpeed: 700,
    responsive: {
      0: {
        items: 1
      },
      400: {
        items: 1
      },
      740: {
        items: 1
      },
      940: {
        items: 1
      }
    },
  };

  property: PropertyDto;
  propertyId: number;
  isLoggedIn = false;

  constructor(private propertyService: PropertyService, private appointmentService: AppointmentService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    const params = this.route.snapshot.paramMap;
    const propertyId = +params.get('propertyId');
    this.propertyService.getPropertyById(propertyId).subscribe(
      (property) => {
        this.property = property;
        this.propertyId = propertyId;
      }
    );
    if (localStorage.getItem('userData')) {
      this.isLoggedIn = true;
    }
  }

  bookAppointment() {
    const theAppointmentItem = new AppointmentItem(this.property);
    this.appointmentService.addToAppointment(theAppointmentItem);
    this.router.navigate(['/appointments']);
  }
}

