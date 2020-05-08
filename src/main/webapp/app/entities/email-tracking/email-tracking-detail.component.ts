import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmailTracking } from 'app/shared/model/email-tracking.model';

@Component({
  selector: 'jhi-email-tracking-detail',
  templateUrl: './email-tracking-detail.component.html'
})
export class EmailTrackingDetailComponent implements OnInit {
  emailTracking: IEmailTracking | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emailTracking }) => (this.emailTracking = emailTracking));
  }

  previousState(): void {
    window.history.back();
  }
}
