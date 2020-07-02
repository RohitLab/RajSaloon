import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { RajsaloonSharedModule } from 'app/shared/shared.module';
import { NewUserComponent } from './new-user.component';
import { NewUserDetailComponent } from './new-user-detail.component';
import { NewUserUpdateComponent } from './new-user-update.component';
import { NewUserDeleteDialogComponent } from './new-user-delete-dialog.component';
import { newUserRoute } from './new-user.route';

@NgModule({
  imports: [RajsaloonSharedModule, RouterModule.forChild(newUserRoute)],
  declarations: [NewUserComponent, NewUserDetailComponent, NewUserUpdateComponent, NewUserDeleteDialogComponent],
  entryComponents: [NewUserDeleteDialogComponent],
})
export class RajsaloonNewUserModule {}
