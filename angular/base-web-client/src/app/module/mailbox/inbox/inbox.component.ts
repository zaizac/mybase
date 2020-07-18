import { ChangeDetectionStrategy, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService, InboxService, ReferenceService, WfwReferenceService, InboxSmartSearch } from 'bff-api';
import { SelectConfig } from 'ui-master';
import { InboxCommunicationService } from './inbox-communication.service';
import { Observable } from 'rxjs';
import { InboxConfig } from '@appmodule/mailbox/inbox.config';
import { map, debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { NgbTypeahead } from '@ng-bootstrap/ng-bootstrap';
@Component({
  selector: 'ui-inbox',
  templateUrl: './inbox.component.html',
  styleUrls: ['./inbox.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class InboxComponent implements OnInit {

  public id: number;
  public type: string;
  public markAsRead = false;
  public markAsUnRead = false;
  public deleteChecked = false;
  searchMailForm: FormGroup;
  filterForm: FormGroup;
  public appType = 'inbox';
  firstLoad = false;
  globalSearch: FormControl;
  submitted = false;
  reset = false;
  loading = false;
  statusLegends: Observable<any>;
  inboxLegends: any;
  mailboxHeader: string;

  smartValue: any;
  smartValue2: InboxSmartSearch[];
  v1: any;
  v2: any;
  appNoRefValue: string;
  value: string;
  @ViewChild('instance') instance: NgbTypeahead;

  public isInboxEmpty: boolean;
  public isPoolEmpty: boolean;
  public isHistoryEmpty: boolean;
  public isCurrInboxEmpty: boolean;
  public isCurrPoolEmpty: boolean;
  public userType: boolean;
  public userTypes: string;
  selStatusInit: SelectConfig;
  inboxConfig: InboxConfig;

  constructor(
    private route: ActivatedRoute,
    public router: Router,
    private inbox: InboxService,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private wfwSvc: WfwReferenceService,
    private inboxCommunicationService: InboxCommunicationService) {
    this.globalSearch = new FormControl('');
    this.filterForm = this.formBuilder.group({
      appRefNo: null,
      appDate: null,
      applicant: null,
      statusCd: null
    });
  }

  status = false;

  get f() {
    return this.filterForm.controls;
  }

  ngOnInit() {
    this.selStatusInitialization();
    this.inboxCommunicationService.dataEmitted$.subscribe(data => {
      this.isCurrInboxEmpty = data;
    });

    this.inboxCommunicationService.isNoPoolDataEmitted$.subscribe(data => {
      this.isCurrPoolEmpty = data;
    });

    this.statusLegends = this.wfwSvc.getRefStatusList({
      roles: this.authService.currentUserValue.userRoleGroup.userGroupCode,
      groupBy: 'statusCd, statusDesc'
    });
    this.statusLegends.subscribe(data => {
      this.inboxLegends = data;
    });
  }

  get userRoleType() {
    const inboxConfig = this.inboxCommunicationService.getConfig();
    return !inboxConfig.isInboxOnly;
  }

  get hasReleaseClaim() {
    const inboxConfig = this.inboxCommunicationService.getConfig();
    return !inboxConfig.isInboxOnly;
  }

  get mailboxHeaderText() {
    this.mailboxHeader = this.router.url.split('/')[2];
    const inboxConfig = this.inboxCommunicationService.getConfig();
    if (inboxConfig.isInboxOnly) {
      this.mailboxHeader = 'My Application';
    }
    return this.mailboxHeader;
  }

  get getRefApp() {
    this.getRefNoApplicant();
    return null;
  }


  public getBack() {
    this.router.navigate(['application/inbox']);
  }

  public refresh() {
    this.filterForm.reset();
    this.globalSearch.reset();
    this.inboxCommunicationService.emitRefresh();
  }

  public release() {
    this.inboxCommunicationService.emitRelease();
    this.isPoolEmpty = false;
  }

  public claim() {
    this.inboxCommunicationService.emitClaim();
  }

  onSubmit() {
    this.globalSearch.reset();
    this.inboxCommunicationService.emitFilter(this.f);
  }

  search(term: string) {
    this.globalSearch.setValue(term);
    this.inboxCommunicationService.emitSearch(this.globalSearch.value);
  }

  private selStatusInitialization() {
    this.selStatusInit = {
      bindLabel: 'statusDesc',
      bindValue: 'statusCd',
      searchable: true,
      data: () => {
        return this.statusLegends;
      }
    };
  }

  onReset() {
    this.reset = true;
    this.submitted = false;
    this.filterForm.reset();
  }

// onSearchChange(val): void {
//   if (val === 3) {
//     this.getRefNoApplicant();
//   }
// }

  private getRefNoApplicant() {
    const smartSearch = new InboxSmartSearch();

    this.inboxConfig = this.inboxCommunicationService.getConfig();

    if (this.inboxConfig && this.inboxConfig.taskAuthorId !== undefined) {
      smartSearch.taskAuthorId = this.inboxConfig.taskAuthorId;
    } else {
      if (!this.inboxConfig.isInboxOnly) {
        smartSearch.taskAuthorId = this.authService.currentUserValue.userId;
      }
    }

    if (this.inboxConfig && this.inboxConfig.roles !== undefined) {
      smartSearch.roles = this.inboxConfig.roles;
    } else {
      if (!this.inboxConfig.isInboxOnly) {
        smartSearch.roles = [this.authService.currentUserValue.userRoleGroup.userGroupCode];
      }
    }

    if (!this.inboxConfig.isInboxOnly) {
      smartSearch.taskAuthorId = this.authService.currentUserValue.userId;
    }

    if (!this.inboxConfig.isInboxOnly && !this.inboxConfig.roles) {
      smartSearch.roles = [this.authService.currentUserValue.userRoleGroup.userGroupCode];
    }

    if (this.inboxConfig && this.inboxConfig.additionalInfo !== undefined) {
      smartSearch.additionalInfo = JSON.stringify(this.inboxConfig.additionalInfo);
    }
    if (this.mailboxHeader === 'inbox') {
      smartSearch.inboxType = 'inbox';
    }
    if (this.mailboxHeader === 'pool') {
      smartSearch.inboxType = 'pool';
    }
    if (this.mailboxHeader === 'history') {
      smartSearch.inboxType = 'history';
    }
    const smartObj = JSON.stringify(smartSearch);
    this.inbox.getRefNoApplicant(smartObj).subscribe(data => this.smartValue = data);
    this.smartValue2 = this.smartValue;
    this.v1 = this.smartValue2.map(value1 => {
      return value1.appRefNo;
    });
    this.v2 = this.smartValue2.map(value1 => {
      return value1.applicant;
    });
  }


  formatter(value: any) {
    return value;
  }
  searchSmart = (text$: Observable<any>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => term.length < 3 ? []
        : this.v1.filter(value => value && value.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10))
    )


  formatterApp(value: any) {
    return value;
  }
  searchSmartApp = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => term.length < 3 ? []
        : this.v2.filter(value => value && value.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10))
    )

}
