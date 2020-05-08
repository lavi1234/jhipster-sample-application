import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITradingHours, TradingHours } from 'app/shared/model/trading-hours.model';
import { TradingHoursService } from './trading-hours.service';
import { ICompany } from 'app/shared/model/company.model';
import { CompanyService } from 'app/entities/company/company.service';

@Component({
  selector: 'jhi-trading-hours-update',
  templateUrl: './trading-hours-update.component.html'
})
export class TradingHoursUpdateComponent implements OnInit {
  isSaving = false;
  companies: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    day: [null, [Validators.required, Validators.maxLength(200)]],
    startTime: [
      null,
      [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(8),
        Validators.pattern('^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$')
      ]
    ],
    endTime: [
      null,
      [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(8),
        Validators.pattern('^(([0-1]\\d)|(2[0-3])):([0-5]\\d):([0-5]\\d)$')
      ]
    ],
    companyId: [null, Validators.required]
  });

  constructor(
    protected tradingHoursService: TradingHoursService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tradingHours }) => {
      this.updateForm(tradingHours);

      this.companyService.query().subscribe((res: HttpResponse<ICompany[]>) => (this.companies = res.body || []));
    });
  }

  updateForm(tradingHours: ITradingHours): void {
    this.editForm.patchValue({
      id: tradingHours.id,
      day: tradingHours.day,
      startTime: tradingHours.startTime,
      endTime: tradingHours.endTime,
      companyId: tradingHours.companyId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tradingHours = this.createFromForm();
    if (tradingHours.id !== undefined) {
      this.subscribeToSaveResponse(this.tradingHoursService.update(tradingHours));
    } else {
      this.subscribeToSaveResponse(this.tradingHoursService.create(tradingHours));
    }
  }

  private createFromForm(): ITradingHours {
    return {
      ...new TradingHours(),
      id: this.editForm.get(['id'])!.value,
      day: this.editForm.get(['day'])!.value,
      startTime: this.editForm.get(['startTime'])!.value,
      endTime: this.editForm.get(['endTime'])!.value,
      companyId: this.editForm.get(['companyId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITradingHours>>): void {
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

  trackById(index: number, item: ICompany): any {
    return item.id;
  }
}
