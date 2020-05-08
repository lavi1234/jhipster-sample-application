import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISubsriptionPlanFeature, SubsriptionPlanFeature } from 'app/shared/model/subsription-plan-feature.model';
import { SubsriptionPlanFeatureService } from './subsription-plan-feature.service';
import { ISubscriptionPlan } from 'app/shared/model/subscription-plan.model';
import { SubscriptionPlanService } from 'app/entities/subscription-plan/subscription-plan.service';
import { IAppFeatures } from 'app/shared/model/app-features.model';
import { AppFeaturesService } from 'app/entities/app-features/app-features.service';

type SelectableEntity = ISubscriptionPlan | IAppFeatures;

@Component({
  selector: 'jhi-subsription-plan-feature-update',
  templateUrl: './subsription-plan-feature-update.component.html'
})
export class SubsriptionPlanFeatureUpdateComponent implements OnInit {
  isSaving = false;
  subscriptionplans: ISubscriptionPlan[] = [];
  appfeatures: IAppFeatures[] = [];

  editForm = this.fb.group({
    id: [],
    subscriptionPlans: [null, Validators.required],
    appFeatures: [null, Validators.required]
  });

  constructor(
    protected subsriptionPlanFeatureService: SubsriptionPlanFeatureService,
    protected subscriptionPlanService: SubscriptionPlanService,
    protected appFeaturesService: AppFeaturesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subsriptionPlanFeature }) => {
      this.updateForm(subsriptionPlanFeature);

      this.subscriptionPlanService.query().subscribe((res: HttpResponse<ISubscriptionPlan[]>) => (this.subscriptionplans = res.body || []));

      this.appFeaturesService.query().subscribe((res: HttpResponse<IAppFeatures[]>) => (this.appfeatures = res.body || []));
    });
  }

  updateForm(subsriptionPlanFeature: ISubsriptionPlanFeature): void {
    this.editForm.patchValue({
      id: subsriptionPlanFeature.id,
      subscriptionPlans: subsriptionPlanFeature.subscriptionPlans,
      appFeatures: subsriptionPlanFeature.appFeatures
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subsriptionPlanFeature = this.createFromForm();
    if (subsriptionPlanFeature.id !== undefined) {
      this.subscribeToSaveResponse(this.subsriptionPlanFeatureService.update(subsriptionPlanFeature));
    } else {
      this.subscribeToSaveResponse(this.subsriptionPlanFeatureService.create(subsriptionPlanFeature));
    }
  }

  private createFromForm(): ISubsriptionPlanFeature {
    return {
      ...new SubsriptionPlanFeature(),
      id: this.editForm.get(['id'])!.value,
      subscriptionPlans: this.editForm.get(['subscriptionPlans'])!.value,
      appFeatures: this.editForm.get(['appFeatures'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubsriptionPlanFeature>>): void {
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

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
