import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILocalization } from 'app/shared/model/localization.model';

type EntityResponseType = HttpResponse<ILocalization>;
type EntityArrayResponseType = HttpResponse<ILocalization[]>;

@Injectable({ providedIn: 'root' })
export class LocalizationService {
  public resourceUrl = SERVER_API_URL + 'api/localizations';

  constructor(protected http: HttpClient) {}

  create(localization: ILocalization): Observable<EntityResponseType> {
    return this.http.post<ILocalization>(this.resourceUrl, localization, { observe: 'response' });
  }

  update(localization: ILocalization): Observable<EntityResponseType> {
    return this.http.put<ILocalization>(this.resourceUrl, localization, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILocalization>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocalization[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
