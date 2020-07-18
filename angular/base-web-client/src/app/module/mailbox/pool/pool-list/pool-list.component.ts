import { Component, DoCheck, Inject, LOCALE_ID, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { Subject, Subscription } from 'rxjs';
import { AuthService, InboxService, ReferenceService } from 'bff-api';
import { DatatableConfig, SelectConfig } from 'ui-master';
import { InboxCommunicationService } from '../../inbox/inbox-communication.service';
import { WebInboxService } from '../../inbox/inbox.service';

@Component({
  selector: 'ui-pool-list',
  templateUrl: './pool-list.component.html',
  styleUrls: ['./pool-list.component.scss'],
  providers: [WebInboxService]
})
export class PoolListComponent implements OnInit, OnDestroy, DoCheck {
  public type: string;
  public isAllSelected: boolean;
  public searchText: string;
  selectedData: any;
  header: DatatableConfig;
  dtData4: any;
  applicationForm: FormGroup;
  filterForm: FormGroup;
  newData: any;
  eventSubmit: Subject<void> = new Subject<void>();
  isNoDataCheck = false;
  selStatusInit: SelectConfig;
  firstLoad = true;
  oldServerRespVal: any;
  claimSubs: Subscription;
  globalSearch: string;


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
    private route: ActivatedRoute

  ) {
    this.applicationForm = this.formBuilder.group({
      appRefNo: null,
      appDate: null,
      applicant: null,
      statusCd: null
    });

    this.webInboxService.applicationType('pool');
  }

  get s() {
    return this.applicationForm.controls;
  }

  ngOnInit() {
    this.tableInitialization();

    this.inboxCommunicationService.filterEmitted$.subscribe(data => {
      this.header.searchForm = data;
      this.header.refresh();
    });
    this.inboxCommunicationService.searchEmitted$.subscribe(data => {
      this.globalSearch = data || '';
      this.header.refresh();
    });
    this.claimSubs = this.inboxCommunicationService.claimEmitted$.subscribe(
      data => {
        this.claim();
        this.header.reload();
      }
    );
    this.inboxCommunicationService.refreshEmitted$.subscribe(data => {
      this.header.reload();
    });
  }

  ngOnDestroy() {
    if (this.claimSubs) {
      this.claimSubs.unsubscribe();
    }
  }
  ngDoCheck() {
    if (this.firstLoad) {
      this.firstLoad = !this.firstLoad;
      if (this.header) {
        this.oldServerRespVal = this.header.serverSideResp;
        if (this.header.serverSideResp) {
          this.inboxCommunicationService.emitIsNoPoolData(Number(this.header.serverSideResp.recordsTotal) === 0);
        }
      }
    } else {
      if (this.oldServerRespVal !== this.header.serverSideResp) {
        this.oldServerRespVal = this.header.serverSideResp;
        if (this.header.serverSideResp) {
          this.inboxCommunicationService.emitIsNoPoolData(Number(this.header.serverSideResp.recordsTotal) === 0);
        }
      }
    }
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
            this.header.columns[5].columnTemplate.context = { color: row.refStatus.legendColor, title: row.refStatus.statusDesc };
          }
        },
      ],
      textWrap: {
        allowWrap: true,
        mode: 'Both'
      },
      searchForm: this.s,
      selectedData: [],
      data: dtRequest => {
        dtRequest.taskAuthorId = this.authService.currentUserValue.userId;
        dtRequest.globalParam = this.globalSearch || '';
        dtRequest.roles = [this.authService.currentUserValue.userRoleGroup.userGroupCode];
        return this.inbox.getMyPool(dtRequest);
      }
    };
  }

  onSubmit() {
    this.header.searchForm = this.s;
    this.header.refresh();
  }

  claim() {
    this.inbox.claimTasks({
      taskMasterIdList: this.header.selectedData.map(data => data.taskMasterId),
      roles: [this.authService.currentUserValue.userRoleGroup.userGroupCode],
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

}
