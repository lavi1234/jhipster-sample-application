import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnquiry } from 'app/shared/model/enquiry.model';
import { EnquiryService } from './enquiry.service';

@Component({
  templateUrl: './enquiry-delete-dialog.component.html'
})
export class EnquiryDeleteDialogComponent {
  enquiry?: IEnquiry;

  constructor(protected enquiryService: EnquiryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enquiryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('enquiryListModification');
      this.activeModal.close();
    });
  }
}
