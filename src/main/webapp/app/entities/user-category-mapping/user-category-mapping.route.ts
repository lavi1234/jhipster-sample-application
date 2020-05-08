import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserCategoryMapping, UserCategoryMapping } from 'app/shared/model/user-category-mapping.model';
import { UserCategoryMappingService } from './user-category-mapping.service';
import { UserCategoryMappingComponent } from './user-category-mapping.component';
import { UserCategoryMappingDetailComponent } from './user-category-mapping-detail.component';
import { UserCategoryMappingUpdateComponent } from './user-category-mapping-update.component';

@Injectable({ providedIn: 'root' })
export class UserCategoryMappingResolve implements Resolve<IUserCategoryMapping> {
  constructor(private service: UserCategoryMappingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserCategoryMapping> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userCategoryMapping: HttpResponse<UserCategoryMapping>) => {
          if (userCategoryMapping.body) {
            return of(userCategoryMapping.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserCategoryMapping());
  }
}

export const userCategoryMappingRoute: Routes = [
  {
    path: '',
    component: UserCategoryMappingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.userCategoryMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserCategoryMappingDetailComponent,
    resolve: {
      userCategoryMapping: UserCategoryMappingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.userCategoryMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserCategoryMappingUpdateComponent,
    resolve: {
      userCategoryMapping: UserCategoryMappingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.userCategoryMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserCategoryMappingUpdateComponent,
    resolve: {
      userCategoryMapping: UserCategoryMappingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.userCategoryMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
