import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEnquiryMaterial } from 'app/shared/model/enquiry-material.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EnquiryMaterialService } from './enquiry-material.service';
import { EnquiryMaterialDeleteDialogComponent } from './enquiry-material-delete-dialog.component';

@Component({
  selector: 'jhi-enquiry-material',
  templateUrl: './enquiry-material.component.html'
})
export class EnquiryMaterialComponent implements OnInit, OnDestroy {
  enquiryMaterials?: IEnquiryMaterial[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected enquiryMaterialService: EnquiryMaterialService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.enquiryMaterialService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEnquiryMaterial[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInEnquiryMaterials();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEnquiryMaterial): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEnquiryMaterials(): void {
    this.eventSubscriber = this.eventManager.subscribe('enquiryMaterialListModification', () => this.loadPage());
  }

  delete(enquiryMaterial: IEnquiryMaterial): void {
    const modalRef = this.modalService.open(EnquiryMaterialDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.enquiryMaterial = enquiryMaterial;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IEnquiryMaterial[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/enquiry-material'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.enquiryMaterials = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
