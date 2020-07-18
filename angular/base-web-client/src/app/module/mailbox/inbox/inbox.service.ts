import { Injectable } from '@angular/core';
import { InboxComponent } from './inbox.component';

@Injectable({
  providedIn: 'root'
})
export class WebInboxService {

  constructor(private inbox: InboxComponent) { }

  setLegends(legends: any) {
    this.inbox.inboxLegends = legends;
  }

  applicationType(type: string) {
    this.inbox.appType = type;
  }



}
