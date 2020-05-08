import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBonusReference, BonusReference } from 'app/shared/model/bonus-reference.model';
import { BonusReferenceService } from './bonus-reference.service';

@Component({
  selector: 'jhi-bonus-reference-update',
  templateUrl: './bonus-reference-update.component.html'
})
export class BonusReferenceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    percentage: [null, [Validators.required]],
    holdDays: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]]
  });

  constructor(protected bonusReferenceService: BonusReferenceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bonusReference }) => {
      if (!bonusReference.id) {
        const today = moment().startOf('day');
        bonusReference.createdAt = today;
        bonusReference.updatedAt = today;
      }

      this.updateForm(bonusReference);
    });
  }

  updateForm(bonusReference: IBonusReference): void {
    this.editForm.patchValue({
      id: bonusReference.id,
      percentage: bonusReference.percentage,
      holdDays: bonusReference.holdDays,
      createdAt: bonusReference.createdAt ? bonusReference.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: bonusReference.updatedAt ? bonusReference.updatedAt.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bonusReference = this.createFromForm();
    if (bonusReference.id !== undefined) {
      this.subscribeToSaveResponse(this.bonusReferenceService.update(bonusReference));
    } else {
      this.subscribeToSaveResponse(this.bonusReferenceService.create(bonusReference));
    }
  }

  private createFromForm(): IBonusReference {
    return {
      ...new BonusReference(),
      id: this.editForm.get(['id'])!.value,
      percentage: this.editForm.get(['percentage'])!.value,
      holdDays: this.editForm.get(['holdDays'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBonusReference>>): void {
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
