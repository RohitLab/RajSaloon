import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { RajsaloonTestModule } from '../../../test.module';
import { AdminUserComponent } from 'app/entities/admin-user/admin-user.component';
import { AdminUserService } from 'app/entities/admin-user/admin-user.service';
import { AdminUser } from 'app/shared/model/admin-user.model';

describe('Component Tests', () => {
  describe('AdminUser Management Component', () => {
    let comp: AdminUserComponent;
    let fixture: ComponentFixture<AdminUserComponent>;
    let service: AdminUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RajsaloonTestModule],
        declarations: [AdminUserComponent],
      })
        .overrideTemplate(AdminUserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AdminUserComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AdminUserService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AdminUser(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.adminUsers && comp.adminUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
