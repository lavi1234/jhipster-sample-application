import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnquiryMaterial } from 'app/shared/model/enquiry-material.model';
import { EnquiryMaterialService } from './enquiry-material.service';

@Component({
  templateUrl: './enquiry-material-delete-dialog.component.html'
})
export class EnquiryMaterialDeleteDialogComponent {
  enquiryMaterial?: IEnquiryMaterial;

  constructor(
    protected enquiryMaterialService: EnquiryMaterialService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enquiryMaterialService.delete(id).subscribe(() => {
      this.eventManager.broadcast('enquiryMaterialListModification');
      this.activeModal.close();
    });
  }
}
