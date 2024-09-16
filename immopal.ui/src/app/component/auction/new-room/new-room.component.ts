import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuctionService } from '../../../service/auction.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-new-room',
  templateUrl: './new-room.component.html',
  styleUrls: ['./new-room.component.css'],
  providers: [DatePipe]
})
export class NewRoomComponent {
  newRoomForm: FormGroup;

  constructor(private fb: FormBuilder, private auctionService: AuctionService, private router: Router, private datePipe: DatePipe) {
    this.initForm();
  }

  initForm() {
    this.newRoomForm = this.fb.group({
      propertyId: ['', [Validators.required, Validators.min(1)]],
      startDate: ['', Validators.required]
    });
  }

  onSubmitForm() {
    if (this.newRoomForm.invalid) {
      return;
    }

    const roomToCreate = {
      propertyId: this.newRoomForm.value.propertyId,
      startDate: this.datePipe.transform(new Date(this.newRoomForm.value.startDate), 'yyyy-MM-ddTHH:mm:ss')
    }

    this.auctionService.createNewRoom(roomToCreate).subscribe(
      (res) => {
        alert('New Auction Room created!');
        this.newRoomForm.reset();
        this.router.navigate(['/auctions/rooms-list']);
      },
      (error) => {
        console.error('Failed to create new Auction Room:', error);
        alert('Failed to create new Auction Room: ' + error);
      }
    );
  }
}
