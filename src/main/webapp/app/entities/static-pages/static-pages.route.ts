import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IStaticPages, StaticPages } from 'app/shared/model/static-pages.model';
import { StaticPagesService } from './static-pages.service';
import { StaticPagesComponent } from './static-pages.component';
import { StaticPagesDetailComponent } from './static-pages-detail.component';
import { StaticPagesUpdateComponent } from './static-pages-update.component';

@Injectable({ providedIn: 'root' })
export class StaticPagesResolve implements Resolve<IStaticPages> {
  constructor(private service: StaticPagesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStaticPages> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((staticPages: HttpResponse<StaticPages>) => {
          if (staticPages.body) {
            return of(staticPages.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new StaticPages());
  }
}

export const staticPagesRoute: Routes = [
  {
    path: '',
    component: StaticPagesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.staticPages.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: StaticPagesDetailComponent,
    resolve: {
      staticPages: StaticPagesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.staticPages.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: StaticPagesUpdateComponent,
    resolve: {
      staticPages: StaticPagesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.staticPages.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: StaticPagesUpdateComponent,
    resolve: {
      staticPages: StaticPagesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.staticPages.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
