import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnquiryDetails } from 'app/shared/model/enquiry-details.model';
import { EnquiryDetailsService } from './enquiry-details.service';

@Component({
  templateUrl: './enquiry-details-delete-dialog.component.html'
})
export class EnquiryDetailsDeleteDialogComponent {
  enquiryDetails?: IEnquiryDetails;

  constructor(
    protected enquiryDetailsService: EnquiryDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enquiryDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('enquiryDetailsListModification');
      this.activeModal.close();
    });
  }
}
