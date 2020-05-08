import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOfferPrice } from 'app/shared/model/offer-price.model';

@Component({
  selector: 'jhi-offer-price-detail',
  templateUrl: './offer-price-detail.component.html'
})
export class OfferPriceDetailComponent implements OnInit {
  offerPrice: IOfferPrice | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offerPrice }) => (this.offerPrice = offerPrice));
  }

  previousState(): void {
    window.history.back();
  }
}
