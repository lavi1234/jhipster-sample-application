import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAppFeatures } from 'app/shared/model/app-features.model';

@Component({
  selector: 'jhi-app-features-detail',
  templateUrl: './app-features-detail.component.html'
})
export class AppFeaturesDetailComponent implements OnInit {
  appFeatures: IAppFeatures | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ appFeatures }) => (this.appFeatures = appFeatures));
  }

  previousState(): void {
    window.history.back();
  }
}
