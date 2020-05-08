import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IRating, Rating } from 'app/shared/model/rating.model';
import { RatingService } from './rating.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from 'app/entities/order/order.service';

type SelectableEntity = IUserProfile | IOrder;

@Component({
  selector: 'jhi-rating-update',
  templateUrl: './rating-update.component.html'
})
export class RatingUpdateComponent implements OnInit {
  isSaving = false;
  userprofiles: IUserProfile[] = [];
  orders: IOrder[] = [];

  editForm = this.fb.group({
    id: [],
    rating: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    remarks: [null, [Validators.maxLength(255)]],
    fromProfileId: [null, Validators.required],
    toProfileId: [null, Validators.required],
    orderId: [null, Validators.required]
  });

  constructor(
    protected ratingService: RatingService,
    protected userProfileService: UserProfileService,
    protected orderService: OrderService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rating }) => {
      if (!rating.id) {
        const today = moment().startOf('day');
        rating.createdAt = today;
      }

      this.updateForm(rating);

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));

      this.orderService.query().subscribe((res: HttpResponse<IOrder[]>) => (this.orders = res.body || []));
    });
  }

  updateForm(rating: IRating): void {
    this.editForm.patchValue({
      id: rating.id,
      rating: rating.rating,
      createdAt: rating.createdAt ? rating.createdAt.format(DATE_TIME_FORMAT) : null,
      remarks: rating.remarks,
      fromProfileId: rating.fromProfileId,
      toProfileId: rating.toProfileId,
      orderId: rating.orderId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rating = this.createFromForm();
    if (rating.id !== undefined) {
      this.subscribeToSaveResponse(this.ratingService.update(rating));
    } else {
      this.subscribeToSaveResponse(this.ratingService.create(rating));
    }
  }

  private createFromForm(): IRating {
    return {
      ...new Rating(),
      id: this.editForm.get(['id'])!.value,
      rating: this.editForm.get(['rating'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      remarks: this.editForm.get(['remarks'])!.value,
      fromProfileId: this.editForm.get(['fromProfileId'])!.value,
      toProfileId: this.editForm.get(['toProfileId'])!.value,
      orderId: this.editForm.get(['orderId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRating>>): void {
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
