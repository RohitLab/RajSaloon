import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAdminUser } from 'app/shared/model/admin-user.model';
import { AdminUserService } from './admin-user.service';

@Component({
  templateUrl: './admin-user-delete-dialog.component.html',
})
export class AdminUserDeleteDialogComponent {
  adminUser?: IAdminUser;

  constructor(protected adminUserService: AdminUserService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.adminUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('adminUserListModification');
      this.activeModal.close();
    });
  }
}
