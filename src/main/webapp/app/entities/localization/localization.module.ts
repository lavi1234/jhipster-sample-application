import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { LocalizationComponent } from './localization.component';
import { LocalizationDetailComponent } from './localization-detail.component';
import { LocalizationUpdateComponent } from './localization-update.component';
import { LocalizationDeleteDialogComponent } from './localization-delete-dialog.component';
import { localizationRoute } from './localization.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(localizationRoute)],
  declarations: [LocalizationComponent, LocalizationDetailComponent, LocalizationUpdateComponent, LocalizationDeleteDialogComponent],
  entryComponents: [LocalizationDeleteDialogComponent]
})
export class JhipsterSampleApplicationLocalizationModule {}
