import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEnquiryNote, EnquiryNote } from 'app/shared/model/enquiry-note.model';
import { EnquiryNoteService } from './enquiry-note.service';
import { IEnquiryDetails } from 'app/shared/model/enquiry-details.model';
import { EnquiryDetailsService } from 'app/entities/enquiry-details/enquiry-details.service';

@Component({
  selector: 'jhi-enquiry-note-update',
  templateUrl: './enquiry-note-update.component.html'
})
export class EnquiryNoteUpdateComponent implements OnInit {
  isSaving = false;
  enquirydetails: IEnquiryDetails[] = [];

  editForm = this.fb.group({
    id: [],
    note: [null, [Validators.required, Validators.maxLength(255)]],
    createdAt: [null, [Validators.required]],
    enquiryDetailsId: [null, Validators.required]
  });

  constructor(
    protected enquiryNoteService: EnquiryNoteService,
    protected enquiryDetailsService: EnquiryDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enquiryNote }) => {
      if (!enquiryNote.id) {
        const today = moment().startOf('day');
        enquiryNote.createdAt = today;
      }

      this.updateForm(enquiryNote);

      this.enquiryDetailsService.query().subscribe((res: HttpResponse<IEnquiryDetails[]>) => (this.enquirydetails = res.body || []));
    });
  }

  updateForm(enquiryNote: IEnquiryNote): void {
    this.editForm.patchValue({
      id: enquiryNote.id,
      note: enquiryNote.note,
      createdAt: enquiryNote.createdAt ? enquiryNote.createdAt.format(DATE_TIME_FORMAT) : null,
      enquiryDetailsId: enquiryNote.enquiryDetailsId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enquiryNote = this.createFromForm();
    if (enquiryNote.id !== undefined) {
      this.subscribeToSaveResponse(this.enquiryNoteService.update(enquiryNote));
    } else {
      this.subscribeToSaveResponse(this.enquiryNoteService.create(enquiryNote));
    }
  }

  private createFromForm(): IEnquiryNote {
    return {
      ...new EnquiryNote(),
      id: this.editForm.get(['id'])!.value,
      note: this.editForm.get(['note'])!.value,
      createdAt: this.editForm.get(['createdAt'])!.value ? moment(this.editForm.get(['createdAt'])!.value, DATE_TIME_FORMAT) : undefined,
      enquiryDetailsId: this.editForm.get(['enquiryDetailsId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnquiryNote>>): void {
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

  trackById(index: number, item: IEnquiryDetails): any {
    return item.id;
  }
}
