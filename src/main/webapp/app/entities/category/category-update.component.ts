import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICategory, Category } from 'app/shared/model/category.model';
import { CategoryService } from './category.service';
import { ILocalization } from 'app/shared/model/localization.model';
import { LocalizationService } from 'app/entities/localization/localization.service';

type SelectableEntity = ILocalization | ICategory;

@Component({
  selector: 'jhi-category-update',
  templateUrl: './category-update.component.html'
})
export class CategoryUpdateComponent implements OnInit {
  isSaving = false;
  localizations: ILocalization[] = [];
  categories: ICategory[] = [];

  editForm = this.fb.group({
    id: [],
    createdBy: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    nameId: [null, Validators.required],
    descriptionId: [],
    parentId: []
  });

  constructor(
    protected categoryService: CategoryService,
    protected localizationService: LocalizationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ category }) => {
      if (!category.id) {
        const today = moment().startOf('day');
        category.createdAt = today;
        category.updatedAt = today;
      }

      this.updateForm(category);

      this.localizationService.query().subscribe((res: HttpResponse<ILocalization[]>) => (this.localizations = res.body || []));

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));
    });
  }

  updateForm(category: ICategory): void {
    this.editForm.patchValue({
      id: category.id,
      createdBy: category.createdBy,
      createdAt: category.createdAt ? category.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: category.updatedAt ? category.updatedAt.format(DATE_TIME_FORMAT) : null,
      nameId: category.nameId,
      descriptionId: category.descriptionId,
      parentId: category.parentId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const category = this.createFromForm();
    if (category.id !== undefined) {
      this.subscribeToSaveResponse(this.categoryService.update(category));
    } else {
      this.subscribeToSaveResponse(this.categoryService.create(category));
    }
  }

  private createFromForm(): ICategory {
    return {
      ...new Category(),
      id: this.editForm.get(['id'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      nameId: this.editForm.get(['nameId'])!.value,
      descriptionId: this.editForm.get(['descriptionId'])!.value,
      parentId: this.editForm.get(['parentId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategory>>): void {
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
