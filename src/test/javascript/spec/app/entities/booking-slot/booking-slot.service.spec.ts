import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { BookingSlotService } from 'app/entities/booking-slot/booking-slot.service';
import { IBookingSlot, BookingSlot } from 'app/shared/model/booking-slot.model';

describe('Service Tests', () => {
  describe('BookingSlot Service', () => {
    let injector: TestBed;
    let service: BookingSlotService;
    let httpMock: HttpTestingController;
    let elemDefault: IBookingSlot;
    let expectedResult: IBookingSlot | IBookingSlot[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(BookingSlotService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new BookingSlot(0, 0, 'AAAAAAA', currentDate, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            bookingDate: currentDate.format(DATE_FORMAT),
            slotStarting: currentDate.format(DATE_FORMAT),
            slotEnding: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a BookingSlot', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            bookingDate: currentDate.format(DATE_FORMAT),
            slotStarting: currentDate.format(DATE_FORMAT),
            slotEnding: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            bookingDate: currentDate,
            slotStarting: currentDate,
            slotEnding: currentDate,
          },
          returnedFromService
        );

        service.create(new BookingSlot()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BookingSlot', () => {
        const returnedFromService = Object.assign(
          {
            bookingId: 1,
            description: 'BBBBBB',
            bookingDate: currentDate.format(DATE_FORMAT),
            slotStarting: currentDate.format(DATE_FORMAT),
            slotEnding: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            bookingDate: currentDate,
            slotStarting: currentDate,
            slotEnding: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BookingSlot', () => {
        const returnedFromService = Object.assign(
          {
            bookingId: 1,
            description: 'BBBBBB',
            bookingDate: currentDate.format(DATE_FORMAT),
            slotStarting: currentDate.format(DATE_FORMAT),
            slotEnding: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            bookingDate: currentDate,
            slotStarting: currentDate,
            slotEnding: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a BookingSlot', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
