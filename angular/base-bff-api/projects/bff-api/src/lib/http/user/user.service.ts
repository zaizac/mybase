import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NGXLogger } from 'ngx-logger';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';

import { BffApiConfig } from '../../bff-api.config';
import { UserProfile, User } from '../../models';
import { AuthService } from '../auth/auth.service';
import { UserRoleGroup } from '../../models/userRoleGroup';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(
    private http: HttpClient,
    private config: BffApiConfig,
    private authService: AuthService,
    private logger: NGXLogger
  ) { }

  searchPaginated(payload: any, isPortalAdmin: boolean) {
    payload.portalAdmin = isPortalAdmin;
    const params = { params: payload };
    return this.http
      .get(
        this.config.url + `/api/users/paginated`,
        Object.assign({}, params)
      );
  }

  search(payload: any): Observable<UserProfile[]> {
    const params = { params: payload };
    return this.http
      .get<UserProfile[]>(
        this.config.url + `/api/users/search`,
        Object.assign({}, params)
      );
  }

  createUser(formData) {
    return this.http
      .post(
        this.config.url + `/api/users/create`,
        formData
      );
  }

  updateUser(formData, isProfile: boolean): Observable<UserProfile> {
    return this.http
      .post<UserProfile>(
        this.config.url + `/api/users/update`,
        formData
      )
      .pipe(
        map(result => {
          if (isProfile && formData.docMgtId) {
            var currUser = this.authService.currentUserValue;
            currUser.docMgtId = formData.docMgtId;
            this.authService.updateCurrentUserValue(currUser);
          }
          return result;
        })
      );
  }

  getByUserId(userId): Observable<UserProfile> {
    const params = {
      params: {
        userId: userId
      }
    };
    return this.http
      .get<UserProfile>(
        this.config.url + `/api/users/find`, params
      );
  }

  getUserRoleGroups(userType: string, userRoleGroupCode: string, hasMaxNoUserCreated: boolean, noSystemCreate: boolean): Observable<UserRoleGroup[]> {
    const params = {
      params: {
        userType: userType,
        userRoleGroupCode: userRoleGroupCode,
        hasMaxNoUserCreated: '' + hasMaxNoUserCreated,
        noSystemCreate: '' + noSystemCreate
      }
    };
    return this.http
      .get<UserRoleGroup[]>(
        this.config.url + `/api/users/getUserRoleGroups`, params
      );
  }


  searchUserGroupBranch(userGroupBranchCode: string, countryCd: string) {
    const params = { params: { userGroupCode: userGroupBranchCode, countryCode: countryCd } };
    return this.http
      .get(
        this.config.url + `/api/users/userGroupBranch`, params
      );
  }

  userAction(userId: string, status: string) {
    const params = {
      params: {
        userId: userId
      }
    };

    let url: any;
    switch (status) {
      case 'F': {
        url = `/api/users/resentCredentials`;
        break;
      }
      case 'A': {
        url = `/api/users/deactivate`;
        break;
      }
      case 'I': {
        url = `/api/users/activate`;
        break;
      }
      case 'reset': {
        url = `/api/users/resetPassword`;
        break;
      }
    }
    return this.http
      .get(this.config.url + url, params)
      ;
  }

}
