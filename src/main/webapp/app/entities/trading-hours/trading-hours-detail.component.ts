import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITradingHours } from 'app/shared/model/trading-hours.model';

@Component({
  selector: 'jhi-trading-hours-detail',
  templateUrl: './trading-hours-detail.component.html'
})
export class TradingHoursDetailComponent implements OnInit {
  tradingHours: ITradingHours | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tradingHours }) => (this.tradingHours = tradingHours));
  }

  previousState(): void {
    window.history.back();
  }
}
