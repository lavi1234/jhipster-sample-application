import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITransactionHistory, TransactionHistory } from 'app/shared/model/transaction-history.model';
import { TransactionHistoryService } from './transaction-history.service';
import { ISubscriptionPlan } from 'app/shared/model/subscription-plan.model';
import { SubscriptionPlanService } from 'app/entities/subscription-plan/subscription-plan.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

type SelectableEntity = ISubscriptionPlan | IUserProfile;

@Component({
  selector: 'jhi-transaction-history-update',
  templateUrl: './transaction-history-update.component.html'
})
export class TransactionHistoryUpdateComponent implements OnInit {
  isSaving = false;
  subscriptionplans: ISubscriptionPlan[] = [];
  userprofiles: IUserProfile[] = [];

  editForm = this.fb.group({
    id: [],
    price: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    paymentGatewayToken: [null, [Validators.required, Validators.maxLength(255)]],
    paymentGatewayResponse: [null, [Validators.required, Validators.maxLength(255)]],
    status: [null, [Validators.required, Validators.maxLength(200)]],
    subscriptionPlanId: [null, Validators.required],
    userProfileId: [null, Validators.required]
  });

  constructor(
    protected transactionHistoryService: TransactionHistoryService,
    protected subscriptionPlanService: SubscriptionPlanService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transactionHistory }) => {
      if (!transactionHistory.id) {
        const today = moment().startOf('day');
        transactionHistory.createdAt = today;
      }

      this.updateForm(transactionHistory);

      this.subscriptionPlanService.query().subscribe((res: HttpResponse<ISubscriptionPlan[]>) => (this.subscriptionplans = res.body || []));

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));
    });
  }

  updateForm(transactionHistory: ITransactionHistory): void {
    this.editForm.patchValue({
      id: transactionHistory.id,
      price: transactionHistory.price,
      createdAt: transactionHistory.createdAt ? transactionHistory.createdAt.format(DATE_TIME_FORMAT) : null,
      paymentGatewayToken: transactionHistory.paymentGatewayToken,
      paymentGatewayResponse: transactionHistory.paymentGatewayResponse,
      status: transactionHistory.status,
      subscriptionPlanId: transactionHistory.subscriptionPlanId,
      userProfileId: transactionHistory.userProfileId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transactionHistory = this.createFromForm();
    if (transactionHistory.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionHistoryService.update(transactionHistory));
    } else {
      this.subscribeToSaveResponse(this.transactionHistoryService.create(transactionHistory));
    }
  }

  private createFromForm(): ITransactionHistory {
    return {
      ...new TransactionHistory(),
      id: this.editForm.get(['id'])!.value,
      price: this.editForm.get(['price'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      paymentGatewayToken: this.editForm.get(['paymentGatewayToken'])!.value,
      paymentGatewayResponse: this.editForm.get(['paymentGatewayResponse'])!.value,
      status: this.editForm.get(['status'])!.value,
      subscriptionPlanId: this.editForm.get(['subscriptionPlanId'])!.value,
      userProfileId: this.editForm.get(['userProfileId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionHistory>>): void {
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
