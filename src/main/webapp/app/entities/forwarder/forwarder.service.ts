import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IForwarder } from 'app/shared/model/forwarder.model';

type EntityResponseType = HttpResponse<IForwarder>;
type EntityArrayResponseType = HttpResponse<IForwarder[]>;

@Injectable({ providedIn: 'root' })
export class ForwarderService {
  public resourceUrl = SERVER_API_URL + 'api/forwarders';

  constructor(protected http: HttpClient) {}

  create(forwarder: IForwarder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(forwarder);
    return this.http
      .post<IForwarder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(forwarder: IForwarder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(forwarder);
    return this.http
      .put<IForwarder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IForwarder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IForwarder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(forwarder: IForwarder): IForwarder {
    const copy: IForwarder = Object.assign({}, forwarder, {
      hireDate: forwarder.hireDate && forwarder.hireDate.isValid() ? forwarder.hireDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.hireDate = res.body.hireDate ? moment(res.body.hireDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((forwarder: IForwarder) => {
        forwarder.hireDate = forwarder.hireDate ? moment(forwarder.hireDate) : undefined;
      });
    }
    return res;
  }
}
