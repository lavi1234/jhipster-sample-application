import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPaymentDetails, PaymentDetails } from 'app/shared/model/payment-details.model';
import { PaymentDetailsService } from './payment-details.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

@Component({
  selector: 'jhi-payment-details-update',
  templateUrl: './payment-details-update.component.html'
})
export class PaymentDetailsUpdateComponent implements OnInit {
  isSaving = false;
  userprofiles: IUserProfile[] = [];

  editForm = this.fb.group({
    id: [],
    bankName: [null, [Validators.required, Validators.maxLength(255)]],
    accountNumber: [null, [Validators.required, Validators.maxLength(255)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    userProfileId: []
  });

  constructor(
    protected paymentDetailsService: PaymentDetailsService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paymentDetails }) => {
      if (!paymentDetails.id) {
        const today = moment().startOf('day');
        paymentDetails.createdAt = today;
        paymentDetails.updatedAt = today;
      }

      this.updateForm(paymentDetails);

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));
    });
  }

  updateForm(paymentDetails: IPaymentDetails): void {
    this.editForm.patchValue({
      id: paymentDetails.id,
      bankName: paymentDetails.bankName,
      accountNumber: paymentDetails.accountNumber,
      createdAt: paymentDetails.createdAt ? paymentDetails.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: paymentDetails.updatedAt ? paymentDetails.updatedAt.format(DATE_TIME_FORMAT) : null,
      userProfileId: paymentDetails.userProfileId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paymentDetails = this.createFromForm();
    if (paymentDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.paymentDetailsService.update(paymentDetails));
    } else {
      this.subscribeToSaveResponse(this.paymentDetailsService.create(paymentDetails));
    }
  }

  private createFromForm(): IPaymentDetails {
    return {
      ...new PaymentDetails(),
      id: this.editForm.get(['id'])!.value,
      bankName: this.editForm.get(['bankName'])!.value,
      accountNumber: this.editForm.get(['accountNumber'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      userProfileId: this.editForm.get(['userProfileId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaymentDetails>>): void {
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

  trackById(index: number, item: IUserProfile): any {
    return item.id;
  }
}
