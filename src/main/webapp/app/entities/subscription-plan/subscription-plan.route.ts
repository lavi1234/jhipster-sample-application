import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISubscriptionPlan, SubscriptionPlan } from 'app/shared/model/subscription-plan.model';
import { SubscriptionPlanService } from './subscription-plan.service';
import { SubscriptionPlanComponent } from './subscription-plan.component';
import { SubscriptionPlanDetailComponent } from './subscription-plan-detail.component';
import { SubscriptionPlanUpdateComponent } from './subscription-plan-update.component';

@Injectable({ providedIn: 'root' })
export class SubscriptionPlanResolve implements Resolve<ISubscriptionPlan> {
  constructor(private service: SubscriptionPlanService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubscriptionPlan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((subscriptionPlan: HttpResponse<SubscriptionPlan>) => {
          if (subscriptionPlan.body) {
            return of(subscriptionPlan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubscriptionPlan());
  }
}

export const subscriptionPlanRoute: Routes = [
  {
    path: '',
    component: SubscriptionPlanComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.subscriptionPlan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubscriptionPlanDetailComponent,
    resolve: {
      subscriptionPlan: SubscriptionPlanResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.subscriptionPlan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubscriptionPlanUpdateComponent,
    resolve: {
      subscriptionPlan: SubscriptionPlanResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.subscriptionPlan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubscriptionPlanUpdateComponent,
    resolve: {
      subscriptionPlan: SubscriptionPlanResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.subscriptionPlan.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
