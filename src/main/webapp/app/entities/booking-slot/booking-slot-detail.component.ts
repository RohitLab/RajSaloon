import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBookingSlot } from 'app/shared/model/booking-slot.model';

@Component({
  selector: 'jhi-booking-slot-detail',
  templateUrl: './booking-slot-detail.component.html',
})
export class BookingSlotDetailComponent implements OnInit {
  bookingSlot: IBookingSlot | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookingSlot }) => (this.bookingSlot = bookingSlot));
  }

  previousState(): void {
    window.history.back();
  }
}
