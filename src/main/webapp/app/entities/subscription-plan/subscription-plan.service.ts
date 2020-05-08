import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISubscriptionPlan } from 'app/shared/model/subscription-plan.model';

type EntityResponseType = HttpResponse<ISubscriptionPlan>;
type EntityArrayResponseType = HttpResponse<ISubscriptionPlan[]>;

@Injectable({ providedIn: 'root' })
export class SubscriptionPlanService {
  public resourceUrl = SERVER_API_URL + 'api/subscription-plans';

  constructor(protected http: HttpClient) {}

  create(subscriptionPlan: ISubscriptionPlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscriptionPlan);
    return this.http
      .post<ISubscriptionPlan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(subscriptionPlan: ISubscriptionPlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(subscriptionPlan);
    return this.http
      .put<ISubscriptionPlan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISubscriptionPlan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISubscriptionPlan[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(subscriptionPlan: ISubscriptionPlan): ISubscriptionPlan {
    const copy: ISubscriptionPlan = Object.assign({}, subscriptionPlan, {
      createdAt: subscriptionPlan.createdAt && subscriptionPlan.createdAt.isValid() ? subscriptionPlan.createdAt.toJSON() : undefined,
      updatedAt: subscriptionPlan.updatedAt && subscriptionPlan.updatedAt.isValid() ? subscriptionPlan.updatedAt.toJSON() : undefined
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
      res.body.forEach((subscriptionPlan: ISubscriptionPlan) => {
        subscriptionPlan.createdAt = subscriptionPlan.createdAt ? moment(subscriptionPlan.createdAt) : undefined;
        subscriptionPlan.updatedAt = subscriptionPlan.updatedAt ? moment(subscriptionPlan.updatedAt) : undefined;
      });
    }
    return res;
  }
}
