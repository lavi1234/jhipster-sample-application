import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEnquiryMaterial, EnquiryMaterial } from 'app/shared/model/enquiry-material.model';
import { EnquiryMaterialService } from './enquiry-material.service';
import { IEnquiryDetails } from 'app/shared/model/enquiry-details.model';
import { EnquiryDetailsService } from 'app/entities/enquiry-details/enquiry-details.service';

@Component({
  selector: 'jhi-enquiry-material-update',
  templateUrl: './enquiry-material-update.component.html'
})
export class EnquiryMaterialUpdateComponent implements OnInit {
  isSaving = false;
  enquirydetails: IEnquiryDetails[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(200)]],
    dimension: [null, [Validators.required, Validators.maxLength(200)]],
    materialId: [null, [Validators.required]],
    color: [null, [Validators.required, Validators.maxLength(200)]],
    comments: [null, [Validators.maxLength(255)]],
    enquiryDetailsId: [null, Validators.required]
  });

  constructor(
    protected enquiryMaterialService: EnquiryMaterialService,
    protected enquiryDetailsService: EnquiryDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enquiryMaterial }) => {
      this.updateForm(enquiryMaterial);

      this.enquiryDetailsService.query().subscribe((res: HttpResponse<IEnquiryDetails[]>) => (this.enquirydetails = res.body || []));
    });
  }

  updateForm(enquiryMaterial: IEnquiryMaterial): void {
    this.editForm.patchValue({
      id: enquiryMaterial.id,
      name: enquiryMaterial.name,
      dimension: enquiryMaterial.dimension,
      materialId: enquiryMaterial.materialId,
      color: enquiryMaterial.color,
      comments: enquiryMaterial.comments,
      enquiryDetailsId: enquiryMaterial.enquiryDetailsId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enquiryMaterial = this.createFromForm();
    if (enquiryMaterial.id !== undefined) {
      this.subscribeToSaveResponse(this.enquiryMaterialService.update(enquiryMaterial));
    } else {
      this.subscribeToSaveResponse(this.enquiryMaterialService.create(enquiryMaterial));
    }
  }

  private createFromForm(): IEnquiryMaterial {
    return {
      ...new EnquiryMaterial(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      dimension: this.editForm.get(['dimension'])!.value,
      materialId: this.editForm.get(['materialId'])!.value,
      color: this.editForm.get(['color'])!.value,
      comments: this.editForm.get(['comments'])!.value,
      enquiryDetailsId: this.editForm.get(['enquiryDetailsId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnquiryMaterial>>): void {
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
