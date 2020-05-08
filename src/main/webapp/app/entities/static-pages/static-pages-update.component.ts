import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IStaticPages, StaticPages } from 'app/shared/model/static-pages.model';
import { StaticPagesService } from './static-pages.service';

@Component({
  selector: 'jhi-static-pages-update',
  templateUrl: './static-pages-update.component.html'
})
export class StaticPagesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    pageTitle: [null, [Validators.required, Validators.maxLength(255)]],
    heading: [null, [Validators.required, Validators.maxLength(255)]],
    description: [null, [Validators.maxLength(255)]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    publish: [null, [Validators.required]]
  });

  constructor(protected staticPagesService: StaticPagesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ staticPages }) => {
      if (!staticPages.id) {
        const today = moment().startOf('day');
        staticPages.createdAt = today;
        staticPages.updatedAt = today;
      }

      this.updateForm(staticPages);
    });
  }

  updateForm(staticPages: IStaticPages): void {
    this.editForm.patchValue({
      id: staticPages.id,
      pageTitle: staticPages.pageTitle,
      heading: staticPages.heading,
      description: staticPages.description,
      createdAt: staticPages.createdAt ? staticPages.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: staticPages.updatedAt ? staticPages.updatedAt.format(DATE_TIME_FORMAT) : null,
      publish: staticPages.publish
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const staticPages = this.createFromForm();
    if (staticPages.id !== undefined) {
      this.subscribeToSaveResponse(this.staticPagesService.update(staticPages));
    } else {
      this.subscribeToSaveResponse(this.staticPagesService.create(staticPages));
    }
  }

  private createFromForm(): IStaticPages {
    return {
      ...new StaticPages(),
      id: this.editForm.get(['id'])!.value,
      pageTitle: this.editForm.get(['pageTitle'])!.value,
      heading: this.editForm.get(['heading'])!.value,
      description: this.editForm.get(['description'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedAt: this.editForm.get(['updatedAt'])!.value ? moment(this.editForm.get(['updatedAt'])!.value, DATE_TIME_FORMAT) : undefined,
      publish: this.editForm.get(['publish'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStaticPages>>): void {
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
