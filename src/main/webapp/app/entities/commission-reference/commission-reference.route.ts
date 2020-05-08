import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommissionReference, CommissionReference } from 'app/shared/model/commission-reference.model';
import { CommissionReferenceService } from './commission-reference.service';
import { CommissionReferenceComponent } from './commission-reference.component';
import { CommissionReferenceDetailComponent } from './commission-reference-detail.component';
import { CommissionReferenceUpdateComponent } from './commission-reference-update.component';

@Injectable({ providedIn: 'root' })
export class CommissionReferenceResolve implements Resolve<ICommissionReference> {
  constructor(private service: CommissionReferenceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommissionReference> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commissionReference: HttpResponse<CommissionReference>) => {
          if (commissionReference.body) {
            return of(commissionReference.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommissionReference());
  }
}

export const commissionReferenceRoute: Routes = [
  {
    path: '',
    component: CommissionReferenceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.commissionReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommissionReferenceDetailComponent,
    resolve: {
      commissionReference: CommissionReferenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commissionReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommissionReferenceUpdateComponent,
    resolve: {
      commissionReference: CommissionReferenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commissionReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommissionReferenceUpdateComponent,
    resolve: {
      commissionReference: CommissionReferenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commissionReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
