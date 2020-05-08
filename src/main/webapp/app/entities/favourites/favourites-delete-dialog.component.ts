import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFavourites } from 'app/shared/model/favourites.model';
import { FavouritesService } from './favourites.service';

@Component({
  templateUrl: './favourites-delete-dialog.component.html'
})
export class FavouritesDeleteDialogComponent {
  favourites?: IFavourites;

  constructor(
    protected favouritesService: FavouritesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.favouritesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('favouritesListModification');
      this.activeModal.close();
    });
  }
}
