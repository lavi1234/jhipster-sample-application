import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBonusReference } from 'app/shared/model/bonus-reference.model';

type EntityResponseType = HttpResponse<IBonusReference>;
type EntityArrayResponseType = HttpResponse<IBonusReference[]>;

@Injectable({ providedIn: 'root' })
export class BonusReferenceService {
  public resourceUrl = SERVER_API_URL + 'api/bonus-references';

  constructor(protected http: HttpClient) {}

  create(bonusReference: IBonusReference): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bonusReference);
    return this.http
      .post<IBonusReference>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bonusReference: IBonusReference): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bonusReference);
    return this.http
      .put<IBonusReference>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBonusReference>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBonusReference[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bonusReference: IBonusReference): IBonusReference {
    const copy: IBonusReference = Object.assign({}, bonusReference, {
      createdAt: bonusReference.createdAt && bonusReference.createdAt.isValid() ? bonusReference.createdAt.toJSON() : undefined,
      updatedAt: bonusReference.updatedAt && bonusReference.updatedAt.isValid() ? bonusReference.updatedAt.toJSON() : undefined
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
      res.body.forEach((bonusReference: IBonusReference) => {
        bonusReference.createdAt = bonusReference.createdAt ? moment(bonusReference.createdAt) : undefined;
        bonusReference.updatedAt = bonusReference.updatedAt ? moment(bonusReference.updatedAt) : undefined;
      });
    }
    return res;
  }
}
