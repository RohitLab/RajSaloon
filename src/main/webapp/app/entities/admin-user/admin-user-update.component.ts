import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAdminUser, AdminUser } from 'app/shared/model/admin-user.model';
import { AdminUserService } from './admin-user.service';

@Component({
  selector: 'jhi-admin-user-update',
  templateUrl: './admin-user-update.component.html',
})
export class AdminUserUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userId: [],
    name: [],
    phoneNumber: [],
    encryptedPassword: [null, [Validators.required]],
  });

  constructor(protected adminUserService: AdminUserService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ adminUser }) => {
      this.updateForm(adminUser);
    });
  }

  updateForm(adminUser: IAdminUser): void {
    this.editForm.patchValue({
      id: adminUser.id,
      userId: adminUser.userId,
      name: adminUser.name,
      phoneNumber: adminUser.phoneNumber,
      encryptedPassword: adminUser.encryptedPassword,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const adminUser = this.createFromForm();
    if (adminUser.id !== undefined) {
      this.subscribeToSaveResponse(this.adminUserService.update(adminUser));
    } else {
      this.subscribeToSaveResponse(this.adminUserService.create(adminUser));
    }
  }

  private createFromForm(): IAdminUser {
    return {
      ...new AdminUser(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      name: this.editForm.get(['name'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      encryptedPassword: this.editForm.get(['encryptedPassword'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdminUser>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
