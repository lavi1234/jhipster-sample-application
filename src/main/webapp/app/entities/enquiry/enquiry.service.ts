import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEnquiry } from 'app/shared/model/enquiry.model';

type EntityResponseType = HttpResponse<IEnquiry>;
type EntityArrayResponseType = HttpResponse<IEnquiry[]>;

@Injectable({ providedIn: 'root' })
export class EnquiryService {
  public resourceUrl = SERVER_API_URL + 'api/enquiries';

  constructor(protected http: HttpClient) {}

  create(enquiry: IEnquiry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(enquiry);
    return this.http
      .post<IEnquiry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(enquiry: IEnquiry): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(enquiry);
    return this.http
      .put<IEnquiry>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEnquiry>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEnquiry[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(enquiry: IEnquiry): IEnquiry {
    const copy: IEnquiry = Object.assign({}, enquiry, {
      offerTaxtUntil: enquiry.offerTaxtUntil && enquiry.offerTaxtUntil.isValid() ? enquiry.offerTaxtUntil.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.offerTaxtUntil = res.body.offerTaxtUntil ? moment(res.body.offerTaxtUntil) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((enquiry: IEnquiry) => {
        enquiry.offerTaxtUntil = enquiry.offerTaxtUntil ? moment(enquiry.offerTaxtUntil) : undefined;
      });
    }
    return res;
  }
}
