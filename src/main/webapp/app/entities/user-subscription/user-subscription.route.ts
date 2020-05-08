import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserSubscription, UserSubscription } from 'app/shared/model/user-subscription.model';
import { UserSubscriptionService } from './user-subscription.service';
import { UserSubscriptionComponent } from './user-subscription.component';
import { UserSubscriptionDetailComponent } from './user-subscription-detail.component';
import { UserSubscriptionUpdateComponent } from './user-subscription-update.component';

@Injectable({ providedIn: 'root' })
export class UserSubscriptionResolve implements Resolve<IUserSubscription> {
  constructor(private service: UserSubscriptionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserSubscription> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userSubscription: HttpResponse<UserSubscription>) => {
          if (userSubscription.body) {
            return of(userSubscription.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserSubscription());
  }
}

export const userSubscriptionRoute: Routes = [
  {
    path: '',
    component: UserSubscriptionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.userSubscription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserSubscriptionDetailComponent,
    resolve: {
      userSubscription: UserSubscriptionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.userSubscription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserSubscriptionUpdateComponent,
    resolve: {
      userSubscription: UserSubscriptionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.userSubscription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserSubscriptionUpdateComponent,
    resolve: {
      userSubscription: UserSubscriptionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.userSubscription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
