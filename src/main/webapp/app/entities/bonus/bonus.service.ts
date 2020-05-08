import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBonus } from 'app/shared/model/bonus.model';

type EntityResponseType = HttpResponse<IBonus>;
type EntityArrayResponseType = HttpResponse<IBonus[]>;

@Injectable({ providedIn: 'root' })
export class BonusService {
  public resourceUrl = SERVER_API_URL + 'api/bonuses';

  constructor(protected http: HttpClient) {}

  create(bonus: IBonus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bonus);
    return this.http
      .post<IBonus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bonus: IBonus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bonus);
    return this.http
      .put<IBonus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBonus>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBonus[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bonus: IBonus): IBonus {
    const copy: IBonus = Object.assign({}, bonus, {
      createdAt: bonus.createdAt && bonus.createdAt.isValid() ? bonus.createdAt.toJSON() : undefined,
      updatedAt: bonus.updatedAt && bonus.updatedAt.isValid() ? bonus.updatedAt.toJSON() : undefined
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
      res.body.forEach((bonus: IBonus) => {
        bonus.createdAt = bonus.createdAt ? moment(bonus.createdAt) : undefined;
        bonus.updatedAt = bonus.updatedAt ? moment(bonus.updatedAt) : undefined;
      });
    }
    return res;
  }
}
