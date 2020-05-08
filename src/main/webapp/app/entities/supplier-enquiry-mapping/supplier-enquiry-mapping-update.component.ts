import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISupplierEnquiryMapping, SupplierEnquiryMapping } from 'app/shared/model/supplier-enquiry-mapping.model';
import { SupplierEnquiryMappingService } from './supplier-enquiry-mapping.service';
import { IEnquiry } from 'app/shared/model/enquiry.model';
import { EnquiryService } from 'app/entities/enquiry/enquiry.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

type SelectableEntity = IEnquiry | IUserProfile;

@Component({
  selector: 'jhi-supplier-enquiry-mapping-update',
  templateUrl: './supplier-enquiry-mapping-update.component.html'
})
export class SupplierEnquiryMappingUpdateComponent implements OnInit {
  isSaving = false;
  enquiries: IEnquiry[] = [];
  userprofiles: IUserProfile[] = [];

  editForm = this.fb.group({
    id: [],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    enquiryId: [null, Validators.required],
    supplierId: [null, Validators.required],
    createdById: [null, Validators.required]
  });

  constructor(
    protected supplierEnquiryMappingService: SupplierEnquiryMappingService,
    protected enquiryService: EnquiryService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supplierEnquiryMapping }) => {
      if (!supplierEnquiryMapping.id) {
        const today = moment().startOf('day');
        supplierEnquiryMapping.createdAt = today;
        supplierEnquiryMapping.updatedAt = today;
      }

      this.updateForm(supplierEnquiryMapping);

      this.enquiryService.query().subscribe((res: HttpResponse<IEnquiry[]>) => (this.enquiries = res.body || []));

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));
    });
  }

  updateForm(supplierEnquiryMapping: ISupplierEnquiryMapping): void {
    this.editForm.patchValue({
      id: supplierEnquiryMapping.id,
      createdAt: supplierEnquiryMapping.createdAt ? supplierEnquiryMapping.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: supplierEnquiryMapping.updatedAt ? supplierEnquiryMapping.updatedAt.format(DATE_TIME_FORMAT) : null,
      enquiryId: supplierEnquiryMapping.enquiryId,
      supplierId: supplierEnquiryMapping.supplierId,
      createdById: supplierEnquiryMapping.createdById
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const supplierEnquiryMapping = this.createFromForm();
    if (supplierEnquiryMapping.id !== undefined) {
      this.subscribeToSaveResponse(this.supplierEnquiryMappingService.update(supplierEnquiryMapping));
    } else {
      this.subscribeToSaveResponse(this.supplierEnquiryMappingService.create(supplierEnquiryMapping));
    }
  }

  private createFromForm(): ISupplierEnquiryMapping {
    return {
      ...new SupplierEnquiryMapping(),
      id: this.editForm.get(['id'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      enquiryId: this.editForm.get(['enquiryId'])!.value,
      supplierId: this.editForm.get(['supplierId'])!.value,
      createdById: this.editForm.get(['createdById'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupplierEnquiryMapping>>): void {
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
