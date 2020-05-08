import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStaticPages } from 'app/shared/model/static-pages.model';
import { StaticPagesService } from './static-pages.service';

@Component({
  templateUrl: './static-pages-delete-dialog.component.html'
})
export class StaticPagesDeleteDialogComponent {
  staticPages?: IStaticPages;

  constructor(
    protected staticPagesService: StaticPagesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.staticPagesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('staticPagesListModification');
      this.activeModal.close();
    });
  }
}
