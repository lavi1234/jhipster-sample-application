import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBonusReference, BonusReference } from 'app/shared/model/bonus-reference.model';
import { BonusReferenceService } from './bonus-reference.service';
import { BonusReferenceComponent } from './bonus-reference.component';
import { BonusReferenceDetailComponent } from './bonus-reference-detail.component';
import { BonusReferenceUpdateComponent } from './bonus-reference-update.component';

@Injectable({ providedIn: 'root' })
export class BonusReferenceResolve implements Resolve<IBonusReference> {
  constructor(private service: BonusReferenceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBonusReference> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bonusReference: HttpResponse<BonusReference>) => {
          if (bonusReference.body) {
            return of(bonusReference.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BonusReference());
  }
}

export const bonusReferenceRoute: Routes = [
  {
    path: '',
    component: BonusReferenceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.bonusReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BonusReferenceDetailComponent,
    resolve: {
      bonusReference: BonusReferenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.bonusReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BonusReferenceUpdateComponent,
    resolve: {
      bonusReference: BonusReferenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.bonusReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BonusReferenceUpdateComponent,
    resolve: {
      bonusReference: BonusReferenceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.bonusReference.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
