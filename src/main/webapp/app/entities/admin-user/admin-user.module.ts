import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RajsaloonSharedModule } from 'app/shared/shared.module';
import { AdminUserComponent } from './admin-user.component';
import { AdminUserDetailComponent } from './admin-user-detail.component';
import { AdminUserUpdateComponent } from './admin-user-update.component';
import { AdminUserDeleteDialogComponent } from './admin-user-delete-dialog.component';
import { adminUserRoute } from './admin-user.route';

@NgModule({
  imports: [RajsaloonSharedModule, RouterModule.forChild(adminUserRoute)],
  declarations: [AdminUserComponent, AdminUserDetailComponent, AdminUserUpdateComponent, AdminUserDeleteDialogComponent],
  entryComponents: [AdminUserDeleteDialogComponent],
})
export class RajsaloonAdminUserModule {}
