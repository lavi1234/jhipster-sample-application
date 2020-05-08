import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEnquiryDetails, EnquiryDetails } from 'app/shared/model/enquiry-details.model';
import { EnquiryDetailsService } from './enquiry-details.service';
import { IEnquiry } from 'app/shared/model/enquiry.model';
import { EnquiryService } from 'app/entities/enquiry/enquiry.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';
import { IOffer } from 'app/shared/model/offer.model';
import { OfferService } from 'app/entities/offer/offer.service';

type SelectableEntity = IEnquiry | ICategory | IUserProfile | IOffer;

@Component({
  selector: 'jhi-enquiry-details-update',
  templateUrl: './enquiry-details-update.component.html'
})
export class EnquiryDetailsUpdateComponent implements OnInit {
  isSaving = false;
  enquiries: IEnquiry[] = [];
  categories: ICategory[] = [];
  userprofiles: IUserProfile[] = [];
  offers: IOffer[] = [];
  deliveryDateDp: any;

  editForm = this.fb.group({
    id: [],
    version: [null, [Validators.required]],
    edition: [null, [Validators.required]],
    format: [null, [Validators.required]],
    documents: [null, [Validators.required, Validators.maxLength(255)]],
    deliveryDate: [null, [Validators.required]],
    remarks: [null, [Validators.required, Validators.maxLength(255)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    enquiryId: [null, Validators.required],
    printId: [null, Validators.required],
    finishingId: [null, Validators.required],
    handlingId: [null, Validators.required],
    packagingId: [null, Validators.required],
    createdById: [null, Validators.required],
    offerId: []
  });

  constructor(
    protected enquiryDetailsService: EnquiryDetailsService,
    protected enquiryService: EnquiryService,
    protected categoryService: CategoryService,
    protected userProfileService: UserProfileService,
    protected offerService: OfferService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enquiryDetails }) => {
      if (!enquiryDetails.id) {
        const today = moment().startOf('day');
        enquiryDetails.createdAt = today;
        enquiryDetails.updatedAt = today;
      }

      this.updateForm(enquiryDetails);

      this.enquiryService.query().subscribe((res: HttpResponse<IEnquiry[]>) => (this.enquiries = res.body || []));

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));

      this.offerService.query().subscribe((res: HttpResponse<IOffer[]>) => (this.offers = res.body || []));
    });
  }

  updateForm(enquiryDetails: IEnquiryDetails): void {
    this.editForm.patchValue({
      id: enquiryDetails.id,
      version: enquiryDetails.version,
      edition: enquiryDetails.edition,
      format: enquiryDetails.format,
      documents: enquiryDetails.documents,
      deliveryDate: enquiryDetails.deliveryDate,
      remarks: enquiryDetails.remarks,
      createdAt: enquiryDetails.createdAt ? enquiryDetails.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: enquiryDetails.updatedAt ? enquiryDetails.updatedAt.format(DATE_TIME_FORMAT) : null,
      enquiryId: enquiryDetails.enquiryId,
      printId: enquiryDetails.printId,
      finishingId: enquiryDetails.finishingId,
      handlingId: enquiryDetails.handlingId,
      packagingId: enquiryDetails.packagingId,
      createdById: enquiryDetails.createdById,
      offerId: enquiryDetails.offerId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enquiryDetails = this.createFromForm();
    if (enquiryDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.enquiryDetailsService.update(enquiryDetails));
    } else {
      this.subscribeToSaveResponse(this.enquiryDetailsService.create(enquiryDetails));
    }
  }

  private createFromForm(): IEnquiryDetails {
    return {
      ...new EnquiryDetails(),
      id: this.editForm.get(['id'])!.value,
      version: this.editForm.get(['version'])!.value,
      edition: this.editForm.get(['edition'])!.value,
      format: this.editForm.get(['format'])!.value,
      documents: this.editForm.get(['documents'])!.value,
      deliveryDate: this.editForm.get(['deliveryDate'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      enquiryId: this.editForm.get(['enquiryId'])!.value,
      printId: this.editForm.get(['printId'])!.value,
      finishingId: this.editForm.get(['finishingId'])!.value,
      handlingId: this.editForm.get(['handlingId'])!.value,
      packagingId: this.editForm.get(['packagingId'])!.value,
      createdById: this.editForm.get(['createdById'])!.value,
      offerId: this.editForm.get(['offerId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnquiryDetails>>): void {
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
