import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INotificationReceiver, NotificationReceiver } from 'app/shared/model/notification-receiver.model';
import { NotificationReceiverService } from './notification-receiver.service';
import { NotificationReceiverComponent } from './notification-receiver.component';
import { NotificationReceiverDetailComponent } from './notification-receiver-detail.component';
import { NotificationReceiverUpdateComponent } from './notification-receiver-update.component';

@Injectable({ providedIn: 'root' })
export class NotificationReceiverResolve implements Resolve<INotificationReceiver> {
  constructor(private service: NotificationReceiverService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INotificationReceiver> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((notificationReceiver: HttpResponse<NotificationReceiver>) => {
          if (notificationReceiver.body) {
            return of(notificationReceiver.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NotificationReceiver());
  }
}

export const notificationReceiverRoute: Routes = [
  {
    path: '',
    component: NotificationReceiverComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.notificationReceiver.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NotificationReceiverDetailComponent,
    resolve: {
      notificationReceiver: NotificationReceiverResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.notificationReceiver.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NotificationReceiverUpdateComponent,
    resolve: {
      notificationReceiver: NotificationReceiverResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.notificationReceiver.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NotificationReceiverUpdateComponent,
    resolve: {
      notificationReceiver: NotificationReceiverResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.notificationReceiver.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
