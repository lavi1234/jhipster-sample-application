import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmailTracking, EmailTracking } from 'app/shared/model/email-tracking.model';
import { EmailTrackingService } from './email-tracking.service';
import { EmailTrackingComponent } from './email-tracking.component';
import { EmailTrackingDetailComponent } from './email-tracking-detail.component';
import { EmailTrackingUpdateComponent } from './email-tracking-update.component';

@Injectable({ providedIn: 'root' })
export class EmailTrackingResolve implements Resolve<IEmailTracking> {
  constructor(private service: EmailTrackingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmailTracking> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((emailTracking: HttpResponse<EmailTracking>) => {
          if (emailTracking.body) {
            return of(emailTracking.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmailTracking());
  }
}

export const emailTrackingRoute: Routes = [
  {
    path: '',
    component: EmailTrackingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.emailTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EmailTrackingDetailComponent,
    resolve: {
      emailTracking: EmailTrackingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.emailTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EmailTrackingUpdateComponent,
    resolve: {
      emailTracking: EmailTrackingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.emailTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EmailTrackingUpdateComponent,
    resolve: {
      emailTracking: EmailTrackingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.emailTracking.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
