import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INotificationReceiver } from 'app/shared/model/notification-receiver.model';

type EntityResponseType = HttpResponse<INotificationReceiver>;
type EntityArrayResponseType = HttpResponse<INotificationReceiver[]>;

@Injectable({ providedIn: 'root' })
export class NotificationReceiverService {
  public resourceUrl = SERVER_API_URL + 'api/notification-receivers';

  constructor(protected http: HttpClient) {}

  create(notificationReceiver: INotificationReceiver): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notificationReceiver);
    return this.http
      .post<INotificationReceiver>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(notificationReceiver: INotificationReceiver): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notificationReceiver);
    return this.http
      .put<INotificationReceiver>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INotificationReceiver>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INotificationReceiver[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(notificationReceiver: INotificationReceiver): INotificationReceiver {
    const copy: INotificationReceiver = Object.assign({}, notificationReceiver, {
      updatedAt:
        notificationReceiver.updatedAt && notificationReceiver.updatedAt.isValid() ? notificationReceiver.updatedAt.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.updatedAt = res.body.updatedAt ? moment(res.body.updatedAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((notificationReceiver: INotificationReceiver) => {
        notificationReceiver.updatedAt = notificationReceiver.updatedAt ? moment(notificationReceiver.updatedAt) : undefined;
      });
    }
    return res;
  }
}
