import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { INotification, Notification } from 'app/shared/model/notification.model';
import { NotificationService } from './notification.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

@Component({
  selector: 'jhi-notification-update',
  templateUrl: './notification-update.component.html'
})
export class NotificationUpdateComponent implements OnInit {
  isSaving = false;
  userprofiles: IUserProfile[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required, Validators.maxLength(200)]],
    description: [null, [Validators.required, Validators.maxLength(255)]],
    bannerImage: [null, [Validators.maxLength(500)]],
    createdAt: [null, [Validators.required]],
    createdById: [null, Validators.required]
  });

  constructor(
    protected notificationService: NotificationService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notification }) => {
      if (!notification.id) {
        const today = moment().startOf('day');
        notification.createdAt = today;
      }

      this.updateForm(notification);

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));
    });
  }

  updateForm(notification: INotification): void {
    this.editForm.patchValue({
      id: notification.id,
      title: notification.title,
      description: notification.description,
      bannerImage: notification.bannerImage,
      createdAt: notification.createdAt ? notification.createdAt.format(DATE_TIME_FORMAT) : null,
      createdById: notification.createdById
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notification = this.createFromForm();
    if (notification.id !== undefined) {
      this.subscribeToSaveResponse(this.notificationService.update(notification));
    } else {
      this.subscribeToSaveResponse(this.notificationService.create(notification));
    }
  }

  private createFromForm(): INotification {
    return {
      ...new Notification(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      bannerImage: this.editForm.get(['bannerImage'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      createdById: this.editForm.get(['createdById'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotification>>): void {
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
