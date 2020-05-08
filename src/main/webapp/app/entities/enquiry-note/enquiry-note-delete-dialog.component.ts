import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEnquiryNote } from 'app/shared/model/enquiry-note.model';
import { EnquiryNoteService } from './enquiry-note.service';

@Component({
  templateUrl: './enquiry-note-delete-dialog.component.html'
})
export class EnquiryNoteDeleteDialogComponent {
  enquiryNote?: IEnquiryNote;

  constructor(
    protected enquiryNoteService: EnquiryNoteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enquiryNoteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('enquiryNoteListModification');
      this.activeModal.close();
    });
  }
}
