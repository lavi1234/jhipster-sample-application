import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMessageDeleteDetails } from 'app/shared/model/message-delete-details.model';

type EntityResponseType = HttpResponse<IMessageDeleteDetails>;
type EntityArrayResponseType = HttpResponse<IMessageDeleteDetails[]>;

@Injectable({ providedIn: 'root' })
export class MessageDeleteDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/message-delete-details';

  constructor(protected http: HttpClient) {}

  create(messageDeleteDetails: IMessageDeleteDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(messageDeleteDetails);
    return this.http
      .post<IMessageDeleteDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(messageDeleteDetails: IMessageDeleteDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(messageDeleteDetails);
    return this.http
      .put<IMessageDeleteDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMessageDeleteDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMessageDeleteDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(messageDeleteDetails: IMessageDeleteDetails): IMessageDeleteDetails {
    const copy: IMessageDeleteDetails = Object.assign({}, messageDeleteDetails, {
      deletedAt:
        messageDeleteDetails.deletedAt && messageDeleteDetails.deletedAt.isValid() ? messageDeleteDetails.deletedAt.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.deletedAt = res.body.deletedAt ? moment(res.body.deletedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((messageDeleteDetails: IMessageDeleteDetails) => {
        messageDeleteDetails.deletedAt = messageDeleteDetails.deletedAt ? moment(messageDeleteDetails.deletedAt) : undefined;
      });
    }
    return res;
  }
}
