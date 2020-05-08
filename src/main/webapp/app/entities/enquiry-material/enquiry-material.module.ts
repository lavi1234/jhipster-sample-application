import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { EnquiryMaterialComponent } from './enquiry-material.component';
import { EnquiryMaterialDetailComponent } from './enquiry-material-detail.component';
import { EnquiryMaterialUpdateComponent } from './enquiry-material-update.component';
import { EnquiryMaterialDeleteDialogComponent } from './enquiry-material-delete-dialog.component';
import { enquiryMaterialRoute } from './enquiry-material.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(enquiryMaterialRoute)],
  declarations: [
    EnquiryMaterialComponent,
    EnquiryMaterialDetailComponent,
    EnquiryMaterialUpdateComponent,
    EnquiryMaterialDeleteDialogComponent
  ],
  entryComponents: [EnquiryMaterialDeleteDialogComponent]
})
export class JhipsterSampleApplicationEnquiryMaterialModule {}
