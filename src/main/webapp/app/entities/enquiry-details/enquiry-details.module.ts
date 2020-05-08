import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { EnquiryDetailsComponent } from './enquiry-details.component';
import { EnquiryDetailsDetailComponent } from './enquiry-details-detail.component';
import { EnquiryDetailsUpdateComponent } from './enquiry-details-update.component';
import { EnquiryDetailsDeleteDialogComponent } from './enquiry-details-delete-dialog.component';
import { enquiryDetailsRoute } from './enquiry-details.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(enquiryDetailsRoute)],
  declarations: [
    EnquiryDetailsComponent,
    EnquiryDetailsDetailComponent,
    EnquiryDetailsUpdateComponent,
    EnquiryDetailsDeleteDialogComponent
  ],
  entryComponents: [EnquiryDetailsDeleteDialogComponent]
})
export class JhipsterSampleApplicationEnquiryDetailsModule {}
