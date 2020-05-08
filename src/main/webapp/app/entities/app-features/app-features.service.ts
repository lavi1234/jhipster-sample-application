import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAppFeatures } from 'app/shared/model/app-features.model';

type EntityResponseType = HttpResponse<IAppFeatures>;
type EntityArrayResponseType = HttpResponse<IAppFeatures[]>;

@Injectable({ providedIn: 'root' })
export class AppFeaturesService {
  public resourceUrl = SERVER_API_URL + 'api/app-features';

  constructor(protected http: HttpClient) {}

  create(appFeatures: IAppFeatures): Observable<EntityResponseType> {
    return this.http.post<IAppFeatures>(this.resourceUrl, appFeatures, { observe: 'response' });
  }

  update(appFeatures: IAppFeatures): Observable<EntityResponseType> {
    return this.http.put<IAppFeatures>(this.resourceUrl, appFeatures, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAppFeatures>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAppFeatures[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
