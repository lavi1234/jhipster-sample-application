import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEnquiryNote } from 'app/shared/model/enquiry-note.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EnquiryNoteService } from './enquiry-note.service';
import { EnquiryNoteDeleteDialogComponent } from './enquiry-note-delete-dialog.component';

@Component({
  selector: 'jhi-enquiry-note',
  templateUrl: './enquiry-note.component.html'
})
export class EnquiryNoteComponent implements OnInit, OnDestroy {
  enquiryNotes?: IEnquiryNote[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected enquiryNoteService: EnquiryNoteService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.enquiryNoteService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IEnquiryNote[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInEnquiryNotes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEnquiryNote): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEnquiryNotes(): void {
    this.eventSubscriber = this.eventManager.subscribe('enquiryNoteListModification', () => this.loadPage());
  }

  delete(enquiryNote: IEnquiryNote): void {
    const modalRef = this.modalService.open(EnquiryNoteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.enquiryNote = enquiryNote;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IEnquiryNote[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/enquiry-note'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.enquiryNotes = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
