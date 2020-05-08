import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppFeatures } from 'app/shared/model/app-features.model';
import { AppFeaturesService } from './app-features.service';

@Component({
  templateUrl: './app-features-delete-dialog.component.html'
})
export class AppFeaturesDeleteDialogComponent {
  appFeatures?: IAppFeatures;

  constructor(
    protected appFeaturesService: AppFeaturesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.appFeaturesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('appFeaturesListModification');
      this.activeModal.close();
    });
  }
}
