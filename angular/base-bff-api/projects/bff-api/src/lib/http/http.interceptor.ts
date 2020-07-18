import { Injectable, Optional, Inject } from '@angular/core';
import {
  HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpEventType,
  HttpHeaders, HttpResponse, HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { NGXLogger } from 'ngx-logger';
import { tap, finalize, catchError } from 'rxjs/operators';
import { AuthService } from './auth/auth.service';
import { User } from '../models';
import { BffApiConfig } from '../bff-api.config';
import { v4 as uuid } from 'uuid';
import { Request } from 'express';
import { REQUEST } from '@nguniversal/express-engine/tokens';
import { Router } from '@angular/router';

@Injectable()
export class ApiHttpInterceptor implements HttpInterceptor {
  private uniqueId: string = uuid();

  constructor(private logger: NGXLogger, private authService: AuthService, private router: Router,
    private config: BffApiConfig, @Optional() @Inject(REQUEST) protected request: Request) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<HttpEventType.Response>> {
    let object = {};
    if (req.body instanceof FormData) {
      const formData = req.body;
      formData.forEach((value, key) => { object[key] = value; });
    } else {
      object = req.body;
    }

    const currUser = this.authService.currentUserValue;

    let authorization = btoa(this.config.client + ':' + this.config.skey);

    if (currUser && currUser.token) {
      authorization = btoa(this.config.client + ':' + currUser.token.accessToken);
    }

    const headers = new HttpHeaders({
      'Content-Type': 'application/json; charset=utf-8',
      'Access-Control-Allow-Origin': '*',
      'Authorization': 'Basic ' + authorization,
      'X-Message-Id': this.uniqueId,
      'X-Portal-Type' : this.config.portalType
    });

    const authReq = req.clone({
      headers: headers,
      body: object
    });

    // intercept express server request
    let serverReq: HttpRequest<any> = authReq;
    if (this.request) {
      let newUrl = `${this.request.protocol}://${this.request.get('host')}`;
      if (!authReq.url.startsWith('/')) {
        newUrl += '/';
      }
      newUrl += authReq.url;
      serverReq = authReq.clone({ url: newUrl, headers, body: object });
      this.logger.info('Intercept Request : serverReq : ', serverReq);
    }


    const startTime = Date.now();
    let status: string;
    return next.handle(serverReq).pipe(
      catchError((error: HttpErrorResponse) => {
        let data = {
          code: error && error.error && error.error.code ? error.error.code : '',
          reason: error && error.error && error.error.reason ? error.error.reason : '',
          status: error.status,
          message: error.message
        };

        // If Access Token not found, revoke access and redirect to Login page
        if (data.code === 'I404IDM115') {
          this.authService.revokeAccess();
          this.router.navigate(['/login']);
        }

        this.logger.error(data);
        if (error.status === 401) {
          // 401 handled in http.interceptor
          // this.toastr.error(error.message);
          this.authService.revokeAccess();
          this.router.navigate(['/login']);
        }
        return throwError(error);
      }),
      tap(
        event => {
          status = '';
          if (event instanceof HttpResponse) {
            status = 'succeeded';
          }
        },
        error => status = 'failed'
      ),
      finalize(() => {
        const elapsedTime = Date.now() - startTime;
        const message = req.method + ' ' + req.urlWithParams + ' ' + status
          + ' in ' + elapsedTime + 'ms';
        this.logger.debug(message);
      })
    );
  }


}