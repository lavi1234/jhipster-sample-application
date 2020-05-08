import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { FavouritesComponent } from './favourites.component';
import { FavouritesDetailComponent } from './favourites-detail.component';
import { FavouritesUpdateComponent } from './favourites-update.component';
import { FavouritesDeleteDialogComponent } from './favourites-delete-dialog.component';
import { favouritesRoute } from './favourites.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(favouritesRoute)],
  declarations: [FavouritesComponent, FavouritesDetailComponent, FavouritesUpdateComponent, FavouritesDeleteDialogComponent],
  entryComponents: [FavouritesDeleteDialogComponent]
})
export class JhipsterSampleApplicationFavouritesModule {}
