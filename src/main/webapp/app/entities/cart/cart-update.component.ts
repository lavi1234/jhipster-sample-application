import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICart, Cart } from 'app/shared/model/cart.model';
import { CartService } from './cart.service';
import { IEnquiry } from 'app/shared/model/enquiry.model';
import { EnquiryService } from 'app/entities/enquiry/enquiry.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

type SelectableEntity = IEnquiry | IUserProfile;

@Component({
  selector: 'jhi-cart-update',
  templateUrl: './cart-update.component.html'
})
export class CartUpdateComponent implements OnInit {
  isSaving = false;
  enquiries: IEnquiry[] = [];
  userprofiles: IUserProfile[] = [];

  editForm = this.fb.group({
    id: [],
    createdAt: [null, [Validators.required]],
    enquiryId: [null, Validators.required],
    supplierId: [null, Validators.required],
    createdById: [null, Validators.required]
  });

  constructor(
    protected cartService: CartService,
    protected enquiryService: EnquiryService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cart }) => {
      if (!cart.id) {
        const today = moment().startOf('day');
        cart.createdAt = today;
      }

      this.updateForm(cart);

      this.enquiryService.query().subscribe((res: HttpResponse<IEnquiry[]>) => (this.enquiries = res.body || []));

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));
    });
  }

  updateForm(cart: ICart): void {
    this.editForm.patchValue({
      id: cart.id,
      createdAt: cart.createdAt ? cart.createdAt.format(DATE_TIME_FORMAT) : null,
      enquiryId: cart.enquiryId,
      supplierId: cart.supplierId,
      createdById: cart.createdById
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cart = this.createFromForm();
    if (cart.id !== undefined) {
      this.subscribeToSaveResponse(this.cartService.update(cart));
    } else {
      this.subscribeToSaveResponse(this.cartService.create(cart));
    }
  }

  private createFromForm(): ICart {
    return {
      ...new Cart(),
      id: this.editForm.get(['id'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      enquiryId: this.editForm.get(['enquiryId'])!.value,
      supplierId: this.editForm.get(['supplierId'])!.value,
      createdById: this.editForm.get(['createdById'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICart>>): void {
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
