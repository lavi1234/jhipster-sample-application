import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILocalization, Localization } from 'app/shared/model/localization.model';
import { LocalizationService } from './localization.service';
import { LocalizationComponent } from './localization.component';
import { LocalizationDetailComponent } from './localization-detail.component';
import { LocalizationUpdateComponent } from './localization-update.component';

@Injectable({ providedIn: 'root' })
export class LocalizationResolve implements Resolve<ILocalization> {
  constructor(private service: LocalizationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILocalization> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((localization: HttpResponse<Localization>) => {
          if (localization.body) {
            return of(localization.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Localization());
  }
}

export const localizationRoute: Routes = [
  {
    path: '',
    component: LocalizationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.localization.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LocalizationDetailComponent,
    resolve: {
      localization: LocalizationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.localization.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LocalizationUpdateComponent,
    resolve: {
      localization: LocalizationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.localization.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LocalizationUpdateComponent,
    resolve: {
      localization: LocalizationResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.localization.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
