import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RajsaloonTestModule } from '../../../test.module';
import { BookingSlotDetailComponent } from 'app/entities/booking-slot/booking-slot-detail.component';
import { BookingSlot } from 'app/shared/model/booking-slot.model';

describe('Component Tests', () => {
  describe('BookingSlot Management Detail Component', () => {
    let comp: BookingSlotDetailComponent;
    let fixture: ComponentFixture<BookingSlotDetailComponent>;
    const route = ({ data: of({ bookingSlot: new BookingSlot(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RajsaloonTestModule],
        declarations: [BookingSlotDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BookingSlotDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookingSlotDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bookingSlot on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bookingSlot).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
