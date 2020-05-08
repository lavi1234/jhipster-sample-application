import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITradingHours } from 'app/shared/model/trading-hours.model';

type EntityResponseType = HttpResponse<ITradingHours>;
type EntityArrayResponseType = HttpResponse<ITradingHours[]>;

@Injectable({ providedIn: 'root' })
export class TradingHoursService {
  public resourceUrl = SERVER_API_URL + 'api/trading-hours';

  constructor(protected http: HttpClient) {}

  create(tradingHours: ITradingHours): Observable<EntityResponseType> {
    return this.http.post<ITradingHours>(this.resourceUrl, tradingHours, { observe: 'response' });
  }

  update(tradingHours: ITradingHours): Observable<EntityResponseType> {
    return this.http.put<ITradingHours>(this.resourceUrl, tradingHours, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITradingHours>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITradingHours[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
