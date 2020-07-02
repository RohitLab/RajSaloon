import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBookingSlot, BookingSlot } from 'app/shared/model/booking-slot.model';
import { BookingSlotService } from './booking-slot.service';
import { INewUser } from 'app/shared/model/new-user.model';
import { NewUserService } from 'app/entities/new-user/new-user.service';

@Component({
  selector: 'jhi-booking-slot-update',
  templateUrl: './booking-slot-update.component.html',
})
export class BookingSlotUpdateComponent implements OnInit {
  isSaving = false;
  newusers: INewUser[] = [];
  bookingDateDp: any;
  slotStartingDp: any;
  slotEndingDp: any;

  editForm = this.fb.group({
    id: [],
    bookingId: [],
    description: [],
    bookingDate: [null, [Validators.required]],
    slotStarting: [],
    slotEnding: [],
    newUserId: [],
  });

  constructor(
    protected bookingSlotService: BookingSlotService,
    protected newUserService: NewUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookingSlot }) => {
      this.updateForm(bookingSlot);

      this.newUserService.query().subscribe((res: HttpResponse<INewUser[]>) => (this.newusers = res.body || []));
    });
  }

  updateForm(bookingSlot: IBookingSlot): void {
    this.editForm.patchValue({
      id: bookingSlot.id,
      bookingId: bookingSlot.bookingId,
      description: bookingSlot.description,
      bookingDate: bookingSlot.bookingDate,
      slotStarting: bookingSlot.slotStarting,
      slotEnding: bookingSlot.slotEnding,
      newUserId: bookingSlot.newUserId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bookingSlot = this.createFromForm();
    if (bookingSlot.id !== undefined) {
      this.subscribeToSaveResponse(this.bookingSlotService.update(bookingSlot));
    } else {
      this.subscribeToSaveResponse(this.bookingSlotService.create(bookingSlot));
    }
  }

  private createFromForm(): IBookingSlot {
    return {
      ...new BookingSlot(),
      id: this.editForm.get(['id'])!.value,
      bookingId: this.editForm.get(['bookingId'])!.value,
      description: this.editForm.get(['description'])!.value,
      bookingDate: this.editForm.get(['bookingDate'])!.value,
      slotStarting: this.editForm.get(['slotStarting'])!.value,
      slotEnding: this.editForm.get(['slotEnding'])!.value,
      newUserId: this.editForm.get(['newUserId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBookingSlot>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: INewUser): any {
    return item.id;
  }
}
