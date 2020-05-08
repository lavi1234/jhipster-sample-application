import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISubscriptionPlan, SubscriptionPlan } from 'app/shared/model/subscription-plan.model';
import { SubscriptionPlanService } from './subscription-plan.service';
import { ILocalization } from 'app/shared/model/localization.model';
import { LocalizationService } from 'app/entities/localization/localization.service';

@Component({
  selector: 'jhi-subscription-plan-update',
  templateUrl: './subscription-plan-update.component.html'
})
export class SubscriptionPlanUpdateComponent implements OnInit {
  isSaving = false;
  localizations: ILocalization[] = [];

  editForm = this.fb.group({
    id: [],
    validity: [null, [Validators.required, Validators.maxLength(100)]],
    price: [null, [Validators.required]],
    createdBy: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    nameId: [null, Validators.required],
    descriptionId: [null, Validators.required]
  });

  constructor(
    protected subscriptionPlanService: SubscriptionPlanService,
    protected localizationService: LocalizationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subscriptionPlan }) => {
      if (!subscriptionPlan.id) {
        const today = moment().startOf('day');
        subscriptionPlan.createdAt = today;
        subscriptionPlan.updatedAt = today;
      }

      this.updateForm(subscriptionPlan);

      this.localizationService.query().subscribe((res: HttpResponse<ILocalization[]>) => (this.localizations = res.body || []));
    });
  }

  updateForm(subscriptionPlan: ISubscriptionPlan): void {
    this.editForm.patchValue({
      id: subscriptionPlan.id,
      validity: subscriptionPlan.validity,
      price: subscriptionPlan.price,
      createdBy: subscriptionPlan.createdBy,
      createdAt: subscriptionPlan.createdAt ? subscriptionPlan.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: subscriptionPlan.updatedAt ? subscriptionPlan.updatedAt.format(DATE_TIME_FORMAT) : null,
      nameId: subscriptionPlan.nameId,
      descriptionId: subscriptionPlan.descriptionId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subscriptionPlan = this.createFromForm();
    if (subscriptionPlan.id !== undefined) {
      this.subscribeToSaveResponse(this.subscriptionPlanService.update(subscriptionPlan));
    } else {
      this.subscribeToSaveResponse(this.subscriptionPlanService.create(subscriptionPlan));
    }
  }

  private createFromForm(): ISubscriptionPlan {
    return {
      ...new SubscriptionPlan(),
      id: this.editForm.get(['id'])!.value,
      validity: this.editForm.get(['validity'])!.value,
      price: this.editForm.get(['price'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      nameId: this.editForm.get(['nameId'])!.value,
      descriptionId: this.editForm.get(['descriptionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubscriptionPlan>>): void {
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

  trackById(index: number, item: ILocalization): any {
    return item.id;
  }
}
