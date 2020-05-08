import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubsriptionPlanFeature } from 'app/shared/model/subsription-plan-feature.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SubsriptionPlanFeatureService } from './subsription-plan-feature.service';
import { SubsriptionPlanFeatureDeleteDialogComponent } from './subsription-plan-feature-delete-dialog.component';

@Component({
  selector: 'jhi-subsription-plan-feature',
  templateUrl: './subsription-plan-feature.component.html'
})
export class SubsriptionPlanFeatureComponent implements OnInit, OnDestroy {
  subsriptionPlanFeatures?: ISubsriptionPlanFeature[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected subsriptionPlanFeatureService: SubsriptionPlanFeatureService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.subsriptionPlanFeatureService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ISubsriptionPlanFeature[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInSubsriptionPlanFeatures();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISubsriptionPlanFeature): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSubsriptionPlanFeatures(): void {
    this.eventSubscriber = this.eventManager.subscribe('subsriptionPlanFeatureListModification', () => this.loadPage());
  }

  delete(subsriptionPlanFeature: ISubsriptionPlanFeature): void {
    const modalRef = this.modalService.open(SubsriptionPlanFeatureDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.subsriptionPlanFeature = subsriptionPlanFeature;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ISubsriptionPlanFeature[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/subsription-plan-feature'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.subsriptionPlanFeatures = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
