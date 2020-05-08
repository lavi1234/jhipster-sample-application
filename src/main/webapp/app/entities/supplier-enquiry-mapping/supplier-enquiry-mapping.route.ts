import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISupplierEnquiryMapping, SupplierEnquiryMapping } from 'app/shared/model/supplier-enquiry-mapping.model';
import { SupplierEnquiryMappingService } from './supplier-enquiry-mapping.service';
import { SupplierEnquiryMappingComponent } from './supplier-enquiry-mapping.component';
import { SupplierEnquiryMappingDetailComponent } from './supplier-enquiry-mapping-detail.component';
import { SupplierEnquiryMappingUpdateComponent } from './supplier-enquiry-mapping-update.component';

@Injectable({ providedIn: 'root' })
export class SupplierEnquiryMappingResolve implements Resolve<ISupplierEnquiryMapping> {
  constructor(private service: SupplierEnquiryMappingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISupplierEnquiryMapping> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((supplierEnquiryMapping: HttpResponse<SupplierEnquiryMapping>) => {
          if (supplierEnquiryMapping.body) {
            return of(supplierEnquiryMapping.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SupplierEnquiryMapping());
  }
}

export const supplierEnquiryMappingRoute: Routes = [
  {
    path: '',
    component: SupplierEnquiryMappingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'jhipsterSampleApplicationApp.supplierEnquiryMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SupplierEnquiryMappingDetailComponent,
    resolve: {
      supplierEnquiryMapping: SupplierEnquiryMappingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.supplierEnquiryMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SupplierEnquiryMappingUpdateComponent,
    resolve: {
      supplierEnquiryMapping: SupplierEnquiryMappingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.supplierEnquiryMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SupplierEnquiryMappingUpdateComponent,
    resolve: {
      supplierEnquiryMapping: SupplierEnquiryMappingResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.supplierEnquiryMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
