import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INewUser, NewUser } from 'app/shared/model/new-user.model';
import { NewUserService } from './new-user.service';

@Component({
  selector: 'jhi-new-user-update',
  templateUrl: './new-user-update.component.html',
})
export class NewUserUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userId: [null, [Validators.required]],
    name: [],
    phoneNumber: [],
  });

  constructor(protected newUserService: NewUserService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ newUser }) => {
      this.updateForm(newUser);
    });
  }

  updateForm(newUser: INewUser): void {
    this.editForm.patchValue({
      id: newUser.id,
      userId: newUser.userId,
      name: newUser.name,
      phoneNumber: newUser.phoneNumber,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const newUser = this.createFromForm();
    if (newUser.id !== undefined) {
      this.subscribeToSaveResponse(this.newUserService.update(newUser));
    } else {
      this.subscribeToSaveResponse(this.newUserService.create(newUser));
    }
  }

  private createFromForm(): INewUser {
    return {
      ...new NewUser(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      name: this.editForm.get(['name'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INewUser>>): void {
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
