import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMessageDeleteDetails, MessageDeleteDetails } from 'app/shared/model/message-delete-details.model';
import { MessageDeleteDetailsService } from './message-delete-details.service';
import { MessageDeleteDetailsComponent } from './message-delete-details.component';
import { MessageDeleteDetailsDetailComponent } from './message-delete-details-detail.component';
import { MessageDeleteDetailsUpdateComponent } from './message-delete-details-update.component';

@Injectable({ providedIn: 'root' })
export class MessageDeleteDetailsResolve implements Resolve<IMessageDeleteDetails> {
  constructor(private service: MessageDeleteDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMessageDeleteDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((messageDeleteDetails: HttpResponse<MessageDeleteDetails>) => {
          if (messageDeleteDetails.body) {
            return of(messageDeleteDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MessageDeleteDetails());
  }
}

export const messageDeleteDetailsRoute: Routes = [
  {
    path: '',
    component: MessageDeleteDetailsComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.messageDeleteDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MessageDeleteDetailsDetailComponent,
    resolve: {
      messageDeleteDetails: MessageDeleteDetailsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.messageDeleteDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MessageDeleteDetailsUpdateComponent,
    resolve: {
      messageDeleteDetails: MessageDeleteDetailsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.messageDeleteDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MessageDeleteDetailsUpdateComponent,
    resolve: {
      messageDeleteDetails: MessageDeleteDetailsResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.messageDeleteDetails.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
