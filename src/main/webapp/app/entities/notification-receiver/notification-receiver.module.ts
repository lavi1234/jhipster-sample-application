import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { NotificationReceiverComponent } from './notification-receiver.component';
import { NotificationReceiverDetailComponent } from './notification-receiver-detail.component';
import { NotificationReceiverUpdateComponent } from './notification-receiver-update.component';
import { NotificationReceiverDeleteDialogComponent } from './notification-receiver-delete-dialog.component';
import { notificationReceiverRoute } from './notification-receiver.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(notificationReceiverRoute)],
  declarations: [
    NotificationReceiverComponent,
    NotificationReceiverDetailComponent,
    NotificationReceiverUpdateComponent,
    NotificationReceiverDeleteDialogComponent
  ],
  entryComponents: [NotificationReceiverDeleteDialogComponent]
})
export class JhipsterSampleApplicationNotificationReceiverModule {}
