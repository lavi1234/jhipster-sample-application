import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOfferPrice } from 'app/shared/model/offer-price.model';
import { OfferPriceService } from './offer-price.service';

@Component({
  templateUrl: './offer-price-delete-dialog.component.html'
})
export class OfferPriceDeleteDialogComponent {
  offerPrice?: IOfferPrice;

  constructor(
    protected offerPriceService: OfferPriceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.offerPriceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('offerPriceListModification');
      this.activeModal.close();
    });
  }
}
