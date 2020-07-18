import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BffApiConfig } from '../../bff-api.config';
import { AuthService } from '../auth/auth.service';
import { NGXLogger } from 'ngx-logger';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TestService {

  constructor(
    private http: HttpClient,
    private config: BffApiConfig,
    private authService: AuthService,
    private logger: NGXLogger
  ) { }

  testSignUp(formData): Observable<any> {
    return this.http
      .post(
        this.config.url + `/api/test/signUp`,
        formData
      );
  }

  testUpdateSignUp(formData): Observable<any> {
    return this.http
      .post(
        this.config.url + `/api/test/updateSignUp`,
        formData
      );
  }

  testFindByRefNo(refNo: string): Observable<any> {
    return this.http
      .get(
        this.config.url + `/api/test/findSignUpByRefNo?refNo=` + refNo
      );
  }

}