import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmailTracking } from 'app/shared/model/email-tracking.model';

type EntityResponseType = HttpResponse<IEmailTracking>;
type EntityArrayResponseType = HttpResponse<IEmailTracking[]>;

@Injectable({ providedIn: 'root' })
export class EmailTrackingService {
  public resourceUrl = SERVER_API_URL + 'api/email-trackings';

  constructor(protected http: HttpClient) {}

  create(emailTracking: IEmailTracking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emailTracking);
    return this.http
      .post<IEmailTracking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(emailTracking: IEmailTracking): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emailTracking);
    return this.http
      .put<IEmailTracking>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmailTracking>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmailTracking[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(emailTracking: IEmailTracking): IEmailTracking {
    const copy: IEmailTracking = Object.assign({}, emailTracking, {
      createdAt: emailTracking.createdAt && emailTracking.createdAt.isValid() ? emailTracking.createdAt.toJSON() : undefined
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
      res.body.forEach((emailTracking: IEmailTracking) => {
        emailTracking.createdAt = emailTracking.createdAt ? moment(emailTracking.createdAt) : undefined;
      });
    }
    return res;
  }
}
