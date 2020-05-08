import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEmailTracking, EmailTracking } from 'app/shared/model/email-tracking.model';
import { EmailTrackingService } from './email-tracking.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

@Component({
  selector: 'jhi-email-tracking-update',
  templateUrl: './email-tracking-update.component.html'
})
export class EmailTrackingUpdateComponent implements OnInit {
  isSaving = false;
  userprofiles: IUserProfile[] = [];

  editForm = this.fb.group({
    id: [],
    toEmail: [null, [Validators.required, Validators.maxLength(200)]],
    subject: [null, [Validators.maxLength(500)]],
    message: [null, [Validators.maxLength(255)]],
    type: [null, [Validators.required, Validators.maxLength(200)]],
    createdAt: [null, [Validators.required]],
    receiverId: [null, Validators.required],
    createdById: [null, Validators.required]
  });

  constructor(
    protected emailTrackingService: EmailTrackingService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emailTracking }) => {
      if (!emailTracking.id) {
        const today = moment().startOf('day');
        emailTracking.createdAt = today;
      }

      this.updateForm(emailTracking);

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));
    });
  }

  updateForm(emailTracking: IEmailTracking): void {
    this.editForm.patchValue({
      id: emailTracking.id,
      toEmail: emailTracking.toEmail,
      subject: emailTracking.subject,
      message: emailTracking.message,
      type: emailTracking.type,
      createdAt: emailTracking.createdAt ? emailTracking.createdAt.format(DATE_TIME_FORMAT) : null,
      receiverId: emailTracking.receiverId,
      createdById: emailTracking.createdById
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emailTracking = this.createFromForm();
    if (emailTracking.id !== undefined) {
      this.subscribeToSaveResponse(this.emailTrackingService.update(emailTracking));
    } else {
      this.subscribeToSaveResponse(this.emailTrackingService.create(emailTracking));
    }
  }

  private createFromForm(): IEmailTracking {
    return {
      ...new EmailTracking(),
      id: this.editForm.get(['id'])!.value,
      toEmail: this.editForm.get(['toEmail'])!.value,
      subject: this.editForm.get(['subject'])!.value,
      message: this.editForm.get(['message'])!.value,
      type: this.editForm.get(['type'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      receiverId: this.editForm.get(['receiverId'])!.value,
      createdById: this.editForm.get(['createdById'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmailTracking>>): void {
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

  trackById(index: number, item: IUserProfile): any {
    return item.id;
  }
}
