import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISubsriptionPlanFeature, SubsriptionPlanFeature } from 'app/shared/model/subsription-plan-feature.model';
import { SubsriptionPlanFeatureService } from './subsription-plan-feature.service';
import { SubsriptionPlanFeatureComponent } from './subsription-plan-feature.component';
import { SubsriptionPlanFeatureDetailComponent } from './subsription-plan-feature-detail.component';
import { SubsriptionPlanFeatureUpdateComponent } from './subsription-plan-feature-update.component';

@Injectable({ providedIn: 'root' })
export class SubsriptionPlanFeatureResolve implements Resolve<ISubsriptionPlanFeature> {
  constructor(private service: SubsriptionPlanFeatureService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubsriptionPlanFeature> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((subsriptionPlanFeature: HttpResponse<SubsriptionPlanFeature>) => {
          if (subsriptionPlanFeature.body) {
            return of(subsriptionPlanFeature.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubsriptionPlanFeature());
  }
}

export const subsriptionPlanFeatureRoute: Routes = [
  {
    path: '',
    component: SubsriptionPlanFeatureComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.subsriptionPlanFeature.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubsriptionPlanFeatureDetailComponent,
    resolve: {
      subsriptionPlanFeature: SubsriptionPlanFeatureResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.subsriptionPlanFeature.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubsriptionPlanFeatureUpdateComponent,
    resolve: {
      subsriptionPlanFeature: SubsriptionPlanFeatureResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.subsriptionPlanFeature.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubsriptionPlanFeatureUpdateComponent,
    resolve: {
      subsriptionPlanFeature: SubsriptionPlanFeatureResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.subsriptionPlanFeature.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
