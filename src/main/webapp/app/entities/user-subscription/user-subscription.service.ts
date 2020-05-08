import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserSubscription } from 'app/shared/model/user-subscription.model';

type EntityResponseType = HttpResponse<IUserSubscription>;
type EntityArrayResponseType = HttpResponse<IUserSubscription[]>;

@Injectable({ providedIn: 'root' })
export class UserSubscriptionService {
  public resourceUrl = SERVER_API_URL + 'api/user-subscriptions';

  constructor(protected http: HttpClient) {}

  create(userSubscription: IUserSubscription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userSubscription);
    return this.http
      .post<IUserSubscription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userSubscription: IUserSubscription): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userSubscription);
    return this.http
      .put<IUserSubscription>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserSubscription>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserSubscription[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userSubscription: IUserSubscription): IUserSubscription {
    const copy: IUserSubscription = Object.assign({}, userSubscription, {
      validUpto:
        userSubscription.validUpto && userSubscription.validUpto.isValid() ? userSubscription.validUpto.format(DATE_FORMAT) : undefined,
      nextRenewal:
        userSubscription.nextRenewal && userSubscription.nextRenewal.isValid()
          ? userSubscription.nextRenewal.format(DATE_FORMAT)
          : undefined,
      createdAt: userSubscription.createdAt && userSubscription.createdAt.isValid() ? userSubscription.createdAt.toJSON() : undefined,
      updatedAt: userSubscription.updatedAt && userSubscription.updatedAt.isValid() ? userSubscription.updatedAt.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.validUpto = res.body.validUpto ? moment(res.body.validUpto) : undefined;
      res.body.nextRenewal = res.body.nextRenewal ? moment(res.body.nextRenewal) : undefined;
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userSubscription: IUserSubscription) => {
        userSubscription.validUpto = userSubscription.validUpto ? moment(userSubscription.validUpto) : undefined;
        userSubscription.nextRenewal = userSubscription.nextRenewal ? moment(userSubscription.nextRenewal) : undefined;
        userSubscription.createdAt = userSubscription.createdAt ? moment(userSubscription.createdAt) : undefined;
        userSubscription.updatedAt = userSubscription.updatedAt ? moment(userSubscription.updatedAt) : undefined;
      });
    }
    return res;
  }
}
