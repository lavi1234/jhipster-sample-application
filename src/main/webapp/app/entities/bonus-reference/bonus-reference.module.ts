import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { BonusReferenceComponent } from './bonus-reference.component';
import { BonusReferenceDetailComponent } from './bonus-reference-detail.component';
import { BonusReferenceUpdateComponent } from './bonus-reference-update.component';
import { BonusReferenceDeleteDialogComponent } from './bonus-reference-delete-dialog.component';
import { bonusReferenceRoute } from './bonus-reference.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(bonusReferenceRoute)],
  declarations: [
    BonusReferenceComponent,
    BonusReferenceDetailComponent,
    BonusReferenceUpdateComponent,
    BonusReferenceDeleteDialogComponent
  ],
  entryComponents: [BonusReferenceDeleteDialogComponent]
})
export class JhipsterSampleApplicationBonusReferenceModule {}
