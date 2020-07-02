import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RajsaloonSharedModule } from 'app/shared/shared.module';
import { BookingSlotComponent } from './booking-slot.component';
import { BookingSlotDetailComponent } from './booking-slot-detail.component';
import { BookingSlotUpdateComponent } from './booking-slot-update.component';
import { BookingSlotDeleteDialogComponent } from './booking-slot-delete-dialog.component';
import { bookingSlotRoute } from './booking-slot.route';

@NgModule({
  imports: [RajsaloonSharedModule, RouterModule.forChild(bookingSlotRoute)],
  declarations: [BookingSlotComponent, BookingSlotDetailComponent, BookingSlotUpdateComponent, BookingSlotDeleteDialogComponent],
  entryComponents: [BookingSlotDeleteDialogComponent],
})
export class RajsaloonBookingSlotModule {}
