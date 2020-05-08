import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubscriptionPlan } from 'app/shared/model/subscription-plan.model';
import { SubscriptionPlanService } from './subscription-plan.service';

@Component({
  templateUrl: './subscription-plan-delete-dialog.component.html'
})
export class SubscriptionPlanDeleteDialogComponent {
  subscriptionPlan?: ISubscriptionPlan;

  constructor(
    protected subscriptionPlanService: SubscriptionPlanService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subscriptionPlanService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subscriptionPlanListModification');
      this.activeModal.close();
    });
  }
}
