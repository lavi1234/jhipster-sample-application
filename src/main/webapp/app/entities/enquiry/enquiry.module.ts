import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { EnquiryComponent } from './enquiry.component';
import { EnquiryDetailComponent } from './enquiry-detail.component';
import { EnquiryUpdateComponent } from './enquiry-update.component';
import { EnquiryDeleteDialogComponent } from './enquiry-delete-dialog.component';
import { enquiryRoute } from './enquiry.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(enquiryRoute)],
  declarations: [EnquiryComponent, EnquiryDetailComponent, EnquiryUpdateComponent, EnquiryDeleteDialogComponent],
  entryComponents: [EnquiryDeleteDialogComponent]
})
export class JhipsterSampleApplicationEnquiryModule {}
