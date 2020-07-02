import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RajsaloonTestModule } from '../../../test.module';
import { BookingSlotComponent } from 'app/entities/booking-slot/booking-slot.component';
import { BookingSlotService } from 'app/entities/booking-slot/booking-slot.service';
import { BookingSlot } from 'app/shared/model/booking-slot.model';

describe('Component Tests', () => {
  describe('BookingSlot Management Component', () => {
    let comp: BookingSlotComponent;
    let fixture: ComponentFixture<BookingSlotComponent>;
    let service: BookingSlotService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RajsaloonTestModule],
        declarations: [BookingSlotComponent],
      })
        .overrideTemplate(BookingSlotComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookingSlotComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookingSlotService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BookingSlot(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bookingSlots && comp.bookingSlots[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
