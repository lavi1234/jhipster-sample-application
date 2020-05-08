import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserCategoryMapping } from 'app/shared/model/user-category-mapping.model';

@Component({
  selector: 'jhi-user-category-mapping-detail',
  templateUrl: './user-category-mapping-detail.component.html'
})
export class UserCategoryMappingDetailComponent implements OnInit {
  userCategoryMapping: IUserCategoryMapping | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userCategoryMapping }) => (this.userCategoryMapping = userCategoryMapping));
  }

  previousState(): void {
    window.history.back();
  }
}
