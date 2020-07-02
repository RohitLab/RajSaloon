import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RajsaloonTestModule } from '../../../test.module';
import { AdminUserDetailComponent } from 'app/entities/admin-user/admin-user-detail.component';
import { AdminUser } from 'app/shared/model/admin-user.model';

describe('Component Tests', () => {
  describe('AdminUser Management Detail Component', () => {
    let comp: AdminUserDetailComponent;
    let fixture: ComponentFixture<AdminUserDetailComponent>;
    const route = ({ data: of({ adminUser: new AdminUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [RajsaloonTestModule],
        declarations: [AdminUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AdminUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AdminUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load adminUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.adminUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
