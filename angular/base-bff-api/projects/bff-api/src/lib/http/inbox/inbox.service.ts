import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { BffApiConfig } from '../../bff-api.config';

@Injectable({
  providedIn: 'root'
})
export class InboxService {

  constructor(
    private http: HttpClient,
    private config: BffApiConfig
  ) { }

  getMyInbox(payload: any) {
    const params = { params: payload };
    return this.http
      .get(
        this.config.url + `/inbox/wfw/myInbox/paginated`, Object.assign({}, params)
      ).pipe(map(result => {
        return result;
      })
      );
  }

  getMasterHistory(payload: any) {
    const params = { params: payload };
    return this.http
      .get(
        this.config.url + `/inbox/wfw/masterHistory/paginated`, Object.assign({}, params)
        ).pipe(map(result => {
          return result;
        })
        );
  }

  getMyHistory(payload: any) {
    const params = { params: payload };
    return this.http
      .get(
        this.config.url + `/inbox/wfw/myHistory/paginated`, Object.assign({}, params)
        ).pipe(map(result => {
          return result;
        })
        );
  }

  getMyPool(payload: any) {
    const params = { params: payload };
    return this.http
      .get(
        this.config.url + `/inbox/wfw/myPool/paginated`, Object.assign({}, params)
      ).pipe(map(result => {
        return result;
      })
      );
  }

  claimTasks(payload: any) {
    return this.http.post(this.config.url + `/inbox/wfw/claim`, payload);
  }

  releaseTasks(payload: any) {
    return this.http.post(this.config.url + `/inbox/wfw/release`, payload);
  }

  getRefNoApplicant(payload: any) {
    return this.http.post(this.config.url + `/inbox/wfw/refNoApplicant`, payload);
  }
}
