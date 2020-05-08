import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPaymentDetails } from 'app/shared/model/payment-details.model';

type EntityResponseType = HttpResponse<IPaymentDetails>;
type EntityArrayResponseType = HttpResponse<IPaymentDetails[]>;

@Injectable({ providedIn: 'root' })
export class PaymentDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/payment-details';

  constructor(protected http: HttpClient) {}

  create(paymentDetails: IPaymentDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentDetails);
    return this.http
      .post<IPaymentDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(paymentDetails: IPaymentDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(paymentDetails);
    return this.http
      .put<IPaymentDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPaymentDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPaymentDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(paymentDetails: IPaymentDetails): IPaymentDetails {
    const copy: IPaymentDetails = Object.assign({}, paymentDetails, {
      createdAt: paymentDetails.createdAt && paymentDetails.createdAt.isValid() ? paymentDetails.createdAt.toJSON() : undefined,
      updatedAt: paymentDetails.updatedAt && paymentDetails.updatedAt.isValid() ? paymentDetails.updatedAt.toJSON() : undefined
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
      res.body.forEach((paymentDetails: IPaymentDetails) => {
        paymentDetails.createdAt = paymentDetails.createdAt ? moment(paymentDetails.createdAt) : undefined;
        paymentDetails.updatedAt = paymentDetails.updatedAt ? moment(paymentDetails.updatedAt) : undefined;
      });
    }
    return res;
  }
}
