import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFavourites } from 'app/shared/model/favourites.model';

type EntityResponseType = HttpResponse<IFavourites>;
type EntityArrayResponseType = HttpResponse<IFavourites[]>;

@Injectable({ providedIn: 'root' })
export class FavouritesService {
  public resourceUrl = SERVER_API_URL + 'api/favourites';

  constructor(protected http: HttpClient) {}

  create(favourites: IFavourites): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(favourites);
    return this.http
      .post<IFavourites>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(favourites: IFavourites): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(favourites);
    return this.http
      .put<IFavourites>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFavourites>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFavourites[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(favourites: IFavourites): IFavourites {
    const copy: IFavourites = Object.assign({}, favourites, {
      createdAt: favourites.createdAt && favourites.createdAt.isValid() ? favourites.createdAt.toJSON() : undefined
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
      res.body.forEach((favourites: IFavourites) => {
        favourites.createdAt = favourites.createdAt ? moment(favourites.createdAt) : undefined;
      });
    }
    return res;
  }
}
