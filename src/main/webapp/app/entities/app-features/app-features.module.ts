import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { AppFeaturesComponent } from './app-features.component';
import { AppFeaturesDetailComponent } from './app-features-detail.component';
import { AppFeaturesUpdateComponent } from './app-features-update.component';
import { AppFeaturesDeleteDialogComponent } from './app-features-delete-dialog.component';
import { appFeaturesRoute } from './app-features.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(appFeaturesRoute)],
  declarations: [AppFeaturesComponent, AppFeaturesDetailComponent, AppFeaturesUpdateComponent, AppFeaturesDeleteDialogComponent],
  entryComponents: [AppFeaturesDeleteDialogComponent]
})
export class JhipsterSampleApplicationAppFeaturesModule {}
