import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BffApiConfig } from '../../bff-api.config';
import { NGXLogger } from 'ngx-logger';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WfwReferenceService {

  constructor( 
    private http: HttpClient,
    private config: BffApiConfig,
    private logger: NGXLogger) 
    { }

    getRefConfig(reference: any) {
      return this.http
        .post(
          this.config.url + `/wfw/ref`, reference);
    }

    getRefTypePagination(reference: any) {
      return this.http
        .post(
          this.config.url + `/wfw/ref/pagination`, reference);
    }

    getRefTypeList(reference: any) {
      return this.http
        .post(
          this.config.url + `/wfw/ref/type-list`, reference);
    }

    getRefLevelList(level: any) {
      return this.http
        .post(
          this.config.url + `/wfw/ref/level-list`, level);
    }

    getRefLevelPagination(level: any) {
      return this.http
        .post(
          this.config.url + `/wfw/ref/level/pagination`, level);
    }

    addUpdateType(type: any) {
      return this.http
        .post(
          this.config.url + `/wfw/ref/type/addUpdate`, type);
    }

    addUpdateLevel(level: any) {
      return this.http
        .post(
          this.config.url + `/wfw/ref/level/addUpdate`, level);
    }

    addUpdateStatus(level: any) {
      return this.http
        .post(
          this.config.url + `/wfw/ref/status/addUpdate`, level);
    }

    getRefTypeActionList(action: any) {
      return this.http
        .post(
          this.config.url + `/wfw/ref/action-list`, action);
    }

    getRefStatusList(status: any) {
      return this.http
        .post(
          this.config.url + `/wfw/ref/status-list`, status);
    }
}
