import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubsriptionPlanFeature } from 'app/shared/model/subsription-plan-feature.model';
import { SubsriptionPlanFeatureService } from './subsription-plan-feature.service';

@Component({
  templateUrl: './subsription-plan-feature-delete-dialog.component.html'
})
export class SubsriptionPlanFeatureDeleteDialogComponent {
  subsriptionPlanFeature?: ISubsriptionPlanFeature;

  constructor(
    protected subsriptionPlanFeatureService: SubsriptionPlanFeatureService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subsriptionPlanFeatureService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subsriptionPlanFeatureListModification');
      this.activeModal.close();
    });
  }
}
