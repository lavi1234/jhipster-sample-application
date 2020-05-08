import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISupplierEnquiryMapping } from 'app/shared/model/supplier-enquiry-mapping.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SupplierEnquiryMappingService } from './supplier-enquiry-mapping.service';
import { SupplierEnquiryMappingDeleteDialogComponent } from './supplier-enquiry-mapping-delete-dialog.component';

@Component({
  selector: 'jhi-supplier-enquiry-mapping',
  templateUrl: './supplier-enquiry-mapping.component.html'
})
export class SupplierEnquiryMappingComponent implements OnInit, OnDestroy {
  supplierEnquiryMappings?: ISupplierEnquiryMapping[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected supplierEnquiryMappingService: SupplierEnquiryMappingService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.supplierEnquiryMappingService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<ISupplierEnquiryMapping[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInSupplierEnquiryMappings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISupplierEnquiryMapping): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSupplierEnquiryMappings(): void {
    this.eventSubscriber = this.eventManager.subscribe('supplierEnquiryMappingListModification', () => this.loadPage());
  }

  delete(supplierEnquiryMapping: ISupplierEnquiryMapping): void {
    const modalRef = this.modalService.open(SupplierEnquiryMappingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.supplierEnquiryMapping = supplierEnquiryMapping;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: ISupplierEnquiryMapping[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/supplier-enquiry-mapping'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.supplierEnquiryMappings = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
