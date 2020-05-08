import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEnquiryMaterial, EnquiryMaterial } from 'app/shared/model/enquiry-material.model';
import { EnquiryMaterialService } from './enquiry-material.service';
import { EnquiryMaterialComponent } from './enquiry-material.component';
import { EnquiryMaterialDetailComponent } from './enquiry-material-detail.component';
import { EnquiryMaterialUpdateComponent } from './enquiry-material-update.component';

@Injectable({ providedIn: 'root' })
export class EnquiryMaterialResolve implements Resolve<IEnquiryMaterial> {
  constructor(private service: EnquiryMaterialService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEnquiryMaterial> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((enquiryMaterial: HttpResponse<EnquiryMaterial>) => {
          if (enquiryMaterial.body) {
            return of(enquiryMaterial.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EnquiryMaterial());
  }
}

export const enquiryMaterialRoute: Routes = [
  {
    path: '',
    component: EnquiryMaterialComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.enquiryMaterial.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EnquiryMaterialDetailComponent,
    resolve: {
      enquiryMaterial: EnquiryMaterialResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.enquiryMaterial.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EnquiryMaterialUpdateComponent,
    resolve: {
      enquiryMaterial: EnquiryMaterialResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.enquiryMaterial.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EnquiryMaterialUpdateComponent,
    resolve: {
      enquiryMaterial: EnquiryMaterialResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.enquiryMaterial.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
