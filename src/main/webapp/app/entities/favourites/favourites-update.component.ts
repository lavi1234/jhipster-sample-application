import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFavourites, Favourites } from 'app/shared/model/favourites.model';
import { FavouritesService } from './favourites.service';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

@Component({
  selector: 'jhi-favourites-update',
  templateUrl: './favourites-update.component.html'
})
export class FavouritesUpdateComponent implements OnInit {
  isSaving = false;
  userprofiles: IUserProfile[] = [];

  editForm = this.fb.group({
    id: [],
    createdAt: [null, [Validators.required]],
    remarks: [null, [Validators.maxLength(255)]],
    fromProfileId: [null, Validators.required],
    toProfileId: [null, Validators.required]
  });

  constructor(
    protected favouritesService: FavouritesService,
    protected userProfileService: UserProfileService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ favourites }) => {
      if (!favourites.id) {
        const today = moment().startOf('day');
        favourites.createdAt = today;
      }

      this.updateForm(favourites);

      this.userProfileService.query().subscribe((res: HttpResponse<IUserProfile[]>) => (this.userprofiles = res.body || []));
    });
  }

  updateForm(favourites: IFavourites): void {
    this.editForm.patchValue({
      id: favourites.id,
      createdAt: favourites.createdAt ? favourites.createdAt.format(DATE_TIME_FORMAT) : null,
      remarks: favourites.remarks,
      fromProfileId: favourites.fromProfileId,
      toProfileId: favourites.toProfileId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const favourites = this.createFromForm();
    if (favourites.id !== undefined) {
      this.subscribeToSaveResponse(this.favouritesService.update(favourites));
    } else {
      this.subscribeToSaveResponse(this.favouritesService.create(favourites));
    }
  }

  private createFromForm(): IFavourites {
    return {
      ...new Favourites(),
      id: this.editForm.get(['id'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      remarks: this.editForm.get(['remarks'])!.value,
      fromProfileId: this.editForm.get(['fromProfileId'])!.value,
      toProfileId: this.editForm.get(['toProfileId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFavourites>>): void {
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
