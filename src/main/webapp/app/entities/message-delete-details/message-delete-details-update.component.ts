import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMessageDeleteDetails, MessageDeleteDetails } from 'app/shared/model/message-delete-details.model';
import { MessageDeleteDetailsService } from './message-delete-details.service';
import { IMessage } from 'app/shared/model/message.model';
import { MessageService } from 'app/entities/message/message.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

type SelectableEntity = IMessage | IUserProfile;

@Component({
  selector: 'jhi-message-delete-details-update',
  templateUrl: './message-delete-details-update.component.html'
})
export class MessageDeleteDetailsUpdateComponent implements OnInit {
  isSaving = false;
  messages: IMessage[] = [];
  userprofiles: IUserProfile[] = [];

  editForm = this.fb.group({
    id: [],
    deletedAt: [null, [Validators.required]],
    messageId: [null, Validators.required],
    deletedById: [null, Validators.required]
  });

  constructor(
    protected messageDeleteDetailsService: MessageDeleteDetailsService,
    protected messageService: MessageService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ messageDeleteDetails }) => {
      if (!messageDeleteDetails.id) {
        const today = moment().startOf('day');
        messageDeleteDetails.deletedAt = today;
      }

      this.updateForm(messageDeleteDetails);

      this.messageService.query().subscribe((res: HttpResponse<IMessage[]>) => (this.messages = res.body || []));

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));
    });
  }

  updateForm(messageDeleteDetails: IMessageDeleteDetails): void {
    this.editForm.patchValue({
      id: messageDeleteDetails.id,
      deletedAt: messageDeleteDetails.deletedAt ? messageDeleteDetails.deletedAt.format(DATE_TIME_FORMAT) : null,
      messageId: messageDeleteDetails.messageId,
      deletedById: messageDeleteDetails.deletedById
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const messageDeleteDetails = this.createFromForm();
    if (messageDeleteDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.messageDeleteDetailsService.update(messageDeleteDetails));
    } else {
      this.subscribeToSaveResponse(this.messageDeleteDetailsService.create(messageDeleteDetails));
    }
  }

  private createFromForm(): IMessageDeleteDetails {
    return {
      ...new MessageDeleteDetails(),
      id: this.editForm.get(['id'])!.value,
      deletedAt: this.editForm.get(['deletedAt'])!.value ? moment(this.editForm.get(['deletedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      messageId: this.editForm.get(['messageId'])!.value,
      deletedById: this.editForm.get(['deletedById'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMessageDeleteDetails>>): void {
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
