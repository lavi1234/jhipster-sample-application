import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrder, Order } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { IOffer } from 'app/shared/model/offer.model';
import { OfferService } from 'app/entities/offer/offer.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';
import { IEnquiry } from 'app/shared/model/enquiry.model';
import { EnquiryService } from 'app/entities/enquiry/enquiry.service';
import { IEnquiryDetails } from 'app/shared/model/enquiry-details.model';
import { EnquiryDetailsService } from 'app/entities/enquiry-details/enquiry-details.service';

type SelectableEntity = IOffer | IUserProfile | IEnquiry | IEnquiryDetails;

@Component({
  selector: 'jhi-order-update',
  templateUrl: './order-update.component.html'
})
export class OrderUpdateComponent implements OnInit {
  isSaving = false;
  offers: IOffer[] = [];
  userprofiles: IUserProfile[] = [];
  enquiries: IEnquiry[] = [];
  enquirydetails: IEnquiryDetails[] = [];
  deliveryDateDp: any;
  commissionDateDp: any;

  editForm = this.fb.group({
    id: [],
    price: [null, [Validators.required]],
    deliveryDate: [null, [Validators.required]],
    status: [null, [Validators.required, Validators.maxLength(200)]],
    commissionDate: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    remarks: [null, [Validators.required, Validators.maxLength(255)]],
    offerId: [null, Validators.required],
    buyerId: [null, Validators.required],
    supplierId: [null, Validators.required],
    enquiryId: [null, Validators.required],
    enquiryDetailsId: [null, Validators.required]
  });

  constructor(
    protected orderService: OrderService,
    protected offerService: OfferService,
    protected userProfileService: UserProfileService,
    protected enquiryService: EnquiryService,
    protected enquiryDetailsService: EnquiryDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ order }) => {
      if (!order.id) {
        const today = moment().startOf('day');
        order.createdAt = today;
        order.updatedAt = today;
      }

      this.updateForm(order);

      this.offerService.query().subscribe((res: HttpResponse<IOffer[]>) => (this.offers = res.body || []));

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));

      this.enquiryService.query().subscribe((res: HttpResponse<IEnquiry[]>) => (this.enquiries = res.body || []));

      this.enquiryDetailsService.query().subscribe((res: HttpResponse<IEnquiryDetails[]>) => (this.enquirydetails = res.body || []));
    });
  }

  updateForm(order: IOrder): void {
    this.editForm.patchValue({
      id: order.id,
      price: order.price,
      deliveryDate: order.deliveryDate,
      status: order.status,
      commissionDate: order.commissionDate,
      createdAt: order.createdAt ? order.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: order.updatedAt ? order.updatedAt.format(DATE_TIME_FORMAT) : null,
      remarks: order.remarks,
      offerId: order.offerId,
      buyerId: order.buyerId,
      supplierId: order.supplierId,
      enquiryId: order.enquiryId,
      enquiryDetailsId: order.enquiryDetailsId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const order = this.createFromForm();
    if (order.id !== undefined) {
      this.subscribeToSaveResponse(this.orderService.update(order));
    } else {
      this.subscribeToSaveResponse(this.orderService.create(order));
    }
  }

  private createFromForm(): IOrder {
    return {
      ...new Order(),
      id: this.editForm.get(['id'])!.value,
      price: this.editForm.get(['price'])!.value,
      deliveryDate: this.editForm.get(['deliveryDate'])!.value,
      status: this.editForm.get(['status'])!.value,
      commissionDate: this.editForm.get(['commissionDate'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      remarks: this.editForm.get(['remarks'])!.value,
      offerId: this.editForm.get(['offerId'])!.value,
      buyerId: this.editForm.get(['buyerId'])!.value,
      supplierId: this.editForm.get(['supplierId'])!.value,
      enquiryId: this.editForm.get(['enquiryId'])!.value,
      enquiryDetailsId: this.editForm.get(['enquiryDetailsId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>): void {
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
