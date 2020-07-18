import { Component, DoCheck, Inject, LOCALE_ID, OnDestroy, OnInit, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { Subject, Subscription } from 'rxjs';
import { AuthService, InboxService, ReferenceService } from 'bff-api';
import { DatatableConfig, SelectConfig } from 'ui-master';
import { WebInboxService } from '../../inbox/inbox.service';
import { InboxCommunicationService } from '../inbox-communication.service';
import { AppComponent } from 'src/app/app.component';
import { InboxConfig } from '@appmodule/mailbox/inbox.config';

@Component({
  selector: 'ui-inbox-list',
  templateUrl: './inbox-list.component.html',
  styleUrls: ['./inbox-list.component.scss'],
  providers: [WebInboxService]
})
export class InboxListComponent implements OnInit, OnDestroy, DoCheck, AfterViewInit {

  public type: string;
  public isAllSelected: boolean;
  public searchText: string;
  selectedData: any;
  header: DatatableConfig;
  dtData4: any;
  applicationForm: FormGroup;
  newData: any;
  isRelease: false;
  eventSubmit: Subject<void> = new Subject<void>();
  filterData: any;
  firstLoad = true;
  oldServerRespVal: any;
  selStatusInit: SelectConfig;
  globalSearch: string;
  releaseSubs: Subscription;
  inboxConfig: InboxConfig;

  constructor(
    public router: Router,
    public inbox: InboxService,
    private formBuilder: FormBuilder,
    private logger: NGXLogger,
    @Inject(LOCALE_ID) private locale: string,
    private authService: AuthService,
    private webInboxService: WebInboxService,
    private inboxCommunicationService: InboxCommunicationService,
    private reference: ReferenceService,
    private route: ActivatedRoute,
    private cd: ChangeDetectorRef
  ) {
    this.applicationForm = this.formBuilder.group({
      appRefNo: null,
      appDate: null,
      applicant: null,
      statusCd: null
    });

    this.webInboxService.applicationType('inbox');
  }
  get a() {
    return this.applicationForm.controls;
  }

  ngOnInit() {
    // this.tableInitialization();

    this.inboxCommunicationService.filterEmitted$.subscribe(data => {
      this.header.searchForm = data;
      this.header.refresh();
    });

    this.inboxCommunicationService.searchEmitted$.subscribe(data => {
      this.globalSearch = data || '';
      this.header.refresh();
    });
    this.releaseSubs = this.inboxCommunicationService.releaseEmitted$.subscribe(data => {
      this.release();
      this.header.reload();
    });

    this.inboxCommunicationService.refreshEmitted$.subscribe(data => {
      this.header.reload();
    });
  }

  ngAfterViewInit(): void {
    this.tableInitialization();
    this.cd.detectChanges();
  }

  ngOnDestroy() {
    if (this.releaseSubs) {
      this.releaseSubs.unsubscribe();
    }
  }

  ngDoCheck() {
    if (this.firstLoad) {
      this.firstLoad = !this.firstLoad;
      if (this.header) {
        this.oldServerRespVal = this.header.serverSideResp;
        if (this.header.serverSideResp) {
          this.inboxCommunicationService.emitData(Number(this.header.serverSideResp.recordsTotal) === 0);
        }
      }
    } else {
      if (this.oldServerRespVal !== this.header.serverSideResp) {
        this.oldServerRespVal = this.header.serverSideResp;
        if (this.header.serverSideResp) {
          this.inboxCommunicationService.emitData(Number(this.header.serverSideResp.recordsTotal) === 0);
        }
      }
    }
  }

  get serverSideResp() {
    return this.header.serverSideResp;
  }

  get isNoData() {
    if (this.header.serverSideResp) {
      return Number(this.header.serverSideResp.recordsTotal) === 0;
    }
    return true;
  }

  private tableInitialization() {
    this.header = {
      columns: [
        {
          type: 'checkbox',
          width: '50px'
        },
        { field: 'appRefNo', headerText: 'Ref. No.', width: '20%' },
        { field: 'appDate', headerText: 'Appl. Date', width: '20%' },
        { field: 'applicant', headerText: 'Applicant', width: '25%' },
        { field: 'refType.description', headerText: 'Task Name', width: '25%' },
        {
          field: '',
          headerText: 'Status',
          width: '150px',
          columnTemplate: {
            id: 'myTemplate',
            context: {}
          },
          render: (data, cell, row) => {
            data = row.refStatus.statusCd;
            this.header.columns[5].columnTemplate.context = { color: row.refStatus.legendColor, title: row.refStatus.statusDesc};
          }
        },
        {
          field: '',
          customAttributes: {
            'no-row-click': true,
            class: 'grid-td-dropdown'
          },
          headerText: '',
          width: '100px',
          columnTemplate: {
            id: 'actionTemplate',
            context: {}
          },
          render: (data, cell, row) => {
            if (row.refLevel.refTypeActionList) {
              this.header.columns[6].columnTemplate.context = { info: row };
            }
          }
        }
      ],
      textWrap: {
        allowWrap: true,
        mode: 'Both'
      },
      searchForm: this.a,
      selectedData: [],
      data: dtRequest => {
        dtRequest.globalParam = this.globalSearch || '';
        this.inboxConfig = this.inboxCommunicationService.getConfig();
        if (this.inboxConfig && this.inboxConfig.taskAuthorId !== undefined) {
          dtRequest.taskAuthorId = this.inboxConfig.taskAuthorId;
        } else {
          if (!this.inboxConfig.isInboxOnly) {
            dtRequest.taskAuthorId = this.authService.currentUserValue.userId;
          }
        }

        if (this.inboxConfig && this.inboxConfig.roles !== undefined) {
          dtRequest.roles = this.inboxConfig.roles;
        } else {
          if (!this.inboxConfig.isInboxOnly) {
            dtRequest.roles = [this.authService.currentUserValue.userRoleGroup.userGroupCode];
          }
        }

        if (!this.inboxConfig.isInboxOnly) {
          dtRequest.taskAuthorId = this.authService.currentUserValue.userId;
        }

        if (!this.inboxConfig.isInboxOnly && !this.inboxConfig.roles) {
          dtRequest.roles = [this.authService.currentUserValue.userRoleGroup.userGroupCode];
        }

        if (this.inboxConfig && this.inboxConfig.additionalInfo !== undefined) {
          dtRequest.additionalInfo = JSON.stringify(this.inboxConfig.additionalInfo);
        }

        return this.inbox.getMyInbox(dtRequest);
      }
    };
  }

  onSubmit() {
    this.header.searchForm = this.a;
    this.header.refresh();
  }

  release() {
    this.inbox.releaseTasks({
      taskMasterIdList: this.header.selectedData.map(data => data.taskMasterId),
      taskAuthorId: this.authService.currentUserValue.userId
    })
      .subscribe(
        data => {
          this.logger.info('Successful');
          this.header.refresh();
        },
        error => console.log('Error: ', error)
      );
  }

  handleRowClick(row) {
    if (row.refType.redirectPath) {
      this.router.navigate([row.refType.redirectPath], { state: row });
    }
  }

  handleRowClickAction(row, redirectPath) {
    if (row) {
      this.router.navigate([redirectPath], { state: row });
    }
  }
}
