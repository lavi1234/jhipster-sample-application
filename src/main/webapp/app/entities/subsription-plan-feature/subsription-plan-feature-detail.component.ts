import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubsriptionPlanFeature } from 'app/shared/model/subsription-plan-feature.model';

@Component({
  selector: 'jhi-subsription-plan-feature-detail',
  templateUrl: './subsription-plan-feature-detail.component.html'
})
export class SubsriptionPlanFeatureDetailComponent implements OnInit {
  subsriptionPlanFeature: ISubsriptionPlanFeature | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subsriptionPlanFeature }) => (this.subsriptionPlanFeature = subsriptionPlanFeature));
  }

  previousState(): void {
    window.history.back();
  }
}
