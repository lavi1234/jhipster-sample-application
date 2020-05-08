import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEnquiryMaterial } from 'app/shared/model/enquiry-material.model';

type EntityResponseType = HttpResponse<IEnquiryMaterial>;
type EntityArrayResponseType = HttpResponse<IEnquiryMaterial[]>;

@Injectable({ providedIn: 'root' })
export class EnquiryMaterialService {
  public resourceUrl = SERVER_API_URL + 'api/enquiry-materials';

  constructor(protected http: HttpClient) {}

  create(enquiryMaterial: IEnquiryMaterial): Observable<EntityResponseType> {
    return this.http.post<IEnquiryMaterial>(this.resourceUrl, enquiryMaterial, { observe: 'response' });
  }

  update(enquiryMaterial: IEnquiryMaterial): Observable<EntityResponseType> {
    return this.http.put<IEnquiryMaterial>(this.resourceUrl, enquiryMaterial, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEnquiryMaterial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEnquiryMaterial[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
