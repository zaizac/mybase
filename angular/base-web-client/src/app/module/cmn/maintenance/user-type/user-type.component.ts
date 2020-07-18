import { Component, OnInit, SimpleChanges } from '@angular/core';
import { DatatableConfig, ModalService } from 'ui-master';
import { Router } from '@angular/router';
import { AuthService, IdmRoleConstants, IdmReferenceService, UserType, ReferenceService } from 'bff-api';
import { FormGroup, FormBuilder } from '@angular/forms';
import { NGXLogger } from 'ngx-logger';

@Component({
  selector: 'ui-user-type',
  templateUrl: './user-type.component.html',
  styleUrls: ['./user-type.component.scss']
})
export class UserTypeComponent implements OnInit {
  manageusers: FormGroup;
  tableInit: DatatableConfig;
  selectedFld: any;
  currentUser = this.authService.currentUserValue;
  isPortalAdmin: boolean;
  userType: UserType[];
  dtConfig: DatatableConfig;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private idmService: IdmReferenceService,
    private reference: ReferenceService,
    private logger: NGXLogger,
    private modalService: ModalService,
  ) {
    this.manageusers = this.formBuilder.group({
      firstName: null,
      lastName: null,
      userRoleGroupCode: null,
      status: null
    });
  }

  get f() {
    return this.manageusers.controls;
  }

  ngOnInit() {

    this.isPortalAdmin = this.authService.hasAnyRole(
      IdmRoleConstants.SYSTEM_ADMIN,
      IdmRoleConstants.DQ_ADMIN
    );

    // Table Initialization
    this.tableInitialization();
  }

  // Datatable row selected event
  rowSelected($event) {
    this.selectedFld = $event;
    this.router.navigate(['maintenance/userType/', this.selectedFld.userTypeCode]);
  }

  private tableInitialization() {
    this.tableInit = {
      columns: [
        { field: 'SlNo', headerText: 'No.', allowSorting: false, width: 10 },
        { field: 'userTypeCode', headerText: 'User Type Code', width: 20 },
        { field: 'userTypeDesc.en', headerText: 'Code Description', width: 30 },
        { field: 'emailAsUserId', headerText: 'User email', width: 30 },
        {
          field: '', headerText: 'Action', width: 10, customAttributes: {
            'no-row-click': true
          },
          columnTemplate: {
            id: 'actionDelete',
          },
          render: (data, cell, row) => {
            this.tableInit.columns[4].columnTemplate.context = {
              row
            };
          }
        }
      ],
      //searchForm: this.f,
      data: dtRequest => {
        return this.idmService.searchPaginated(dtRequest);
      }
    };
  }

  ngOnChanges(changes: SimpleChanges) {
    this.logger.debug(changes);
    if (changes.userType) {
      this.tableInit.reload();
    }
  }

  deleteRow(selectedRow: any) {
    this.modalService.confirm('Do you want to delete User Type: ' + selectedRow.menuCode + '?').then(confirm => {
      if (confirm) {
        this.idmService.deleteUserType(selectedRow).subscribe(res => {
          if (res) {
            this.logger.debug('Deleting ', selectedRow);
            //this.userType.slice(this.userType.indexOf(selectedRow), 1);
            this.tableInit.reload();
          }
        });
      }
    });
  }
}
