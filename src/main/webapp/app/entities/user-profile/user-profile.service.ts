import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserProfile } from 'app/shared/model/user-profile.model';

type EntityResponseType = HttpResponse<IUserProfile>;
type EntityArrayResponseType = HttpResponse<IUserProfile[]>;

@Injectable({ providedIn: 'root' })
export class UserProfileService {
  public resourceUrl = SERVER_API_URL + 'api/user-profiles';

  constructor(protected http: HttpClient) {}

  create(userProfile: IUserProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userProfile);
    return this.http
      .post<IUserProfile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userProfile: IUserProfile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userProfile);
    return this.http
      .put<IUserProfile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserProfile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserProfile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userProfile: IUserProfile): IUserProfile {
    const copy: IUserProfile = Object.assign({}, userProfile, {
      createdAt: userProfile.createdAt && userProfile.createdAt.isValid() ? userProfile.createdAt.toJSON() : undefined,
      updatedAt: userProfile.updatedAt && userProfile.updatedAt.isValid() ? userProfile.updatedAt.toJSON() : undefined
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
      res.body.forEach((userProfile: IUserProfile) => {
        userProfile.createdAt = userProfile.createdAt ? moment(userProfile.createdAt) : undefined;
        userProfile.updatedAt = userProfile.updatedAt ? moment(userProfile.updatedAt) : undefined;
      });
    }
    return res;
  }
}
