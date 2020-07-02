import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBookingSlot } from 'app/shared/model/booking-slot.model';

type EntityResponseType = HttpResponse<IBookingSlot>;
type EntityArrayResponseType = HttpResponse<IBookingSlot[]>;

@Injectable({ providedIn: 'root' })
export class BookingSlotService {
  public resourceUrl = SERVER_API_URL + 'api/booking-slots';

  constructor(protected http: HttpClient) {}

  create(bookingSlot: IBookingSlot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bookingSlot);
    return this.http
      .post<IBookingSlot>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bookingSlot: IBookingSlot): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bookingSlot);
    return this.http
      .put<IBookingSlot>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBookingSlot>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBookingSlot[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bookingSlot: IBookingSlot): IBookingSlot {
    const copy: IBookingSlot = Object.assign({}, bookingSlot, {
      bookingDate: bookingSlot.bookingDate && bookingSlot.bookingDate.isValid() ? bookingSlot.bookingDate.format(DATE_FORMAT) : undefined,
      slotStarting:
        bookingSlot.slotStarting && bookingSlot.slotStarting.isValid() ? bookingSlot.slotStarting.format(DATE_FORMAT) : undefined,
      slotEnding: bookingSlot.slotEnding && bookingSlot.slotEnding.isValid() ? bookingSlot.slotEnding.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.bookingDate = res.body.bookingDate ? moment(res.body.bookingDate) : undefined;
      res.body.slotStarting = res.body.slotStarting ? moment(res.body.slotStarting) : undefined;
      res.body.slotEnding = res.body.slotEnding ? moment(res.body.slotEnding) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((bookingSlot: IBookingSlot) => {
        bookingSlot.bookingDate = bookingSlot.bookingDate ? moment(bookingSlot.bookingDate) : undefined;
        bookingSlot.slotStarting = bookingSlot.slotStarting ? moment(bookingSlot.slotStarting) : undefined;
        bookingSlot.slotEnding = bookingSlot.slotEnding ? moment(bookingSlot.slotEnding) : undefined;
      });
    }
    return res;
  }
}
