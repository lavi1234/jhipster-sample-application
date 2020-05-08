import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { SupplierEnquiryMappingComponent } from './supplier-enquiry-mapping.component';
import { SupplierEnquiryMappingDetailComponent } from './supplier-enquiry-mapping-detail.component';
import { SupplierEnquiryMappingUpdateComponent } from './supplier-enquiry-mapping-update.component';
import { SupplierEnquiryMappingDeleteDialogComponent } from './supplier-enquiry-mapping-delete-dialog.component';
import { supplierEnquiryMappingRoute } from './supplier-enquiry-mapping.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(supplierEnquiryMappingRoute)],
  declarations: [
    SupplierEnquiryMappingComponent,
    SupplierEnquiryMappingDetailComponent,
    SupplierEnquiryMappingUpdateComponent,
    SupplierEnquiryMappingDeleteDialogComponent
  ],
  entryComponents: [SupplierEnquiryMappingDeleteDialogComponent]
})
export class JhipsterSampleApplicationSupplierEnquiryMappingModule {}
