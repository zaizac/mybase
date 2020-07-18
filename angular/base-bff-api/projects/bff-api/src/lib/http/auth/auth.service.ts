import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { User } from '../../models/user';
import { ChangePassword } from '../../models';
import { BffApiConfig } from '../../bff-api.config';
import { NGXLogger } from 'ngx-logger';

@Injectable({ providedIn: 'root' })
export class AuthService {

    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;
    private loggedInStatus = JSON.parse(localStorage.getItem('loggedIn') || 'false');

    constructor(
        private http: HttpClient,
        private config: BffApiConfig,
        private logger: NGXLogger) {
        var jsonParseResult = localStorage.getItem('currentUser');
        if (this.config.isProd && jsonParseResult) {
            jsonParseResult = atob(jsonParseResult);
        }
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(jsonParseResult));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    updateCurrentUserValue(user: any) {
        var jsonStrResult = JSON.stringify(user);
        if (this.config.isProd) {
            jsonStrResult = btoa(jsonStrResult);
        }
        localStorage.setItem('currentUser', jsonStrResult);
        var jsonParseResult = localStorage.getItem('currentUser');
        if (this.config.isProd && jsonParseResult) {
            jsonParseResult = atob(jsonParseResult);
        }
        this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(jsonParseResult));
    }

    public get currentUserValue(): User {
        if (this.currentUserSubject) {
            return this.currentUserSubject.value;
        }
        return null;
    }

    setLoggedIn(value: boolean) {
        this.loggedInStatus = value;
        localStorage.setItem('loggedIn', 'true');
    }

    get isLoggedIn() {
        return JSON.parse(localStorage.getItem('loggedIn') || this.loggedInStatus.toString());
    }

    login(userId: string, password: string) {

        const portalType = this.config.portalType;

        return this.http.post<User>(this.config.url + `/api/login`, { userId, password, portalType })
            .pipe(map(result => {
                // login successful if there's a token in the response                
                if (result && result.token) {
                    try {
                        // store user details and token in local storage to keep user logged in between page refreshes
                        this.updateCurrentUserValue(result);
                    } catch (e) {
                        this.logger.error(e)
                    }
                }

                return result;
            }));

    }

    logout(user: User) {
        const userId = user.userId;
        const authToken = user.token.accessToken;

        // remove user from local storage to log user out
        const isLogout = this.http.post(this.config.url + `/api/logout`, { userId, authToken })
            .pipe(map(result => {
                // login successful if there's a token in the response                
                if (result) {
                    localStorage.removeItem('currentUser');
                    localStorage.removeItem('loggedIn');
                    this.currentUserSubject = null;
                }

                return result;
            }));
        return isLogout;
    }

    revokeAccess() {
        localStorage.removeItem('currentUser');
        localStorage.removeItem('loggedIn');
        this.currentUserSubject = null;
    }

    changePassword(changePassword: ChangePassword) {
        return this.http.post(this.config.url + `/api/password/change`,
            JSON.stringify(changePassword))
            .pipe(map(result => {
                return result;
            }));
    }

    resetPassword(userName: string) {        
        return this.http.post(this.config.url + `/api/password/reset`, {
            userName: userName
        })
            .pipe(map(result => {
                return result;
            }));
    }

    public hasRole(validateRole: string): boolean {
        var roleList = [];
        var roles = this.currentUserSubject.value.roles;
        for (var key in roles) {
            roleList.push(roles[key].roleCode);
        }

        if (roleList.indexOf(validateRole) > -1) {
            return true;
        }
        return false;
    }

    public hasAnyRole(...validateRoles: string[]): boolean {
        var roleList = [];
        var roles = this.currentUserSubject.value.roles;
        if (roles) {
            for (var key in roles) {
                roleList.push(roles[key].roleCode);
            }
            for (var i = 0; i < validateRoles.length; i++) {
                if (roleList.indexOf(validateRoles[i]) > -1) {
                    return true;
                }
            }
        }
        return false;
    }
}