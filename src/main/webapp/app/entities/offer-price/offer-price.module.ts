import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { OfferPriceComponent } from './offer-price.component';
import { OfferPriceDetailComponent } from './offer-price-detail.component';
import { OfferPriceUpdateComponent } from './offer-price-update.component';
import { OfferPriceDeleteDialogComponent } from './offer-price-delete-dialog.component';
import { offerPriceRoute } from './offer-price.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(offerPriceRoute)],
  declarations: [OfferPriceComponent, OfferPriceDetailComponent, OfferPriceUpdateComponent, OfferPriceDeleteDialogComponent],
  entryComponents: [OfferPriceDeleteDialogComponent]
})
export class JhipsterSampleApplicationOfferPriceModule {}
