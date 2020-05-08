import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFavourites, Favourites } from 'app/shared/model/favourites.model';
import { FavouritesService } from './favourites.service';
import { FavouritesComponent } from './favourites.component';
import { FavouritesDetailComponent } from './favourites-detail.component';
import { FavouritesUpdateComponent } from './favourites-update.component';

@Injectable({ providedIn: 'root' })
export class FavouritesResolve implements Resolve<IFavourites> {
  constructor(private service: FavouritesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFavourites> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((favourites: HttpResponse<Favourites>) => {
          if (favourites.body) {
            return of(favourites.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Favourites());
  }
}

export const favouritesRoute: Routes = [
  {
    path: '',
    component: FavouritesComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.favourites.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FavouritesDetailComponent,
    resolve: {
      favourites: FavouritesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.favourites.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FavouritesUpdateComponent,
    resolve: {
      favourites: FavouritesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.favourites.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FavouritesUpdateComponent,
    resolve: {
      favourites: FavouritesResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.favourites.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
