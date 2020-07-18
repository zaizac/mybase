import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { v4 as uuid } from 'uuid';

import { BffApiConfig } from '../../bff-api.config';


@Injectable({
  providedIn: 'root'
})
export class CaptchaService {
  private uniqueId: uuid = uuid();
  constructor(
    private http: HttpClient,
    private config: BffApiConfig
  ) { }

  captcha(page: string): Observable<Blob> {
    return this.http.get(this.config.url + '/api/captcha/' + this.config.portalType + '/' + page + '?uniqueId=' + this.uniqueId,
      { responseType: 'blob' });
  }

  validateCaptcha(captcha: string, page: string): Observable<any> {
    return this.http.post(this.config.url + '/api/captcha/' + this.config.portalType + '/' + page + '?uniqueId=' + this.uniqueId, { 'captcha': captcha });
  }

}