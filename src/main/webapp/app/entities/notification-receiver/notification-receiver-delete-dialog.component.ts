import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INotificationReceiver } from 'app/shared/model/notification-receiver.model';
import { NotificationReceiverService } from './notification-receiver.service';

@Component({
  templateUrl: './notification-receiver-delete-dialog.component.html'
})
export class NotificationReceiverDeleteDialogComponent {
  notificationReceiver?: INotificationReceiver;

  constructor(
    protected notificationReceiverService: NotificationReceiverService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.notificationReceiverService.delete(id).subscribe(() => {
      this.eventManager.broadcast('notificationReceiverListModification');
      this.activeModal.close();
    });
  }
}
