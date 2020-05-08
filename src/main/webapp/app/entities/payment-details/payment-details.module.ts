import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PaymentDetailsComponent } from './payment-details.component';
import { PaymentDetailsDetailComponent } from './payment-details-detail.component';
import { PaymentDetailsUpdateComponent } from './payment-details-update.component';
import { PaymentDetailsDeleteDialogComponent } from './payment-details-delete-dialog.component';
import { paymentDetailsRoute } from './payment-details.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(paymentDetailsRoute)],
  declarations: [
    PaymentDetailsComponent,
    PaymentDetailsDetailComponent,
    PaymentDetailsUpdateComponent,
    PaymentDetailsDeleteDialogComponent
  ],
  entryComponents: [PaymentDetailsDeleteDialogComponent]
})
export class JhipsterSampleApplicationPaymentDetailsModule {}
