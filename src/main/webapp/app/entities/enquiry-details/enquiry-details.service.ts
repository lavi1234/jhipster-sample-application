import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEnquiryDetails } from 'app/shared/model/enquiry-details.model';

type EntityResponseType = HttpResponse<IEnquiryDetails>;
type EntityArrayResponseType = HttpResponse<IEnquiryDetails[]>;

@Injectable({ providedIn: 'root' })
export class EnquiryDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/enquiry-details';

  constructor(protected http: HttpClient) {}

  create(enquiryDetails: IEnquiryDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(enquiryDetails);
    return this.http
      .post<IEnquiryDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(enquiryDetails: IEnquiryDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(enquiryDetails);
    return this.http
      .put<IEnquiryDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEnquiryDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEnquiryDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(enquiryDetails: IEnquiryDetails): IEnquiryDetails {
    const copy: IEnquiryDetails = Object.assign({}, enquiryDetails, {
      deliveryDate:
        enquiryDetails.deliveryDate && enquiryDetails.deliveryDate.isValid() ? enquiryDetails.deliveryDate.format(DATE_FORMAT) : undefined,
      createdAt: enquiryDetails.createdAt && enquiryDetails.createdAt.isValid() ? enquiryDetails.createdAt.toJSON() : undefined,
      updatedAt: enquiryDetails.updatedAt && enquiryDetails.updatedAt.isValid() ? enquiryDetails.updatedAt.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.deliveryDate = res.body.deliveryDate ? moment(res.body.deliveryDate) : undefined;
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((enquiryDetails: IEnquiryDetails) => {
        enquiryDetails.deliveryDate = enquiryDetails.deliveryDate ? moment(enquiryDetails.deliveryDate) : undefined;
        enquiryDetails.createdAt = enquiryDetails.createdAt ? moment(enquiryDetails.createdAt) : undefined;
        enquiryDetails.updatedAt = enquiryDetails.updatedAt ? moment(enquiryDetails.updatedAt) : undefined;
      });
    }
    return res;
  }
}
