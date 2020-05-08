import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { CommissionReferenceComponent } from './commission-reference.component';
import { CommissionReferenceDetailComponent } from './commission-reference-detail.component';
import { CommissionReferenceUpdateComponent } from './commission-reference-update.component';
import { CommissionReferenceDeleteDialogComponent } from './commission-reference-delete-dialog.component';
import { commissionReferenceRoute } from './commission-reference.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(commissionReferenceRoute)],
  declarations: [
    CommissionReferenceComponent,
    CommissionReferenceDetailComponent,
    CommissionReferenceUpdateComponent,
    CommissionReferenceDeleteDialogComponent
  ],
  entryComponents: [CommissionReferenceDeleteDialogComponent]
})
export class JhipsterSampleApplicationCommissionReferenceModule {}
