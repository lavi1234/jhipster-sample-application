import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserCategoryMapping } from 'app/shared/model/user-category-mapping.model';

type EntityResponseType = HttpResponse<IUserCategoryMapping>;
type EntityArrayResponseType = HttpResponse<IUserCategoryMapping[]>;

@Injectable({ providedIn: 'root' })
export class UserCategoryMappingService {
  public resourceUrl = SERVER_API_URL + 'api/user-category-mappings';

  constructor(protected http: HttpClient) {}

  create(userCategoryMapping: IUserCategoryMapping): Observable<EntityResponseType> {
    return this.http.post<IUserCategoryMapping>(this.resourceUrl, userCategoryMapping, { observe: 'response' });
  }

  update(userCategoryMapping: IUserCategoryMapping): Observable<EntityResponseType> {
    return this.http.put<IUserCategoryMapping>(this.resourceUrl, userCategoryMapping, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserCategoryMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserCategoryMapping[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
