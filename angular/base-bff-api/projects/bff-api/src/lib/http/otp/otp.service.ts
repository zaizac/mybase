import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { BffApiConfig } from '../../bff-api.config';

@Injectable({
  providedIn: 'root'
})
export class OtpService {

  constructor(
    private http: HttpClient,
    private config: BffApiConfig
  ) { }


  // otpservice call
  generateOtp(subUrl: string) {
    return this.http.get(this.config.url + '/api/otp/generate/' + this.config.portalType + '?' + subUrl);
  }

  validateOtp(subUrl: string, otpCode: string) {
    return this.http.post(this.config.url + '/api/otp/validate/' + this.config.portalType + '?' + subUrl, { "otpCode": otpCode });
  }

}