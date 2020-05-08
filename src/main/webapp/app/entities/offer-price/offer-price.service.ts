import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOfferPrice } from 'app/shared/model/offer-price.model';

type EntityResponseType = HttpResponse<IOfferPrice>;
type EntityArrayResponseType = HttpResponse<IOfferPrice[]>;

@Injectable({ providedIn: 'root' })
export class OfferPriceService {
  public resourceUrl = SERVER_API_URL + 'api/offer-prices';

  constructor(protected http: HttpClient) {}

  create(offerPrice: IOfferPrice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(offerPrice);
    return this.http
      .post<IOfferPrice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(offerPrice: IOfferPrice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(offerPrice);
    return this.http
      .put<IOfferPrice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOfferPrice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOfferPrice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(offerPrice: IOfferPrice): IOfferPrice {
    const copy: IOfferPrice = Object.assign({}, offerPrice, {
      createdAt: offerPrice.createdAt && offerPrice.createdAt.isValid() ? offerPrice.createdAt.toJSON() : undefined,
      finishingDate:
        offerPrice.finishingDate && offerPrice.finishingDate.isValid() ? offerPrice.finishingDate.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.finishingDate = res.body.finishingDate ? moment(res.body.finishingDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((offerPrice: IOfferPrice) => {
        offerPrice.createdAt = offerPrice.createdAt ? moment(offerPrice.createdAt) : undefined;
        offerPrice.finishingDate = offerPrice.finishingDate ? moment(offerPrice.finishingDate) : undefined;
      });
    }
    return res;
  }
}
