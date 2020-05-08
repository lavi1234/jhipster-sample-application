import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEnquiryNote, EnquiryNote } from 'app/shared/model/enquiry-note.model';
import { EnquiryNoteService } from './enquiry-note.service';
import { EnquiryNoteComponent } from './enquiry-note.component';
import { EnquiryNoteDetailComponent } from './enquiry-note-detail.component';
import { EnquiryNoteUpdateComponent } from './enquiry-note-update.component';

@Injectable({ providedIn: 'root' })
export class EnquiryNoteResolve implements Resolve<IEnquiryNote> {
  constructor(private service: EnquiryNoteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnquiryNote> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((enquiryNote: HttpResponse<EnquiryNote>) => {
          if (enquiryNote.body) {
            return of(enquiryNote.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EnquiryNote());
  }
}

export const enquiryNoteRoute: Routes = [
  {
    path: '',
    component: EnquiryNoteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.enquiryNote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EnquiryNoteDetailComponent,
    resolve: {
      enquiryNote: EnquiryNoteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.enquiryNote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EnquiryNoteUpdateComponent,
    resolve: {
      enquiryNote: EnquiryNoteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.enquiryNote.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EnquiryNoteUpdateComponent,
    resolve: {
      enquiryNote: EnquiryNoteResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.enquiryNote.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
