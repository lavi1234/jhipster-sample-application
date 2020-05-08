import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserCategoryMapping } from 'app/shared/model/user-category-mapping.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { UserCategoryMappingService } from './user-category-mapping.service';
import { UserCategoryMappingDeleteDialogComponent } from './user-category-mapping-delete-dialog.component';

@Component({
  selector: 'jhi-user-category-mapping',
  templateUrl: './user-category-mapping.component.html'
})
export class UserCategoryMappingComponent implements OnInit, OnDestroy {
  userCategoryMappings?: IUserCategoryMapping[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected userCategoryMappingService: UserCategoryMappingService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.userCategoryMappingService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IUserCategoryMapping[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInUserCategoryMappings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserCategoryMapping): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserCategoryMappings(): void {
    this.eventSubscriber = this.eventManager.subscribe('userCategoryMappingListModification', () => this.loadPage());
  }

  delete(userCategoryMapping: IUserCategoryMapping): void {
    const modalRef = this.modalService.open(UserCategoryMappingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userCategoryMapping = userCategoryMapping;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IUserCategoryMapping[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/user-category-mapping'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.userCategoryMappings = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
