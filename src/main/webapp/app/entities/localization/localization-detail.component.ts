import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocalization } from 'app/shared/model/localization.model';

@Component({
  selector: 'jhi-localization-detail',
  templateUrl: './localization-detail.component.html'
})
export class LocalizationDetailComponent implements OnInit {
  localization: ILocalization | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ localization }) => (this.localization = localization));
  }

  previousState(): void {
    window.history.back();
  }
}
