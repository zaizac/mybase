import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { NGXLogger } from 'ngx-logger';
import { BffApiConfig } from '../../bff-api.config';
import { Observable } from 'rxjs';
import { Documents, StaticData } from '../../models';
import { IdmConfigDto } from '../../models/idmconfigdto';
import { map } from 'rxjs/operators';
import { City } from '../../models/city';
import { State } from '../../models/state';
import { Country } from '../../models/country';
import { Reason } from '../../models/reason';
import { Status } from '../../models/status';
import { Metadata } from '../../models/metadata';
import { PortalType } from '../../models/portalType';


@Injectable({ providedIn: 'root' })
export class ReferenceService {

  apiRef = '/api/references';
  apiRefUrl: string;

  constructor(
    private http: HttpClient,
    private config: BffApiConfig,
    private logger: NGXLogger) { this.apiRefUrl = this.config.url + this.apiRef; }

  // Get config reference
  idmConfig() {
    return this.configMap('IDM_CONFIG');
  }

  idmConfigByPortalType(portalType: string): Observable<IdmConfigDto[]> {
    return this.http.get<IdmConfigDto[]>(this.apiRefUrl + '/config/portalType?portalType=' + portalType.toUpperCase());
  }

  updateIdmConfig(portalType: string, idmConfigDtos: IdmConfigDto[]): Observable<[]> {
    this.logger.debug('Update IdmConfig: portalType [', portalType, '] : idmConfigDtos [', idmConfigDtos, ']');
    return this.http.post<[]>((this.apiRefUrl + '/config/updateConfig?portalType=' + portalType.toUpperCase()),
      idmConfigDtos);
  }

  beConfig() {
    return this.configMap('BE_CONFIG');
  }

  private configMap(configStr: string) {
    return this.http.get(this.apiRefUrl + '/config?config=' + configStr);
  }

  userStatus(): Observable<StaticData[]> {
    return this.http
      .get<StaticData[]>(
        this.apiRefUrl + '/userStatus'
      )
      .pipe(
        map(result => {
          var res: any = JSON.stringify(result);
          res = JSON.parse(res);
          var resp = [];
          for (var type in res) {
            var obj = {};
            obj['code'] = type;
            obj['desc'] = res[type];
            resp.push(obj);
          }
          return resp;
        })
      );
  }

  userStatusMap() {
    return this.http
      .get<Map<string, string>>(
        this.apiRefUrl + '/userStatus'
      )
      .pipe(
        map(result => {
          var res: any = JSON.stringify(result);
          res = JSON.parse(res);
          return new Map<string, string>(Object.entries(res));
        })
      );
  }

  countryServe(): Observable<[]> {
    return this.http.get<[]>(this.apiRefUrl + '/countries/serve');
  }

  retrieveCities(): Observable<City[]> {
    return this.http.get<City[]>(this.apiRefUrl + '/cities');
  }

  createRefCities(cities: City[]): Observable<[]> {
    this.logger.debug('Create Cities:  [', cities, ']');
    return this.http.post<[]>((this.apiRefUrl + '/cities/create'), cities);
  }

  updateRefCities(cities: City[]): Observable<[]> {
    this.logger.debug('Update Cities:  [', cities, ']');
    return this.http.post<[]>((this.apiRefUrl + '/cities/update'), cities);
  }

  searchRefCities(city: City): Observable<[]> {
    this.logger.debug('Search Cities:  [', city, ']');
    return this.http.post<[]>((this.apiRefUrl + '/cities/search'), city);
  }

  retrieveStates(): Observable<State[]> {
    return this.http.get<State[]>(this.apiRefUrl + '/states');
  }

  createRefStates(states: State[]): Observable<[]> {
    this.logger.debug('Create States:  [', states, ']');
    return this.http.post<[]>((this.apiRefUrl + '/states/create'), states);
  }

  updateRefStates(states: State[]): Observable<[]> {
    this.logger.debug('Update States:  [', states, ']');
    return this.http.post<[]>((this.apiRefUrl + '/states/update'), states);
  }

  searchRefStates(state: State): Observable<[]> {
    this.logger.debug('Search States:  [', state, ']');
    return this.http.post<[]>((this.apiRefUrl + '/states/search'), state);
  }

