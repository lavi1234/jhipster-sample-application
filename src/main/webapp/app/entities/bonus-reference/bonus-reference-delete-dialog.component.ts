import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBonusReference } from 'app/shared/model/bonus-reference.model';
import { BonusReferenceService } from './bonus-reference.service';

@Component({
  templateUrl: './bonus-reference-delete-dialog.component.html'
})
export class BonusReferenceDeleteDialogComponent {
  bonusReference?: IBonusReference;

  constructor(
    protected bonusReferenceService: BonusReferenceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bonusReferenceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bonusReferenceListModification');
      this.activeModal.close();
    });
  }
}
