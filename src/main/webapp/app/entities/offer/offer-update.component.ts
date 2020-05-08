import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOffer, Offer } from 'app/shared/model/offer.model';
import { OfferService } from './offer.service';
import { ISupplierEnquiryMapping } from 'app/shared/model/supplier-enquiry-mapping.model';
import { SupplierEnquiryMappingService } from 'app/entities/supplier-enquiry-mapping/supplier-enquiry-mapping.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

type SelectableEntity = ISupplierEnquiryMapping | IUserProfile;

@Component({
  selector: 'jhi-offer-update',
  templateUrl: './offer-update.component.html'
})
export class OfferUpdateComponent implements OnInit {
  isSaving = false;
  supplierenquirymappings: ISupplierEnquiryMapping[] = [];
  userprofiles: IUserProfile[] = [];
  validUptoDp: any;

  editForm = this.fb.group({
    id: [],
    validUpto: [null, [Validators.required]],
    status: [null, [Validators.required, Validators.maxLength(200)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    supplierEnquiryId: [null, Validators.required],
    createdById: [null, Validators.required],
    updatedById: [null, Validators.required]
  });

  constructor(
    protected offerService: OfferService,
    protected supplierEnquiryMappingService: SupplierEnquiryMappingService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offer }) => {
      if (!offer.id) {
        const today = moment().startOf('day');
        offer.createdAt = today;
        offer.updatedAt = today;
      }

      this.updateForm(offer);

      this.supplierEnquiryMappingService
        .query()
        .subscribe((res: HttpResponse<ISupplierEnquiryMapping[]>) => (this.supplierenquirymappings = res.body || []));

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));
    });
  }

  updateForm(offer: IOffer): void {
    this.editForm.patchValue({
      id: offer.id,
      validUpto: offer.validUpto,
      status: offer.status,
      createdAt: offer.createdAt ? offer.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: offer.updatedAt ? offer.updatedAt.format(DATE_TIME_FORMAT) : null,
      supplierEnquiryId: offer.supplierEnquiryId,
      createdById: offer.createdById,
      updatedById: offer.updatedById
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offer = this.createFromForm();
    if (offer.id !== undefined) {
      this.subscribeToSaveResponse(this.offerService.update(offer));
    } else {
      this.subscribeToSaveResponse(this.offerService.create(offer));
    }
  }

  private createFromForm(): IOffer {
    return {
      ...new Offer(),
      id: this.editForm.get(['id'])!.value,
      validUpto: this.editForm.get(['validUpto'])!.value,
      status: this.editForm.get(['status'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      supplierEnquiryId: this.editForm.get(['supplierEnquiryId'])!.value,
      createdById: this.editForm.get(['createdById'])!.value,
      updatedById: this.editForm.get(['updatedById'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOffer>>): void {
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
