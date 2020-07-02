import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBookingSlot, BookingSlot } from 'app/shared/model/booking-slot.model';
import { BookingSlotService } from './booking-slot.service';
import { BookingSlotComponent } from './booking-slot.component';
import { BookingSlotDetailComponent } from './booking-slot-detail.component';
import { BookingSlotUpdateComponent } from './booking-slot-update.component';

@Injectable({ providedIn: 'root' })
export class BookingSlotResolve implements Resolve<IBookingSlot> {
  constructor(private service: BookingSlotService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBookingSlot> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bookingSlot: HttpResponse<BookingSlot>) => {
          if (bookingSlot.body) {
            return of(bookingSlot.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BookingSlot());
  }
}

export const bookingSlotRoute: Routes = [
  {
    path: '',
    component: BookingSlotComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BookingSlots',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BookingSlotDetailComponent,
    resolve: {
      bookingSlot: BookingSlotResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BookingSlots',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BookingSlotUpdateComponent,
    resolve: {
      bookingSlot: BookingSlotResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BookingSlots',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BookingSlotUpdateComponent,
    resolve: {
      bookingSlot: BookingSlotResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BookingSlots',
    },
    canActivate: [UserRouteAccessService],
  },
];
