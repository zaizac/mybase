import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Document } from '../../models/document'
import { BffApiConfig } from '../../bff-api.config';

@Injectable({
  providedIn: 'root'
})
export class DocumentService {

  constructor(
    private http: HttpClient,
    private config: BffApiConfig
  ) { }

  download(docid: string) {
    return this.http.get<Document>(this.config.url + `/api/references/documents/download/` + docid);
  }

  downloadWithProjId(docid: string) {
    return this.http.get<Document>(this.config.url + `/api/references/documents/download/urp/` + docid);
  }

  downloadContent(docid: string) {
    return this.http.get(this.config.url + `/api/references/documents/download/content/urp/` + docid, { responseType: "blob" });
  }

  upload(document: Document) {
    return this.http.post<Document>(this.config.url + `/api/references/documents/upload/urp`, JSON.stringify(document));
  }

  getRefDocuments(trxnNo: string) {
    return this.http.get(this.config.url + `/api/references/documents/ref/` + trxnNo);
  }
}
