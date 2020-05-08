import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { SubsriptionPlanFeatureComponent } from './subsription-plan-feature.component';
import { SubsriptionPlanFeatureDetailComponent } from './subsription-plan-feature-detail.component';
import { SubsriptionPlanFeatureUpdateComponent } from './subsription-plan-feature-update.component';
import { SubsriptionPlanFeatureDeleteDialogComponent } from './subsription-plan-feature-delete-dialog.component';
import { subsriptionPlanFeatureRoute } from './subsription-plan-feature.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(subsriptionPlanFeatureRoute)],
  declarations: [
    SubsriptionPlanFeatureComponent,
    SubsriptionPlanFeatureDetailComponent,
    SubsriptionPlanFeatureUpdateComponent,
    SubsriptionPlanFeatureDeleteDialogComponent
  ],
  entryComponents: [SubsriptionPlanFeatureDeleteDialogComponent]
})
export class JhipsterSampleApplicationSubsriptionPlanFeatureModule {}
