import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { TradingHoursComponent } from './trading-hours.component';
import { TradingHoursDetailComponent } from './trading-hours-detail.component';
import { TradingHoursUpdateComponent } from './trading-hours-update.component';
import { TradingHoursDeleteDialogComponent } from './trading-hours-delete-dialog.component';
import { tradingHoursRoute } from './trading-hours.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(tradingHoursRoute)],
  declarations: [TradingHoursComponent, TradingHoursDetailComponent, TradingHoursUpdateComponent, TradingHoursDeleteDialogComponent],
  entryComponents: [TradingHoursDeleteDialogComponent]
})
export class JhipsterSampleApplicationTradingHoursModule {}
