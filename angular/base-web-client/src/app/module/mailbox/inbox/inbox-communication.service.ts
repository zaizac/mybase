import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { InboxConfig } from '../inbox.config';

@Injectable({
  providedIn: 'root'
})
export class InboxCommunicationService {
  private emitRfreshSource = new Subject<any>();
  private emitDataSource = new Subject<any>();
  private emitReleaseSource = new Subject<any>();
  private emitClaimSource = new Subject<any>();
  private emitFilterSource = new Subject<any>();
  private emitSearchSource = new Subject<any>();
  private emitIsNoPoolDataSource = new Subject<any>();
  private emitIsNoHisDataSource = new Subject<any>();

  refreshEmitted$ = this.emitRfreshSource.asObservable();
  dataEmitted$ = this.emitDataSource.asObservable();
  releaseEmitted$ = this.emitReleaseSource.asObservable();
  claimEmitted$ = this.emitClaimSource.asObservable();
  filterEmitted$ = this.emitFilterSource.asObservable();
  searchEmitted$ = this.emitSearchSource.asObservable();
  isNoPoolDataEmitted$ = this.emitIsNoPoolDataSource.asObservable();
  isNoHisDataEmitted$ = this.emitIsNoHisDataSource.asObservable();

  private inboxConfig: InboxConfig;

  emitRefresh() {
    this.emitRfreshSource.next();
  }

  emitData(data) {
    this.emitDataSource.next(data);
  }

  emitRelease() {
    this.emitReleaseSource.next();
  }

  emitClaim() {
    this.emitClaimSource.next();
  }

  emitFilter(data) {
    this.emitFilterSource.next(data);
  }

  emitSearch(data) {
    this.emitSearchSource.next(data);
  }

  emitIsNoPoolData(data: boolean) {
    this.emitIsNoPoolDataSource.next(data);
  }

  emitIsNoHisData(data: boolean) {
    this.emitIsNoHisDataSource.next(data);
  }

  setConfig(config: InboxConfig) {
    this.inboxConfig = config;
  }

  getConfig() {
    return this.inboxConfig;
  }

  constructor() { }
}
