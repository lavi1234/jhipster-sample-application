import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { INotificationReceiver, NotificationReceiver } from 'app/shared/model/notification-receiver.model';
import { NotificationReceiverService } from './notification-receiver.service';
import { INotification } from 'app/shared/model/notification.model';
import { NotificationService } from 'app/entities/notification/notification.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

type SelectableEntity = INotification | IUserProfile;

@Component({
  selector: 'jhi-notification-receiver-update',
  templateUrl: './notification-receiver-update.component.html'
})
export class NotificationReceiverUpdateComponent implements OnInit {
  isSaving = false;
  notifications: INotification[] = [];
  userprofiles: IUserProfile[] = [];

  editForm = this.fb.group({
    id: [],
    readStatus: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    notificationId: [null, Validators.required],
    userProfileId: [null, Validators.required]
  });

  constructor(
    protected notificationReceiverService: NotificationReceiverService,
    protected notificationService: NotificationService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ notificationReceiver }) => {
      if (!notificationReceiver.id) {
        const today = moment().startOf('day');
        notificationReceiver.updatedAt = today;
      }

      this.updateForm(notificationReceiver);

      this.notificationService.query().subscribe((res: HttpResponse<INotification[]>) => (this.notifications = res.body || []));

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));
    });
  }

  updateForm(notificationReceiver: INotificationReceiver): void {
    this.editForm.patchValue({
      id: notificationReceiver.id,
      readStatus: notificationReceiver.readStatus,
      updatedAt: notificationReceiver.updatedAt ? notificationReceiver.updatedAt.format(DATE_TIME_FORMAT) : null,
      notificationId: notificationReceiver.notificationId,
      userProfileId: notificationReceiver.userProfileId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const notificationReceiver = this.createFromForm();
    if (notificationReceiver.id !== undefined) {
      this.subscribeToSaveResponse(this.notificationReceiverService.update(notificationReceiver));
    } else {
      this.subscribeToSaveResponse(this.notificationReceiverService.create(notificationReceiver));
    }
  }

  private createFromForm(): INotificationReceiver {
    return {
      ...new NotificationReceiver(),
      id: this.editForm.get(['id'])!.value,
      readStatus: this.editForm.get(['readStatus'])!.value,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      notificationId: this.editForm.get(['notificationId'])!.value,
      userProfileId: this.editForm.get(['userProfileId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotificationReceiver>>): void {
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
