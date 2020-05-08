import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommission } from 'app/shared/model/commission.model';

type EntityResponseType = HttpResponse<ICommission>;
type EntityArrayResponseType = HttpResponse<ICommission[]>;

@Injectable({ providedIn: 'root' })
export class CommissionService {
  public resourceUrl = SERVER_API_URL + 'api/commissions';

  constructor(protected http: HttpClient) {}

  create(commission: ICommission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commission);
    return this.http
      .post<ICommission>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commission: ICommission): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commission);
    return this.http
      .put<ICommission>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommission>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommission[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(commission: ICommission): ICommission {
    const copy: ICommission = Object.assign({}, commission, {
      createdAt: commission.createdAt && commission.createdAt.isValid() ? commission.createdAt.toJSON() : undefined,
      updatedAt: commission.updatedAt && commission.updatedAt.isValid() ? commission.updatedAt.toJSON() : undefined
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
      res.body.forEach((commission: ICommission) => {
        commission.createdAt = commission.createdAt ? moment(commission.createdAt) : undefined;
        commission.updatedAt = commission.updatedAt ? moment(commission.updatedAt) : undefined;
      });
    }
    return res;
  }
}
