import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'new-user',
        loadChildren: () => import('./new-user/new-user.module').then(m => m.RajsaloonNewUserModule),
      },
      {
        path: 'booking-slot',
        loadChildren: () => import('./booking-slot/booking-slot.module').then(m => m.RajsaloonBookingSlotModule),
      },
      {
        path: 'admin-user',
        loadChildren: () => import('./admin-user/admin-user.module').then(m => m.RajsaloonAdminUserModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class RajsaloonEntityModule {}
