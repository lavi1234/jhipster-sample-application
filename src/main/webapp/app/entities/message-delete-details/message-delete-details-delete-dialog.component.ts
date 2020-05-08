import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMessageDeleteDetails } from 'app/shared/model/message-delete-details.model';
import { MessageDeleteDetailsService } from './message-delete-details.service';

@Component({
  templateUrl: './message-delete-details-delete-dialog.component.html'
})
export class MessageDeleteDetailsDeleteDialogComponent {
  messageDeleteDetails?: IMessageDeleteDetails;

  constructor(
    protected messageDeleteDetailsService: MessageDeleteDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.messageDeleteDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('messageDeleteDetailsListModification');
      this.activeModal.close();
    });
  }
}
