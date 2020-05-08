import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserSubscription, UserSubscription } from 'app/shared/model/user-subscription.model';
import { UserSubscriptionService } from './user-subscription.service';
import { ISubscriptionPlan } from 'app/shared/model/subscription-plan.model';
import { SubscriptionPlanService } from 'app/entities/subscription-plan/subscription-plan.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

type SelectableEntity = ISubscriptionPlan | IUserProfile;

@Component({
  selector: 'jhi-user-subscription-update',
  templateUrl: './user-subscription-update.component.html'
})
export class UserSubscriptionUpdateComponent implements OnInit {
  isSaving = false;
  subscriptionplans: ISubscriptionPlan[] = [];
  userprofiles: IUserProfile[] = [];
  validUptoDp: any;
  nextRenewalDp: any;

  editForm = this.fb.group({
    id: [],
    price: [null, [Validators.required]],
    validUpto: [null, [Validators.required]],
    paymentGatewayToken: [null, [Validators.required, Validators.maxLength(255)]],
    nextRenewal: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    subscriptionPlanId: [null, Validators.required],
    userProfileId: [null, Validators.required]
  });

  constructor(
    protected userSubscriptionService: UserSubscriptionService,
    protected subscriptionPlanService: SubscriptionPlanService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userSubscription }) => {
      if (!userSubscription.id) {
        const today = moment().startOf('day');
        userSubscription.createdAt = today;
        userSubscription.updatedAt = today;
      }

      this.updateForm(userSubscription);

      this.subscriptionPlanService.query().subscribe((res: HttpResponse<ISubscriptionPlan[]>) => (this.subscriptionplans = res.body || []));

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));
    });
  }

  updateForm(userSubscription: IUserSubscription): void {
    this.editForm.patchValue({
      id: userSubscription.id,
      price: userSubscription.price,
      validUpto: userSubscription.validUpto,
      paymentGatewayToken: userSubscription.paymentGatewayToken,
      nextRenewal: userSubscription.nextRenewal,
      createdAt: userSubscription.createdAt ? userSubscription.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: userSubscription.updatedAt ? userSubscription.updatedAt.format(DATE_TIME_FORMAT) : null,
      subscriptionPlanId: userSubscription.subscriptionPlanId,
      userProfileId: userSubscription.userProfileId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userSubscription = this.createFromForm();
    if (userSubscription.id !== undefined) {
      this.subscribeToSaveResponse(this.userSubscriptionService.update(userSubscription));
    } else {
      this.subscribeToSaveResponse(this.userSubscriptionService.create(userSubscription));
    }
  }

  private createFromForm(): IUserSubscription {
    return {
      ...new UserSubscription(),
      id: this.editForm.get(['id'])!.value,
      price: this.editForm.get(['price'])!.value,
      validUpto: this.editForm.get(['validUpto'])!.value,
      paymentGatewayToken: this.editForm.get(['paymentGatewayToken'])!.value,
      nextRenewal: this.editForm.get(['nextRenewal'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      subscriptionPlanId: this.editForm.get(['subscriptionPlanId'])!.value,
      userProfileId: this.editForm.get(['userProfileId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserSubscription>>): void {
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
