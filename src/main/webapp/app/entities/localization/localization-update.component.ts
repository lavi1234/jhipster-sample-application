import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILocalization, Localization } from 'app/shared/model/localization.model';
import { LocalizationService } from './localization.service';

@Component({
  selector: 'jhi-localization-update',
  templateUrl: './localization-update.component.html'
})
export class LocalizationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    labelEn: [null, [Validators.required, Validators.maxLength(255)]],
    labelDe: [null, [Validators.required, Validators.maxLength(255)]]
  });

  constructor(protected localizationService: LocalizationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ localization }) => {
      this.updateForm(localization);
    });
  }

  updateForm(localization: ILocalization): void {
    this.editForm.patchValue({
      id: localization.id,
      labelEn: localization.labelEn,
      labelDe: localization.labelDe
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const localization = this.createFromForm();
    if (localization.id !== undefined) {
      this.subscribeToSaveResponse(this.localizationService.update(localization));
    } else {
      this.subscribeToSaveResponse(this.localizationService.create(localization));
    }
  }

  private createFromForm(): ILocalization {
    return {
      ...new Localization(),
      id: this.editForm.get(['id'])!.value,
      labelEn: this.editForm.get(['labelEn'])!.value,
      labelDe: this.editForm.get(['labelDe'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocalization>>): void {
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
