import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmailTracking } from 'app/shared/model/email-tracking.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EmailTrackingService } from './email-tracking.service';
import { EmailTrackingDeleteDialogComponent } from './email-tracking-delete-dialog.component';

@Component({
  selector: 'jhi-email-tracking',
  templateUrl: './email-tracking.component.html'
})
export class EmailTrackingComponent implements OnInit, OnDestroy {
  emailTrackings?: IEmailTracking[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected emailTrackingService: EmailTrackingService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.emailTrackingService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEmailTracking[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInEmailTrackings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmailTracking): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmailTrackings(): void {
    this.eventSubscriber = this.eventManager.subscribe('emailTrackingListModification', () => this.loadPage());
  }

  delete(emailTracking: IEmailTracking): void {
    const modalRef = this.modalService.open(EmailTrackingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emailTracking = emailTracking;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IEmailTracking[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/email-tracking'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.emailTrackings = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
