import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupplierEnquiryMapping } from 'app/shared/model/supplier-enquiry-mapping.model';
import { SupplierEnquiryMappingService } from './supplier-enquiry-mapping.service';

@Component({
  templateUrl: './supplier-enquiry-mapping-delete-dialog.component.html'
})
export class SupplierEnquiryMappingDeleteDialogComponent {
  supplierEnquiryMapping?: ISupplierEnquiryMapping;

  constructor(
    protected supplierEnquiryMappingService: SupplierEnquiryMappingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.supplierEnquiryMappingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('supplierEnquiryMappingListModification');
      this.activeModal.close();
    });
  }
}
