import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICommission, Commission } from 'app/shared/model/commission.model';
import { CommissionService } from './commission.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';

type SelectableEntity = IUserProfile | IOrder;

@Component({
  selector: 'jhi-commission-update',
  templateUrl: './commission-update.component.html'
})
export class CommissionUpdateComponent implements OnInit {
  isSaving = false;
  userprofiles: IUserProfile[] = [];
  orders: IOrder[] = [];

  editForm = this.fb.group({
    id: [],
    amount: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    principalAmount: [null, [Validators.required]],
    status: [null, [Validators.required, Validators.maxLength(200)]],
    remarks: [null, [Validators.maxLength(255)]],
    supplierId: [null, Validators.required],
    orderId: [null, Validators.required]
  });

  constructor(
    protected commissionService: CommissionService,
    protected userProfileService: UserProfileService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commission }) => {
      if (!commission.id) {
        const today = moment().startOf('day');
        commission.createdAt = today;
        commission.updatedAt = today;
      }

      this.updateForm(commission);

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));
    });
  }

  updateForm(commission: ICommission): void {
    this.editForm.patchValue({
      id: commission.id,
      amount: commission.amount,
      createdAt: commission.createdAt ? commission.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: commission.updatedAt ? commission.updatedAt.format(DATE_TIME_FORMAT) : null,
      principalAmount: commission.principalAmount,
      status: commission.status,
      remarks: commission.remarks,
      supplierId: commission.supplierId,
      orderId: commission.orderId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commission = this.createFromForm();
    if (commission.id !== undefined) {
      this.subscribeToSaveResponse(this.commissionService.update(commission));
    } else {
      this.subscribeToSaveResponse(this.commissionService.create(commission));
    }
  }

  private createFromForm(): ICommission {
    return {
      ...new Commission(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      principalAmount: this.editForm.get(['principalAmount'])!.value,
      status: this.editForm.get(['status'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      supplierId: this.editForm.get(['supplierId'])!.value,
      orderId: this.editForm.get(['orderId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommission>>): void {
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
