import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdminUser } from 'app/shared/model/admin-user.model';

@Component({
  selector: 'jhi-admin-user-detail',
  templateUrl: './admin-user-detail.component.html',
})
export class AdminUserDetailComponent implements OnInit {
  adminUser: IAdminUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminUser }) => (this.adminUser = adminUser));
  }

  previousState(): void {
    window.history.back();
  }
}
