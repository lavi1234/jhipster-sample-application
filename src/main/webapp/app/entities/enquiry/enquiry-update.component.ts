import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEnquiry, Enquiry } from 'app/shared/model/enquiry.model';
import { EnquiryService } from './enquiry.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';

type SelectableEntity = ICategory | IAddress;

@Component({
  selector: 'jhi-enquiry-update',
  templateUrl: './enquiry-update.component.html'
})
export class EnquiryUpdateComponent implements OnInit {
  isSaving = false;
  categories: ICategory[] = [];
  addresses: IAddress[] = [];
  offerTaxtUntilDp: any;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required, Validators.maxLength(255)]],
    deliveryTerms: [null, [Validators.maxLength(255)]],
    offerTaxtUntil: [],
    status: [null, [Validators.required, Validators.maxLength(200)]],
    productId: [null, Validators.required],
    deliveryAddressId: [null, Validators.required]
  });

  constructor(
    protected enquiryService: EnquiryService,
    protected categoryService: CategoryService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enquiry }) => {
      this.updateForm(enquiry);

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));

      this.addressService.query().subscribe((res: HttpResponse<IAddress[]>) => (this.addresses = res.body || []));
    });
  }

  updateForm(enquiry: IEnquiry): void {
    this.editForm.patchValue({
      id: enquiry.id,
      description: enquiry.description,
      deliveryTerms: enquiry.deliveryTerms,
      offerTaxtUntil: enquiry.offerTaxtUntil,
      status: enquiry.status,
      productId: enquiry.productId,
      deliveryAddressId: enquiry.deliveryAddressId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enquiry = this.createFromForm();
    if (enquiry.id !== undefined) {
      this.subscribeToSaveResponse(this.enquiryService.update(enquiry));
    } else {
      this.subscribeToSaveResponse(this.enquiryService.create(enquiry));
    }
  }

  private createFromForm(): IEnquiry {
    return {
      ...new Enquiry(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      deliveryTerms: this.editForm.get(['deliveryTerms'])!.value,
      offerTaxtUntil: this.editForm.get(['offerTaxtUntil'])!.value,
      status: this.editForm.get(['status'])!.value,
      productId: this.editForm.get(['productId'])!.value,
      deliveryAddressId: this.editForm.get(['deliveryAddressId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnquiry>>): void {
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
