import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmailTracking } from 'app/shared/model/email-tracking.model';
import { EmailTrackingService } from './email-tracking.service';

@Component({
  templateUrl: './email-tracking-delete-dialog.component.html'
})
export class EmailTrackingDeleteDialogComponent {
  emailTracking?: IEmailTracking;

  constructor(
    protected emailTrackingService: EmailTrackingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emailTrackingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('emailTrackingListModification');
      this.activeModal.close();
    });
  }
}
