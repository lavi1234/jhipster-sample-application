import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICommissionReference, CommissionReference } from 'app/shared/model/commission-reference.model';
import { CommissionReferenceService } from './commission-reference.service';

@Component({
  selector: 'jhi-commission-reference-update',
  templateUrl: './commission-reference-update.component.html'
})
export class CommissionReferenceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    percentage: [null, [Validators.required]],
    holdDays: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]]
  });

  constructor(
    protected commissionReferenceService: CommissionReferenceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commissionReference }) => {
      if (!commissionReference.id) {
        const today = moment().startOf('day');
        commissionReference.createdAt = today;
        commissionReference.updatedAt = today;
      }

      this.updateForm(commissionReference);
    });
  }

  updateForm(commissionReference: ICommissionReference): void {
    this.editForm.patchValue({
      id: commissionReference.id,
      percentage: commissionReference.percentage,
      holdDays: commissionReference.holdDays,
      createdAt: commissionReference.createdAt ? commissionReference.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: commissionReference.updatedAt ? commissionReference.updatedAt.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commissionReference = this.createFromForm();
    if (commissionReference.id !== undefined) {
      this.subscribeToSaveResponse(this.commissionReferenceService.update(commissionReference));
    } else {
      this.subscribeToSaveResponse(this.commissionReferenceService.create(commissionReference));
    }
  }

  private createFromForm(): ICommissionReference {
    return {
      ...new CommissionReference(),
      id: this.editForm.get(['id'])!.value,
      percentage: this.editForm.get(['percentage'])!.value,
      holdDays: this.editForm.get(['holdDays'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommissionReference>>): void {
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
}
