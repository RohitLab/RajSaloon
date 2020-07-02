import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAdminUser } from 'app/shared/model/admin-user.model';
import { AdminUserService } from './admin-user.service';
import { AdminUserDeleteDialogComponent } from './admin-user-delete-dialog.component';

@Component({
  selector: 'jhi-admin-user',
  templateUrl: './admin-user.component.html',
})
export class AdminUserComponent implements OnInit, OnDestroy {
  adminUsers?: IAdminUser[];
  eventSubscriber?: Subscription;

  constructor(protected adminUserService: AdminUserService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.adminUserService.query().subscribe((res: HttpResponse<IAdminUser[]>) => (this.adminUsers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAdminUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAdminUser): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAdminUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('adminUserListModification', () => this.loadAll());
  }

  delete(adminUser: IAdminUser): void {
    const modalRef = this.modalService.open(AdminUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.adminUser = adminUser;
  }
}
