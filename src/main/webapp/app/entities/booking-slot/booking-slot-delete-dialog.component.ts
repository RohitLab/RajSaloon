import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBookingSlot } from 'app/shared/model/booking-slot.model';
import { BookingSlotService } from './booking-slot.service';

@Component({
  templateUrl: './booking-slot-delete-dialog.component.html',
})
export class BookingSlotDeleteDialogComponent {
  bookingSlot?: IBookingSlot;

  constructor(
    protected bookingSlotService: BookingSlotService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bookingSlotService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bookingSlotListModification');
      this.activeModal.close();
    });
  }
}
