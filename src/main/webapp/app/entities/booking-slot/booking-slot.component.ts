import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBookingSlot } from 'app/shared/model/booking-slot.model';
import { BookingSlotService } from './booking-slot.service';
import { BookingSlotDeleteDialogComponent } from './booking-slot-delete-dialog.component';

@Component({
  selector: 'jhi-booking-slot',
  templateUrl: './booking-slot.component.html',
})
export class BookingSlotComponent implements OnInit, OnDestroy {
  bookingSlots?: IBookingSlot[];
  eventSubscriber?: Subscription;

  constructor(
    protected bookingSlotService: BookingSlotService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.bookingSlotService.query().subscribe((res: HttpResponse<IBookingSlot[]>) => (this.bookingSlots = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBookingSlots();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBookingSlot): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBookingSlots(): void {
    this.eventSubscriber = this.eventManager.subscribe('bookingSlotListModification', () => this.loadAll());
  }

  delete(bookingSlot: IBookingSlot): void {
    const modalRef = this.modalService.open(BookingSlotDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bookingSlot = bookingSlot;
  }
}
