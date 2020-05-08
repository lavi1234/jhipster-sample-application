import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICommissionReference } from 'app/shared/model/commission-reference.model';

@Component({
  selector: 'jhi-commission-reference-detail',
  templateUrl: './commission-reference-detail.component.html'
})
export class CommissionReferenceDetailComponent implements OnInit {
  commissionReference: ICommissionReference | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commissionReference }) => (this.commissionReference = commissionReference));
  }

  previousState(): void {
    window.history.back();
  }
}
