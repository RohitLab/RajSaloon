import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RajsaloonTestModule } from '../../../test.module';
import { BookingSlotUpdateComponent } from 'app/entities/booking-slot/booking-slot-update.component';
import { BookingSlotService } from 'app/entities/booking-slot/booking-slot.service';
import { BookingSlot } from 'app/shared/model/booking-slot.model';

describe('Component Tests', () => {
  describe('BookingSlot Management Update Component', () => {
    let comp: BookingSlotUpdateComponent;
    let fixture: ComponentFixture<BookingSlotUpdateComponent>;
    let service: BookingSlotService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RajsaloonTestModule],
        declarations: [BookingSlotUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BookingSlotUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookingSlotUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookingSlotService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BookingSlot(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new BookingSlot();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
