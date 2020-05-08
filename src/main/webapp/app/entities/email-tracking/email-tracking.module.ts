import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { EmailTrackingComponent } from './email-tracking.component';
import { EmailTrackingDetailComponent } from './email-tracking-detail.component';
import { EmailTrackingUpdateComponent } from './email-tracking-update.component';
import { EmailTrackingDeleteDialogComponent } from './email-tracking-delete-dialog.component';
import { emailTrackingRoute } from './email-tracking.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(emailTrackingRoute)],
  declarations: [EmailTrackingComponent, EmailTrackingDetailComponent, EmailTrackingUpdateComponent, EmailTrackingDeleteDialogComponent],
  entryComponents: [EmailTrackingDeleteDialogComponent]
})
export class JhipsterSampleApplicationEmailTrackingModule {}
