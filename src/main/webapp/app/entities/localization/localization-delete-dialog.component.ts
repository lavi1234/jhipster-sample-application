import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILocalization } from 'app/shared/model/localization.model';
import { LocalizationService } from './localization.service';

@Component({
  templateUrl: './localization-delete-dialog.component.html'
})
export class LocalizationDeleteDialogComponent {
  localization?: ILocalization;

  constructor(
    protected localizationService: LocalizationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.localizationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('localizationListModification');
      this.activeModal.close();
    });
  }
}
