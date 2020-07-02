import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAdminUser } from 'app/shared/model/admin-user.model';

type EntityResponseType = HttpResponse<IAdminUser>;
type EntityArrayResponseType = HttpResponse<IAdminUser[]>;

@Injectable({ providedIn: 'root' })
export class AdminUserService {
  public resourceUrl = SERVER_API_URL + 'api/admin-users';

  constructor(protected http: HttpClient) {}

  create(adminUser: IAdminUser): Observable<EntityResponseType> {
    return this.http.post<IAdminUser>(this.resourceUrl, adminUser, { observe: 'response' });
  }

  update(adminUser: IAdminUser): Observable<EntityResponseType> {
    return this.http.put<IAdminUser>(this.resourceUrl, adminUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdminUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdminUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
