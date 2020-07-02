import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { RajsaloonTestModule } from '../../../test.module';
import { AdminUserUpdateComponent } from 'app/entities/admin-user/admin-user-update.component';
import { AdminUserService } from 'app/entities/admin-user/admin-user.service';
import { AdminUser } from 'app/shared/model/admin-user.model';

describe('Component Tests', () => {
  describe('AdminUser Management Update Component', () => {
    let comp: AdminUserUpdateComponent;
    let fixture: ComponentFixture<AdminUserUpdateComponent>;
    let service: AdminUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RajsaloonTestModule],
        declarations: [AdminUserUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AdminUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdminUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdminUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AdminUser(123);
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
        const entity = new AdminUser();
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
