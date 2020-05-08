import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOfferPrice, OfferPrice } from 'app/shared/model/offer-price.model';
import { OfferPriceService } from './offer-price.service';
import { OfferPriceComponent } from './offer-price.component';
import { OfferPriceDetailComponent } from './offer-price-detail.component';
import { OfferPriceUpdateComponent } from './offer-price-update.component';

@Injectable({ providedIn: 'root' })
export class OfferPriceResolve implements Resolve<IOfferPrice> {
  constructor(private service: OfferPriceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOfferPrice> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((offerPrice: HttpResponse<OfferPrice>) => {
          if (offerPrice.body) {
            return of(offerPrice.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OfferPrice());
  }
}

export const offerPriceRoute: Routes = [
  {
    path: '',
    component: OfferPriceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.offerPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OfferPriceDetailComponent,
    resolve: {
      offerPrice: OfferPriceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.offerPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OfferPriceUpdateComponent,
    resolve: {
      offerPrice: OfferPriceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.offerPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OfferPriceUpdateComponent,
    resolve: {
      offerPrice: OfferPriceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.offerPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
