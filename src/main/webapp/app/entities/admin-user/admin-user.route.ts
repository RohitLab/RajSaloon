import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAdminUser, AdminUser } from 'app/shared/model/admin-user.model';
import { AdminUserService } from './admin-user.service';
import { AdminUserComponent } from './admin-user.component';
import { AdminUserDetailComponent } from './admin-user-detail.component';
import { AdminUserUpdateComponent } from './admin-user-update.component';

@Injectable({ providedIn: 'root' })
export class AdminUserResolve implements Resolve<IAdminUser> {
  constructor(private service: AdminUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAdminUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((adminUser: HttpResponse<AdminUser>) => {
          if (adminUser.body) {
            return of(adminUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AdminUser());
  }
}

export const adminUserRoute: Routes = [
  {
    path: '',
    component: AdminUserComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AdminUsers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AdminUserDetailComponent,
    resolve: {
      adminUser: AdminUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AdminUsers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AdminUserUpdateComponent,
    resolve: {
      adminUser: AdminUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AdminUsers',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AdminUserUpdateComponent,
    resolve: {
      adminUser: AdminUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'AdminUsers',
    },
    canActivate: [UserRouteAccessService],
  },
];
