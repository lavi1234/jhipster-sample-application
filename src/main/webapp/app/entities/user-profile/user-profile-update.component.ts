import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserProfile, UserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from './user-profile.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

type SelectableEntity = IUser | ICompany;

@Component({
  selector: 'jhi-user-profile-update',
  templateUrl: './user-profile-update.component.html'
})
export class UserProfileUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  companies: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    salutation: [null, [Validators.required, Validators.maxLength(200)]],
    firstName: [null, [Validators.required, Validators.maxLength(200)]],
    lastName: [null, [Validators.required, Validators.maxLength(200)]],
    profilePicture: [null, [Validators.maxLength(255)]],
    phoneNumber: [null, [Validators.required, Validators.maxLength(200)]],
    userType: [null, [Validators.required, Validators.maxLength(200)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    userId: [null, Validators.required],
    companyId: []
  });

  constructor(
    protected userProfileService: UserProfileService,
    protected userService: UserService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userProfile }) => {
      if (!userProfile.id) {
        const today = moment().startOf('day');
        userProfile.createdAt = today;
        userProfile.updatedAt = today;
      }

      this.updateForm(userProfile);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    });
  }

  updateForm(userProfile: IUserProfile): void {
    this.editForm.patchValue({
      id: userProfile.id,
      salutation: userProfile.salutation,
      firstName: userProfile.firstName,
      lastName: userProfile.lastName,
      profilePicture: userProfile.profilePicture,
      phoneNumber: userProfile.phoneNumber,
      userType: userProfile.userType,
      createdAt: userProfile.createdAt ? userProfile.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: userProfile.updatedAt ? userProfile.updatedAt.format(DATE_TIME_FORMAT) : null,
      userId: userProfile.userId,
      companyId: userProfile.companyId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userProfile = this.createFromForm();
    if (userProfile.id !== undefined) {
      this.subscribeToSaveResponse(this.userProfileService.update(userProfile));
    } else {
      this.subscribeToSaveResponse(this.userProfileService.create(userProfile));
    }
  }

  private createFromForm(): IUserProfile {
    return {
      ...new UserProfile(),
      id: this.editForm.get(['id'])!.value,
      salutation: this.editForm.get(['salutation'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      profilePicture: this.editForm.get(['profilePicture'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      userType: this.editForm.get(['userType'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      userId: this.editForm.get(['userId'])!.value,
      companyId: this.editForm.get(['companyId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserProfile>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
