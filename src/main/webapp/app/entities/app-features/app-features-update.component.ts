import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAppFeatures, AppFeatures } from 'app/shared/model/app-features.model';
import { AppFeaturesService } from './app-features.service';
import { ILocalization } from 'app/shared/model/localization.model';
import { LocalizationService } from 'app/entities/localization/localization.service';

@Component({
  selector: 'jhi-app-features-update',
  templateUrl: './app-features-update.component.html'
})
export class AppFeaturesUpdateComponent implements OnInit {
  isSaving = false;
  localizations: ILocalization[] = [];

  editForm = this.fb.group({
    id: [],
    menuSortNumber: [null, [Validators.required]],
    nameId: [null, Validators.required]
  });

  constructor(
    protected appFeaturesService: AppFeaturesService,
    protected localizationService: LocalizationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appFeatures }) => {
      this.updateForm(appFeatures);

      this.localizationService.query().subscribe((res: HttpResponse<ILocalization[]>) => (this.localizations = res.body || []));
    });
  }

  updateForm(appFeatures: IAppFeatures): void {
    this.editForm.patchValue({
      id: appFeatures.id,
      menuSortNumber: appFeatures.menuSortNumber,
      nameId: appFeatures.nameId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const appFeatures = this.createFromForm();
    if (appFeatures.id !== undefined) {
      this.subscribeToSaveResponse(this.appFeaturesService.update(appFeatures));
    } else {
      this.subscribeToSaveResponse(this.appFeaturesService.create(appFeatures));
    }
  }

  private createFromForm(): IAppFeatures {
    return {
      ...new AppFeatures(),
      id: this.editForm.get(['id'])!.value,
      menuSortNumber: this.editForm.get(['menuSortNumber'])!.value,
      nameId: this.editForm.get(['nameId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppFeatures>>): void {
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

  trackById(index: number, item: ILocalization): any {
    return item.id;
  }
}
