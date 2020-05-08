import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISupplierEnquiryMapping } from 'app/shared/model/supplier-enquiry-mapping.model';

type EntityResponseType = HttpResponse<ISupplierEnquiryMapping>;
type EntityArrayResponseType = HttpResponse<ISupplierEnquiryMapping[]>;

@Injectable({ providedIn: 'root' })
export class SupplierEnquiryMappingService {
  public resourceUrl = SERVER_API_URL + 'api/supplier-enquiry-mappings';

  constructor(protected http: HttpClient) {}

  create(supplierEnquiryMapping: ISupplierEnquiryMapping): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(supplierEnquiryMapping);
    return this.http
      .post<ISupplierEnquiryMapping>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(supplierEnquiryMapping: ISupplierEnquiryMapping): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(supplierEnquiryMapping);
    return this.http
      .put<ISupplierEnquiryMapping>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISupplierEnquiryMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISupplierEnquiryMapping[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(supplierEnquiryMapping: ISupplierEnquiryMapping): ISupplierEnquiryMapping {
    const copy: ISupplierEnquiryMapping = Object.assign({}, supplierEnquiryMapping, {
      createdAt:
        supplierEnquiryMapping.createdAt && supplierEnquiryMapping.createdAt.isValid()
          ? supplierEnquiryMapping.createdAt.toJSON()
          : undefined,
      updatedAt:
        supplierEnquiryMapping.updatedAt && supplierEnquiryMapping.updatedAt.isValid()
          ? supplierEnquiryMapping.updatedAt.toJSON()
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((supplierEnquiryMapping: ISupplierEnquiryMapping) => {
        supplierEnquiryMapping.createdAt = supplierEnquiryMapping.createdAt ? moment(supplierEnquiryMapping.createdAt) : undefined;
        supplierEnquiryMapping.updatedAt = supplierEnquiryMapping.updatedAt ? moment(supplierEnquiryMapping.updatedAt) : undefined;
      });
    }
    return res;
  }
}
