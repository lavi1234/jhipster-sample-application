import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { SubscriptionPlanComponent } from './subscription-plan.component';
import { SubscriptionPlanDetailComponent } from './subscription-plan-detail.component';
import { SubscriptionPlanUpdateComponent } from './subscription-plan-update.component';
import { SubscriptionPlanDeleteDialogComponent } from './subscription-plan-delete-dialog.component';
import { subscriptionPlanRoute } from './subscription-plan.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(subscriptionPlanRoute)],
  declarations: [
    SubscriptionPlanComponent,
    SubscriptionPlanDetailComponent,
    SubscriptionPlanUpdateComponent,
    SubscriptionPlanDeleteDialogComponent
  ],
  entryComponents: [SubscriptionPlanDeleteDialogComponent]
})
export class JhipsterSampleApplicationSubscriptionPlanModule {}
