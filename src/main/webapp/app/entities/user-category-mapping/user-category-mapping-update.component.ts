import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserCategoryMapping, UserCategoryMapping } from 'app/shared/model/user-category-mapping.model';
import { UserCategoryMappingService } from './user-category-mapping.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';

type SelectableEntity = IUserProfile | ICategory;

@Component({
  selector: 'jhi-user-category-mapping-update',
  templateUrl: './user-category-mapping-update.component.html'
})
export class UserCategoryMappingUpdateComponent implements OnInit {
  isSaving = false;
  userprofiles: IUserProfile[] = [];
  categories: ICategory[] = [];

  editForm = this.fb.group({
    id: [],
    userProfiles: [null, Validators.required],
    categories: [null, Validators.required]
  });

  constructor(
    protected userCategoryMappingService: UserCategoryMappingService,
    protected userProfileService: UserProfileService,
    protected categoryService: CategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userCategoryMapping }) => {
      this.updateForm(userCategoryMapping);

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));
    });
  }

  updateForm(userCategoryMapping: IUserCategoryMapping): void {
    this.editForm.patchValue({
      id: userCategoryMapping.id,
      userProfiles: userCategoryMapping.userProfiles,
      categories: userCategoryMapping.categories
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userCategoryMapping = this.createFromForm();
    if (userCategoryMapping.id !== undefined) {
      this.subscribeToSaveResponse(this.userCategoryMappingService.update(userCategoryMapping));
    } else {
      this.subscribeToSaveResponse(this.userCategoryMappingService.create(userCategoryMapping));
    }
  }

  private createFromForm(): IUserCategoryMapping {
    return {
      ...new UserCategoryMapping(),
      id: this.editForm.get(['id'])!.value,
      userProfiles: this.editForm.get(['userProfiles'])!.value,
      categories: this.editForm.get(['categories'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserCategoryMapping>>): void {
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

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
