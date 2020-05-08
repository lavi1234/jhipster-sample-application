import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEnquiry, Enquiry } from 'app/shared/model/enquiry.model';
import { EnquiryService } from './enquiry.service';
import { EnquiryComponent } from './enquiry.component';
import { EnquiryDetailComponent } from './enquiry-detail.component';
import { EnquiryUpdateComponent } from './enquiry-update.component';

@Injectable({ providedIn: 'root' })
export class EnquiryResolve implements Resolve<IEnquiry> {
  constructor(private service: EnquiryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnquiry> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((enquiry: HttpResponse<Enquiry>) => {
          if (enquiry.body) {
            return of(enquiry.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Enquiry());
  }
}

export const enquiryRoute: Routes = [
  {
    path: '',
    component: EnquiryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.enquiry.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EnquiryDetailComponent,
    resolve: {
      enquiry: EnquiryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.enquiry.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EnquiryUpdateComponent,
    resolve: {
      enquiry: EnquiryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.enquiry.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EnquiryUpdateComponent,
    resolve: {
      enquiry: EnquiryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.enquiry.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
