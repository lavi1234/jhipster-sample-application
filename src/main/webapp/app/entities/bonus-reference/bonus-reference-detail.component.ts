import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBonusReference } from 'app/shared/model/bonus-reference.model';

@Component({
  selector: 'jhi-bonus-reference-detail',
  templateUrl: './bonus-reference-detail.component.html'
})
export class BonusReferenceDetailComponent implements OnInit {
  bonusReference: IBonusReference | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bonusReference }) => (this.bonusReference = bonusReference));
  }

  previousState(): void {
    window.history.back();
  }
}
