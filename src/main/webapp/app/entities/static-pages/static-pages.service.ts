import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStaticPages } from 'app/shared/model/static-pages.model';

type EntityResponseType = HttpResponse<IStaticPages>;
type EntityArrayResponseType = HttpResponse<IStaticPages[]>;

@Injectable({ providedIn: 'root' })
export class StaticPagesService {
  public resourceUrl = SERVER_API_URL + 'api/static-pages';

  constructor(protected http: HttpClient) {}

  create(staticPages: IStaticPages): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(staticPages);
    return this.http
      .post<IStaticPages>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(staticPages: IStaticPages): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(staticPages);
    return this.http
      .put<IStaticPages>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStaticPages>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStaticPages[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(staticPages: IStaticPages): IStaticPages {
    const copy: IStaticPages = Object.assign({}, staticPages, {
      createdAt: staticPages.createdAt && staticPages.createdAt.isValid() ? staticPages.createdAt.toJSON() : undefined,
      updatedAt: staticPages.updatedAt && staticPages.updatedAt.isValid() ? staticPages.updatedAt.toJSON() : undefined
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
      res.body.forEach((staticPages: IStaticPages) => {
        staticPages.createdAt = staticPages.createdAt ? moment(staticPages.createdAt) : undefined;
        staticPages.updatedAt = staticPages.updatedAt ? moment(staticPages.updatedAt) : undefined;
      });
    }
    return res;
  }
}
