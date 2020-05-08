import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEnquiryDetails, EnquiryDetails } from 'app/shared/model/enquiry-details.model';
import { EnquiryDetailsService } from './enquiry-details.service';
import { EnquiryDetailsComponent } from './enquiry-details.component';
import { EnquiryDetailsDetailComponent } from './enquiry-details-detail.component';
import { EnquiryDetailsUpdateComponent } from './enquiry-details-update.component';

@Injectable({ providedIn: 'root' })
export class EnquiryDetailsResolve implements Resolve<IEnquiryDetails> {
  constructor(private service: EnquiryDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnquiryDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((enquiryDetails: HttpResponse<EnquiryDetails>) => {
          if (enquiryDetails.body) {
            return of(enquiryDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EnquiryDetails());
  }
}

export const enquiryDetailsRoute: Routes = [
  {
    path: '',
    component: EnquiryDetailsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.enquiryDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EnquiryDetailsDetailComponent,
    resolve: {
      enquiryDetails: EnquiryDetailsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.enquiryDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EnquiryDetailsUpdateComponent,
    resolve: {
      enquiryDetails: EnquiryDetailsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.enquiryDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EnquiryDetailsUpdateComponent,
    resolve: {
      enquiryDetails: EnquiryDetailsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.enquiryDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
