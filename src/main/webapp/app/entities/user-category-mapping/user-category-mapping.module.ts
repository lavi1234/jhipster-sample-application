import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { UserCategoryMappingComponent } from './user-category-mapping.component';
import { UserCategoryMappingDetailComponent } from './user-category-mapping-detail.component';
import { UserCategoryMappingUpdateComponent } from './user-category-mapping-update.component';
import { UserCategoryMappingDeleteDialogComponent } from './user-category-mapping-delete-dialog.component';
import { userCategoryMappingRoute } from './user-category-mapping.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(userCategoryMappingRoute)],
  declarations: [
    UserCategoryMappingComponent,
    UserCategoryMappingDetailComponent,
    UserCategoryMappingUpdateComponent,
    UserCategoryMappingDeleteDialogComponent
  ],
  entryComponents: [UserCategoryMappingDeleteDialogComponent]
})
export class JhipsterSampleApplicationUserCategoryMappingModule {}
