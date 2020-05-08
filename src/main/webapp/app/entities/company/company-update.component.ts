import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICompany, Company } from 'app/shared/model/company.model';
import { CompanyService } from './company.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

type SelectableEntity = IAddress | IUserProfile;

@Component({
  selector: 'jhi-company-update',
  templateUrl: './company-update.component.html'
})
export class CompanyUpdateComponent implements OnInit {
  isSaving = false;
  addresses: IAddress[] = [];
  userprofiles: IUserProfile[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(200)]],
    email: [null, [Validators.maxLength(200)]],
    termsConditions: [null, [Validators.maxLength(255)]],
    aboutUs: [null, [Validators.maxLength(255)]],
    catalogue: [null, [Validators.maxLength(255)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    addressId: [],
    createdById: [null, Validators.required],
    updatedById: [null, Validators.required]
  });

  constructor(
    protected companyService: CompanyService,
    protected addressService: AddressService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ company }) => {
      if (!company.id) {
        const today = moment().startOf('day');
        company.createdAt = today;
        company.updatedAt = today;
      }

      this.updateForm(company);

      this.addressService.query().subscribe((res: HttpResponse<IAddress[]>) => (this.addresses = res.body || []));

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));
    });
  }

  updateForm(company: ICompany): void {
    this.editForm.patchValue({
      id: company.id,
      name: company.name,
      email: company.email,
      termsConditions: company.termsConditions,
      aboutUs: company.aboutUs,
      catalogue: company.catalogue,
      createdAt: company.createdAt ? company.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: company.updatedAt ? company.updatedAt.format(DATE_TIME_FORMAT) : null,
      addressId: company.addressId,
      createdById: company.createdById,
      updatedById: company.updatedById
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const company = this.createFromForm();
    if (company.id !== undefined) {
      this.subscribeToSaveResponse(this.companyService.update(company));
    } else {
      this.subscribeToSaveResponse(this.companyService.create(company));
    }
  }

  private createFromForm(): ICompany {
    return {
      ...new Company(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      email: this.editForm.get(['email'])!.value,
      termsConditions: this.editForm.get(['termsConditions'])!.value,
      aboutUs: this.editForm.get(['aboutUs'])!.value,
      catalogue: this.editForm.get(['catalogue'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      addressId: this.editForm.get(['addressId'])!.value,
      createdById: this.editForm.get(['createdById'])!.value,
      updatedById: this.editForm.get(['updatedById'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompany>>): void {
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
