import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserSubscription } from 'app/shared/model/user-subscription.model';
import { UserSubscriptionService } from './user-subscription.service';

@Component({
  templateUrl: './user-subscription-delete-dialog.component.html'
})
export class UserSubscriptionDeleteDialogComponent {
  userSubscription?: IUserSubscription;

  constructor(
    protected userSubscriptionService: UserSubscriptionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userSubscriptionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userSubscriptionListModification');
      this.activeModal.close();
    });
  }
}
