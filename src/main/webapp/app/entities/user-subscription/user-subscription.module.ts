import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { UserSubscriptionComponent } from './user-subscription.component';
import { UserSubscriptionDetailComponent } from './user-subscription-detail.component';
import { UserSubscriptionUpdateComponent } from './user-subscription-update.component';
import { UserSubscriptionDeleteDialogComponent } from './user-subscription-delete-dialog.component';
import { userSubscriptionRoute } from './user-subscription.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(userSubscriptionRoute)],
  declarations: [
    UserSubscriptionComponent,
    UserSubscriptionDetailComponent,
    UserSubscriptionUpdateComponent,
    UserSubscriptionDeleteDialogComponent
  ],
  entryComponents: [UserSubscriptionDeleteDialogComponent]
})
export class JhipsterSampleApplicationUserSubscriptionModule {}
