import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransactionHistory } from 'app/shared/model/transaction-history.model';

type EntityResponseType = HttpResponse<ITransactionHistory>;
type EntityArrayResponseType = HttpResponse<ITransactionHistory[]>;

@Injectable({ providedIn: 'root' })
export class TransactionHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-histories';

  constructor(protected http: HttpClient) {}

  create(transactionHistory: ITransactionHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionHistory);
    return this.http
      .post<ITransactionHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transactionHistory: ITransactionHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transactionHistory);
    return this.http
      .put<ITransactionHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransactionHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransactionHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(transactionHistory: ITransactionHistory): ITransactionHistory {
    const copy: ITransactionHistory = Object.assign({}, transactionHistory, {
      createdAt: transactionHistory.createdAt && transactionHistory.createdAt.isValid() ? transactionHistory.createdAt.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt ? moment(res.body.createdAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transactionHistory: ITransactionHistory) => {
        transactionHistory.createdAt = transactionHistory.createdAt ? moment(transactionHistory.createdAt) : undefined;
      });
    }
    return res;
  }
}
