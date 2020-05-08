import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserCategoryMapping } from 'app/shared/model/user-category-mapping.model';
import { UserCategoryMappingService } from './user-category-mapping.service';

@Component({
  templateUrl: './user-category-mapping-delete-dialog.component.html'
})
export class UserCategoryMappingDeleteDialogComponent {
  userCategoryMapping?: IUserCategoryMapping;

  constructor(
    protected userCategoryMappingService: UserCategoryMappingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userCategoryMappingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userCategoryMappingListModification');
      this.activeModal.close();
    });
  }
}
