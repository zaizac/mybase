import { Component, Inject, LOCALE_ID, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import 'rxjs/add/operator/switchMap';
import { AuthService, InboxService, ReferenceService } from 'bff-api';
import { DatatableConfig, SelectConfig } from 'ui-master';
import { InboxCommunicationService } from '../../inbox/inbox-communication.service';
import { WebInboxService } from '../../inbox/inbox.service';


@Component({
  selector: 'ui-history-list',
  templateUrl: './history-list.component.html',
  styleUrls: ['./history-list.component.scss'],
  providers: [WebInboxService]
})
export class HistoryListComponent implements OnInit {
  public type: string;
  public isAllSelected: boolean;
  public searchText: string;
  selectedData: any;
  header: DatatableConfig;
  dtData4: any;
  searchMailForm: FormGroup;
  filterForm: FormGroup;
  newData: any;
  selStatusInit: SelectConfig;
  globalSearch: string;

  constructor(
    public router: Router,
    public inbox: InboxService,
    private formBuilder: FormBuilder,
    private logger: NGXLogger,
    private authService: AuthService,
    private webInboxService: WebInboxService,
    private inboxCommunicationService: InboxCommunicationService,
    private reference: ReferenceService,
    private route: ActivatedRoute,
    @Inject(LOCALE_ID) private locale: string
  ) {

    this.searchMailForm = this.formBuilder.group({
      refNo: null,
      applDate: null,
      companyName: null
    });

    this.filterForm = this.formBuilder.group({
      appRefNo: null,
      appDate: null,
      applicant: null,
      statusCd: null
    });

    this.webInboxService.applicationType('history');
  }

  get s() {
    return this.searchMailForm.controls;
  }
  get f() {
    return this.filterForm.controls;
  }

  ngOnInit() {
    this.tableInitialization();

    this.inboxCommunicationService.filterEmitted$.subscribe(data => {
      this.header.searchForm = data;
      this.header.refresh();
    });

    this.inboxCommunicationService.searchEmitted$.subscribe(data => {
      this.header.searchForm = data;
      this.header.refresh();
    });

    this.inboxCommunicationService.refreshEmitted$.subscribe(data => {
      this.header.reload();
    });
  }

  private tableInitialization() {
    this.header = {
      columns: [
        {
          type: 'expand',
          textAlign: 'Center',
          columnTemplate: {
            id: 'expandTemplate',
            context: {}
          },
          render: (data, cell, row) => {
            const contextData = row.taskHistoryList;
            const expandHeader = {
              columns: [
                {
                  field: 'SlNo', headerText: 'No.', width: '5%', textAlign: 'Center',
                },
                {
                  field: '',
                  headerText: '',
                  width: '5%',
                  textAlign: 'Center',

                  columnTemplate: {
                    id: 'mychildTemplate',
                    context: {}
                  },
                  render: (data1, cell1, row1) => {
                    data1 = row1.refStatus.statusDesc;
                    expandHeader.columns[1].columnTemplate.context = { color: row.refStatus.legendColor };
                  }
                },
                {
                  field: 'submitBy', headerText: 'Submit by', textAlign: 'Center',
                  width: '25%', render: (data3, cell3, row3) => {
                    cell3.textContent = data3 || '-';
                  }
                },
                {
                  field: 'claimBy', headerText: 'Claim by', textAlign: 'Center',
                  width: '25%', render: (data2, cell2, row2) => {
                    cell2.textContent = data2 || '-';
                  }
                },
                {
                  field: 'action', headerText: 'Action', textAlign: 'Center',
                  width: '20%'
                }
              ],
              textWrap: {
                allowWrap: true,
                mode: 'Both'
              },
            };
            this.header.columns[0].columnTemplate.context = Object.assign({}, {
              header: expandHeader,
              data: contextData
            });
          }
        },
        { field: 'SlNo', headerText: 'No.', width: '10%' },
        { field: 'appRefNo', headerText: 'Ref. No.', width: '20%' },
        { field: 'appDate', headerText: 'Appl. Date', width: '20%' },
        { field: 'applicant', headerText: 'Applicant', width: '25%' },
        { field: 'refType.description', headerText: 'Task Name', width: '25%' },
        {
          field: '',
          headerText: 'Status',
          width: '5%',
          columnTemplate: {
            id: 'myTemplate',
            context: {}
          },
          render: (data, cell, row) => {
            data = row.refStatus.statusCd;
            this.header.columns[5].columnTemplate.context = { legend: row.refStatus.legendColor };
          }
        },
      ],
      textWrap: {
        allowWrap: true,
        mode: 'Both'
      },
      data: dtRequest => {
        dtRequest.taskAuthorId = this.authService.currentUserValue.userId;
        dtRequest.globalParam = this.globalSearch || '';
        dtRequest.roles = [this.authService.currentUserValue.userRoleGroup.userGroupCode];
        return this.inbox.getMasterHistory(dtRequest);
      }
    };
  }

  onSubmit() {
    this.header.searchForm = this.f;
    this.header.refresh();
  }

  handleRowClick(row) {
    if (row.refType.redirectPath) {
      this.router.navigate([row.refType.redirectPath], { state: row });
    }
  }
}
