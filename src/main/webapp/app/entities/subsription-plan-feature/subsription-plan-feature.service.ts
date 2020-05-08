import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISubsriptionPlanFeature } from 'app/shared/model/subsription-plan-feature.model';

type EntityResponseType = HttpResponse<ISubsriptionPlanFeature>;
type EntityArrayResponseType = HttpResponse<ISubsriptionPlanFeature[]>;

@Injectable({ providedIn: 'root' })
export class SubsriptionPlanFeatureService {
  public resourceUrl = SERVER_API_URL + 'api/subsription-plan-features';

  constructor(protected http: HttpClient) {}

  create(subsriptionPlanFeature: ISubsriptionPlanFeature): Observable<EntityResponseType> {
    return this.http.post<ISubsriptionPlanFeature>(this.resourceUrl, subsriptionPlanFeature, { observe: 'response' });
  }

  update(subsriptionPlanFeature: ISubsriptionPlanFeature): Observable<EntityResponseType> {
    return this.http.put<ISubsriptionPlanFeature>(this.resourceUrl, subsriptionPlanFeature, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubsriptionPlanFeature>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubsriptionPlanFeature[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