  retrieveCountries(): Observable<Country[]> {
    return this.http.get<Country[]>(this.apiRefUrl + '/countries');
  }

  createRefCountries(countries: Country[]): Observable<[]> {
    this.logger.debug('Create Countries:  [', countries, ']');
    return this.http.post<[]>((this.apiRefUrl + '/countries/create'), countries);
  }

  updateRefCountries(countries: Country[]): Observable<[]> {
    this.logger.debug('Update Countries:  [', countries, ']');
    return this.http.post<[]>((this.apiRefUrl + '/countries/update'), countries);
  }

  searchRefCountries(country: Country): Observable<[]> {
    this.logger.debug('Search Countries:  [', country, ']');
    return this.http.post<[]>((this.apiRefUrl + '/countries/search'), country);
  }


  refreshStaticRef(staticlistType: string): Observable<[]> {
    this.logger.debug('Refresh Static References.');
    if (staticlistType) {
      const params = new HttpParams().set('staticlistType', staticlistType);
      this.logger.debug(this.apiRefUrl + '/refresh' + { params });
      return this.http.get<[]>(this.apiRefUrl + '/refresh', { params });
    } else {
      this.logger.debug(this.apiRefUrl + '/refresh' + ' --- no param ---');
      return this.http.post<[]>(this.apiRefUrl + '/refresh', '');
    }
  }

  // Static Documents services
  retrieveDocuments(): Observable<Documents[]> {
    return this.http.get<Documents[]>(this.apiRefUrl + '/documents');
  }

  createDocuments(documents: Documents): Observable<[]> {
    this.logger.debug('Create Documents:  [', documents, ']');
    return this.http.post<[]>((this.apiRefUrl + '/documents/create'), documents);
  }

  updateDocuments(documents: Documents): Observable<[]> {
    this.logger.debug('Update Documents:  [', documents, ']');
    return this.http.post<[]>((this.apiRefUrl + '/documents/update'), documents);
  }

  searchDocuments(documents: Documents): Observable<[]> {
    this.logger.debug('Update Documents:  [', documents, ']');
    return this.http.post<[]>((this.apiRefUrl + '/documents/search'), documents);
  }

  searchByDocumentBucket(dmBucket: string): Observable<Reason[]> {
    const params = { params: { 'dmBucket': dmBucket } };
    return this.http.get<Reason[]>(this.apiRefUrl + '/documents/findByDocBucket', Object.assign({}, params));
  }

  searchByDocumentDesc(docDesc: string): Observable<Reason[]> {
    const params = { params: { 'docDesc': docDesc } };
    return this.http.get<Reason[]>(this.apiRefUrl + '/documents/findByDocDesc', Object.assign({}, params));
  }

  searchByDocumentType(type: string): Observable<Reason[]> {
    const params = { params: { 'type': type } };
    return this.http.get<Reason[]>(this.apiRefUrl + '/documents/findByDocType', Object.assign({}, params));
  }

  findDocumentsByTrxnNo(trxnNo: string): Observable<Documents[]> {
    const params = { params: { 'docTrxnNo': trxnNo } };
    return this.http
      .get<Documents[]>(
        this.apiRefUrl + '/documents/findByDocTrxnNo', Object.assign({}, params)
      );
  }

  // Static Reason services
  retrieveReason(): Observable<Reason[]> {
    return this.http.get<Reason[]>(this.apiRefUrl + '/reason');
  }

  createReason(reason: Reason): Observable<[]> {
    this.logger.debug('Create Reason:  [', reason, ']');
    return this.http.post<[]>((this.apiRefUrl + '/reason/create'), reason);
  }

  updateReason(reason: Reason): Observable<[]> {
    this.logger.debug('Update Reason:  [', reason, ']');
    return this.http.post<[]>((this.apiRefUrl + '/reason/update'), reason);
  }

  searchReason(reason: Reason): Observable<[]> {
    this.logger.debug('Update Reason:  [', reason, ']');
    return this.http.post<[]>((this.apiRefUrl + '/reason/search'), reason);
  }

  searchByReasonCd(reasonCd: string): Observable<Reason[]> {
    const params = { params: { 'reasonCd': reasonCd } };
    return this.http.get<Reason[]>(this.apiRefUrl + '/reason/findByeasonCd', Object.assign({}, params));
  }

