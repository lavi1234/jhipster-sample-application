import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBonus, Bonus } from 'app/shared/model/bonus.model';
import { BonusService } from './bonus.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';

type SelectableEntity = IUserProfile | IOrder;

@Component({
  selector: 'jhi-bonus-update',
  templateUrl: './bonus-update.component.html'
})
export class BonusUpdateComponent implements OnInit {
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
    remarks: [null, [Validators.required, Validators.maxLength(255)]],
    buyerId: [null, Validators.required],
    orderId: [null, Validators.required]
  });

  constructor(
    protected bonusService: BonusService,
    protected userProfileService: UserProfileService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bonus }) => {
      if (!bonus.id) {
        const today = moment().startOf('day');
        bonus.createdAt = today;
        bonus.updatedAt = today;
      }

      this.updateForm(bonus);

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));
    });
  }

  updateForm(bonus: IBonus): void {
    this.editForm.patchValue({
      id: bonus.id,
      amount: bonus.amount,
      createdAt: bonus.createdAt ? bonus.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: bonus.updatedAt ? bonus.updatedAt.format(DATE_TIME_FORMAT) : null,
      principalAmount: bonus.principalAmount,
      status: bonus.status,
      remarks: bonus.remarks,
      buyerId: bonus.buyerId,
      orderId: bonus.orderId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bonus = this.createFromForm();
    if (bonus.id !== undefined) {
      this.subscribeToSaveResponse(this.bonusService.update(bonus));
    } else {
      this.subscribeToSaveResponse(this.bonusService.create(bonus));
    }
  }

  private createFromForm(): IBonus {
    return {
      ...new Bonus(),
      id: this.editForm.get(['id'])!.value,
      amount: this.editForm.get(['amount'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      principalAmount: this.editForm.get(['principalAmount'])!.value,
      status: this.editForm.get(['status'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      buyerId: this.editForm.get(['buyerId'])!.value,
      orderId: this.editForm.get(['orderId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBonus>>): void {
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
