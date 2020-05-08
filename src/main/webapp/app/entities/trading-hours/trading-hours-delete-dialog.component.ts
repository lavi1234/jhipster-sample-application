import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITradingHours } from 'app/shared/model/trading-hours.model';
import { TradingHoursService } from './trading-hours.service';

@Component({
  templateUrl: './trading-hours-delete-dialog.component.html'
})
export class TradingHoursDeleteDialogComponent {
  tradingHours?: ITradingHours;

  constructor(
    protected tradingHoursService: TradingHoursService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tradingHoursService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tradingHoursListModification');
      this.activeModal.close();
    });
  }
}