  searchByReasonType(reasonType: string): Observable<Reason[]> {
    const params = { params: { 'reasonType': reasonType } };
    return this.http.get<Reason[]>(this.apiRefUrl + '/reason/findByReasonType', Object.assign({}, params));
  }

  searchByReasonDesc(reasonDesc: string): Observable<Reason[]> {
    const params = { params: { 'reasonDesc': reasonDesc } };
    return this.http.get<Reason[]>(this.apiRefUrl + '/reason/findByReasonDesc', Object.assign({}, params));
  }

  // Static Status services
  retrieveStatus(): Observable<Status[]> {
    return this.http.get<Status[]>(this.apiRefUrl + '/status');
  }

  createStatus(status: Status): Observable<[]> {
    this.logger.debug('Create Status:  [', status, ']');
    return this.http.post<[]>((this.apiRefUrl + '/status/create'), status);
  }

  updateStatus(status: Status): Observable<[]> {
    this.logger.debug('Update Status:  [', status, ']');
    return this.http.post<[]>((this.apiRefUrl + '/status/update'), status);
  }

  searchStatus(status: Status): Observable<[]> {
    this.logger.debug('Update Status:  [', status, ']');
    return this.http.post<[]>((this.apiRefUrl + '/status/search'), status);
  }

  searchByStatusCd(statusCd: string): Observable<Status[]> {
    const params = { params: { 'statusCd': statusCd } };
    return this.http.get<Status[]>(this.apiRefUrl + '/status/findByStatusCd', Object.assign({}, params));
  }

  searchByStatusType(statusType: string): Observable<Status[]> {
    const params = { params: { 'statusType': statusType } };
    return this.http.get<Status[]>(this.apiRefUrl + '/status/findByStatusType', Object.assign({}, params));
  }

  searchByStatusDesc(statusDesc: string): Observable<Status[]> {
    const params = { params: { 'statusDesc': statusDesc } };
    return this.http.get<Status[]>(this.apiRefUrl + '/status/findByStatusDesc', Object.assign({}, params));
  }

  // Static Metadata services
  retrieveMetadata(): Observable<Metadata[]> {
    return this.http.get<Metadata[]>(this.apiRefUrl + '/metadata');
  }

  createMetadata(metadata: Metadata): Observable<[]> {
    this.logger.debug('Create Metadata:  [', metadata, ']');
    return this.http.post<[]>((this.apiRefUrl + '/metadata/create'), metadata);
  }

  updateMetadata(metadata: Metadata): Observable<[]> {
    this.logger.debug('Update Metadata:  [', metadata, ']');
    return this.http.post<[]>((this.apiRefUrl + '/metadata/update'), metadata);
  }

  searchMetadata(metadata: Metadata): Observable<[]> {
    this.logger.debug('Update Metadata:  [', metadata, ']');
    return this.http.post<[]>((this.apiRefUrl + '/metadata/search'), metadata);
  }

  searchByMetadataCd(mtdtCd: string): Observable<Metadata[]> {
    const params = { params: { 'mtdtCd': mtdtCd } };
    return this.http.get<Metadata[]>(this.apiRefUrl + '/metadata/findByMtdtCd', Object.assign({}, params));
  }

  searchByMetadataType(mtdtType: string): Observable<Metadata[]> {
    const params = { params: { 'mtdtType': mtdtType } };
    return this.http.get<Metadata[]>(this.apiRefUrl + '/metadata/findByMtdtType', Object.assign({}, params));
  }

  searchByMetadataDesc(mtdtDesc: string): Observable<Metadata[]> {
    const params = { params: { 'mtdtDesc': mtdtDesc } };
    return this.http.get<Metadata[]>(this.apiRefUrl + '/metadata/findByMtdtDesc', Object.assign({}, params));
  }

  retrievePortalTypeAll(): Observable<PortalType[]> {
    return this.http.get<PortalType[]>(this.apiRefUrl + '/portalType');
  }

  updatePortalType(portalTypes: PortalType[]): Observable<PortalType[]> {
    return this.http.post<PortalType[]>((this.apiRefUrl + '/portalType/update'), portalTypes);
  }

  deletePortalType(portalType: PortalType): Observable<boolean> {
    return this.http.post<boolean>((this.apiRefUrl + '/portalType/delete'), portalType);
  }

}
