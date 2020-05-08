import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAppFeatures } from 'app/shared/model/app-features.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AppFeaturesService } from './app-features.service';
import { AppFeaturesDeleteDialogComponent } from './app-features-delete-dialog.component';

@Component({
  selector: 'jhi-app-features',
  templateUrl: './app-features.component.html'
})
export class AppFeaturesComponent implements OnInit, OnDestroy {
  appFeatures?: IAppFeatures[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected appFeaturesService: AppFeaturesService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.appFeaturesService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IAppFeatures[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInAppFeatures();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAppFeatures): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAppFeatures(): void {
    this.eventSubscriber = this.eventManager.subscribe('appFeaturesListModification', () => this.loadPage());
  }

  delete(appFeatures: IAppFeatures): void {
    const modalRef = this.modalService.open(AppFeaturesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.appFeatures = appFeatures;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IAppFeatures[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/app-features'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.appFeatures = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
