import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOfferPrice, OfferPrice } from 'app/shared/model/offer-price.model';
import { OfferPriceService } from './offer-price.service';
import { IOffer } from 'app/shared/model/offer.model';
import { OfferService } from 'app/entities/offer/offer.service';
import { IEnquiry } from 'app/shared/model/enquiry.model';
import { EnquiryService } from 'app/entities/enquiry/enquiry.service';
import { IEnquiryDetails } from 'app/shared/model/enquiry-details.model';
import { EnquiryDetailsService } from 'app/entities/enquiry-details/enquiry-details.service';

type SelectableEntity = IOffer | IEnquiry | IEnquiryDetails;

@Component({
  selector: 'jhi-offer-price-update',
  templateUrl: './offer-price-update.component.html'
})
export class OfferPriceUpdateComponent implements OnInit {
  isSaving = false;
  offers: IOffer[] = [];
  enquiries: IEnquiry[] = [];
  enquirydetails: IEnquiryDetails[] = [];
  finishingDateDp: any;

  editForm = this.fb.group({
    id: [],
    price: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    finishingDate: [],
    offerId: [null, Validators.required],
    enquiryId: [null, Validators.required],
    enquiryDetailsId: [null, Validators.required]
  });

  constructor(
    protected offerPriceService: OfferPriceService,
    protected offerService: OfferService,
    protected enquiryService: EnquiryService,
    protected enquiryDetailsService: EnquiryDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offerPrice }) => {
      if (!offerPrice.id) {
        const today = moment().startOf('day');
        offerPrice.createdAt = today;
      }

      this.updateForm(offerPrice);

      this.offerService.query().subscribe((res: HttpResponse<IOffer[]>) => (this.offers = res.body || []));

      this.enquiryService.query().subscribe((res: HttpResponse<IEnquiry[]>) => (this.enquiries = res.body || []));

      this.enquiryDetailsService.query().subscribe((res: HttpResponse<IEnquiryDetails[]>) => (this.enquirydetails = res.body || []));
    });
  }

  updateForm(offerPrice: IOfferPrice): void {
    this.editForm.patchValue({
      id: offerPrice.id,
      price: offerPrice.price,
      createdAt: offerPrice.createdAt ? offerPrice.createdAt.format(DATE_TIME_FORMAT) : null,
      finishingDate: offerPrice.finishingDate,
      offerId: offerPrice.offerId,
      enquiryId: offerPrice.enquiryId,
      enquiryDetailsId: offerPrice.enquiryDetailsId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offerPrice = this.createFromForm();
    if (offerPrice.id !== undefined) {
      this.subscribeToSaveResponse(this.offerPriceService.update(offerPrice));
    } else {
      this.subscribeToSaveResponse(this.offerPriceService.create(offerPrice));
    }
  }

  private createFromForm(): IOfferPrice {
    return {
      ...new OfferPrice(),
      id: this.editForm.get(['id'])!.value,
      price: this.editForm.get(['price'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      finishingDate: this.editForm.get(['finishingDate'])!.value,
      offerId: this.editForm.get(['offerId'])!.value,
      enquiryId: this.editForm.get(['enquiryId'])!.value,
      enquiryDetailsId: this.editForm.get(['enquiryDetailsId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOfferPrice>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
