import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { StaticPagesComponent } from './static-pages.component';
import { StaticPagesDetailComponent } from './static-pages-detail.component';
import { StaticPagesUpdateComponent } from './static-pages-update.component';
import { StaticPagesDeleteDialogComponent } from './static-pages-delete-dialog.component';
import { staticPagesRoute } from './static-pages.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(staticPagesRoute)],
  declarations: [StaticPagesComponent, StaticPagesDetailComponent, StaticPagesUpdateComponent, StaticPagesDeleteDialogComponent],
  entryComponents: [StaticPagesDeleteDialogComponent]
})
export class JhipsterSampleApplicationStaticPagesModule {}
