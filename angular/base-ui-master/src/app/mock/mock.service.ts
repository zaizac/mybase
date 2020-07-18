import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { v4 as uuid } from 'uuid';
import { UiMasterConfig } from 'projects/ui-master/src/lib/ui-master.config';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MockService {
  private uniqueId: string = uuid();

  constructor(
    private http: HttpClient,
    private config: UiMasterConfig
  ) { }

  private httpHeaders(): any {
    let authorization = btoa(this.config.client + ":" + this.config.skey);
    let headers = new HttpHeaders({
      'Content-Type': 'application/json; charset=utf-8',
      'Access-Control-Allow-Origin': '*',
      'Authorization': 'Basic ' + authorization,
      'X-Message-Id': this.uniqueId
    });

    return headers;
  }

  // otpservice call
  generateOtp(subUrl: string) {
    return this.http.get(this.config.url + '/api/otp/generate/' + this.config.portalType + '?' + subUrl, {
      headers: this.httpHeaders()
    });
  }

  validateOtp(subUrl: string, otpCode: string) {
    return this.http.post(this.config.url + '/api/otp/validate/' + this.config.portalType + '?' + subUrl, { "otpCode": otpCode }, {
      headers: this.httpHeaders()
    });
  }

  captcha(page: string): Observable<Blob> {
    return this.http.get(this.config.url + '/api/captcha/' + this.config.portalType + '/' + page + '?uniqueId=' + this.uniqueId, {
      headers: this.httpHeaders(),
      responseType: 'blob'
    });
  }

  validateCaptcha(captcha: string, page: string): Observable<any> {
    return this.http.post(this.config.url + '/api/captcha/' + this.config.portalType + '/' + page + '/validate/' + '?uniqueId=' + this.uniqueId, { 'captcha': captcha }, {
      headers: this.httpHeaders()
    });
  }

}