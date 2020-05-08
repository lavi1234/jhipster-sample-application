import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommissionReference } from 'app/shared/model/commission-reference.model';
import { CommissionReferenceService } from './commission-reference.service';

@Component({
  templateUrl: './commission-reference-delete-dialog.component.html'
})
export class CommissionReferenceDeleteDialogComponent {
  commissionReference?: ICommissionReference;

  constructor(
    protected commissionReferenceService: CommissionReferenceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commissionReferenceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commissionReferenceListModification');
      this.activeModal.close();
    });
  }
}
