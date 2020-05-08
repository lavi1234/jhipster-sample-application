import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { MessageDeleteDetailsComponent } from './message-delete-details.component';
import { MessageDeleteDetailsDetailComponent } from './message-delete-details-detail.component';
import { MessageDeleteDetailsUpdateComponent } from './message-delete-details-update.component';
import { MessageDeleteDetailsDeleteDialogComponent } from './message-delete-details-delete-dialog.component';
import { messageDeleteDetailsRoute } from './message-delete-details.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(messageDeleteDetailsRoute)],
  declarations: [
    MessageDeleteDetailsComponent,
    MessageDeleteDetailsDetailComponent,
    MessageDeleteDetailsUpdateComponent,
    MessageDeleteDetailsDeleteDialogComponent
  ],
  entryComponents: [MessageDeleteDetailsDeleteDialogComponent]
})
export class JhipsterSampleApplicationMessageDeleteDetailsModule {}
