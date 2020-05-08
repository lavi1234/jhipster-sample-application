import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMessage, Message } from 'app/shared/model/message.model';
import { MessageService } from './message.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';
import { IEnquiry } from 'app/shared/model/enquiry.model';
import { EnquiryService } from 'app/entities/enquiry/enquiry.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';
import { IOffer } from 'app/shared/model/offer.model';
import { OfferService } from 'app/entities/offer/offer.service';

type SelectableEntity = IUserProfile | IEnquiry | IOrder | IOffer | IMessage;

@Component({
  selector: 'jhi-message-update',
  templateUrl: './message-update.component.html'
})
export class MessageUpdateComponent implements OnInit {
  isSaving = false;
  userprofiles: IUserProfile[] = [];
  enquiries: IEnquiry[] = [];
  orders: IOrder[] = [];
  offers: IOffer[] = [];
  messages: IMessage[] = [];

  editForm = this.fb.group({
    id: [],
    subject: [null, [Validators.required, Validators.maxLength(200)]],
    message: [null, [Validators.maxLength(255)]],
    createdAt: [null, [Validators.required]],
    apiResponse: [null, [Validators.maxLength(255)]],
    discussionType: [null, [Validators.required, Validators.maxLength(200)]],
    readStatus: [],
    fromId: [null, Validators.required],
    toId: [null, Validators.required],
    createdById: [null, Validators.required],
    enquiryId: [null, Validators.required],
    orderId: [null, Validators.required],
    offerId: [null, Validators.required],
    replyMessageId: []
  });

  constructor(
    protected messageService: MessageService,
    protected userProfileService: UserProfileService,
    protected enquiryService: EnquiryService,
    protected orderService: OrderService,
    protected offerService: OfferService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ message }) => {
      if (!message.id) {
        const today = moment().startOf('day');
        message.createdAt = today;
      }

      this.updateForm(message);

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));

      this.enquiryService.query().subscribe((res: HttpResponse<IEnquiry[]>) => (this.enquiries = res.body || []));

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));

      this.offerService.query().subscribe((res: HttpResponse<IOffer[]>) => (this.offers = res.body || []));

      this.messageService.query().subscribe((res: HttpResponse<IMessage[]>) => (this.messages = res.body || []));
    });
  }

  updateForm(message: IMessage): void {
    this.editForm.patchValue({
      id: message.id,
      subject: message.subject,
      message: message.message,
      createdAt: message.createdAt ? message.createdAt.format(DATE_TIME_FORMAT) : null,
      apiResponse: message.apiResponse,
      discussionType: message.discussionType,
      readStatus: message.readStatus,
      fromId: message.fromId,
      toId: message.toId,
      createdById: message.createdById,
      enquiryId: message.enquiryId,
      orderId: message.orderId,
      offerId: message.offerId,
      replyMessageId: message.replyMessageId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const message = this.createFromForm();
    if (message.id !== undefined) {
      this.subscribeToSaveResponse(this.messageService.update(message));
    } else {
      this.subscribeToSaveResponse(this.messageService.create(message));
    }
  }

  private createFromForm(): IMessage {
    return {
      ...new Message(),
      id: this.editForm.get(['id'])!.value,
      subject: this.editForm.get(['subject'])!.value,
      message: this.editForm.get(['message'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      apiResponse: this.editForm.get(['apiResponse'])!.value,
      discussionType: this.editForm.get(['discussionType'])!.value,
      readStatus: this.editForm.get(['readStatus'])!.value,
      fromId: this.editForm.get(['fromId'])!.value,
      toId: this.editForm.get(['toId'])!.value,
      createdById: this.editForm.get(['createdById'])!.value,
      enquiryId: this.editForm.get(['enquiryId'])!.value,
      orderId: this.editForm.get(['orderId'])!.value,
      offerId: this.editForm.get(['offerId'])!.value,
      replyMessageId: this.editForm.get(['replyMessageId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMessage>>): void {
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
