import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICommissionReference } from 'app/shared/model/commission-reference.model';

type EntityResponseType = HttpResponse<ICommissionReference>;
type EntityArrayResponseType = HttpResponse<ICommissionReference[]>;

@Injectable({ providedIn: 'root' })
export class CommissionReferenceService {
  public resourceUrl = SERVER_API_URL + 'api/commission-references';

  constructor(protected http: HttpClient) {}

  create(commissionReference: ICommissionReference): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commissionReference);
    return this.http
      .post<ICommissionReference>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commissionReference: ICommissionReference): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commissionReference);
    return this.http
      .put<ICommissionReference>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICommissionReference>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommissionReference[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(commissionReference: ICommissionReference): ICommissionReference {
    const copy: ICommissionReference = Object.assign({}, commissionReference, {
      createdAt:
        commissionReference.createdAt && commissionReference.createdAt.isValid() ? commissionReference.createdAt.toJSON() : undefined,
      updatedAt:
        commissionReference.updatedAt && commissionReference.updatedAt.isValid() ? commissionReference.updatedAt.toJSON() : undefined
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
      res.body.forEach((commissionReference: ICommissionReference) => {
        commissionReference.createdAt = commissionReference.createdAt ? moment(commissionReference.createdAt) : undefined;
        commissionReference.updatedAt = commissionReference.updatedAt ? moment(commissionReference.updatedAt) : undefined;
      });
    }
    return res;
  }
}
