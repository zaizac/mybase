import { KeyValue, KeyValuePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { of } from 'rxjs';
import {
  AuthService,
  IdmRoleConstants,
  ReferenceService,
  UserService
} from 'bff-api';
import { DatatableConfig, SelectConfig } from 'ui-master';
import { map } from 'rxjs/operators';

@Component({
  selector: 'ui-user-list',
  templateUrl: './user-list.component.html'
})
export class UserListComponent implements OnInit {
  manageusers: FormGroup;
  userRoleGroupCodeDropDown = false;
  isPortalAdmin: boolean;
  userStatusList: any;
  currentUser = this.authService.currentUserValue;

  submitted: boolean = false;
  reset: boolean = false;
  
  // Datatable
  newData: any;
  tableInit: DatatableConfig;
  selectedFld: any;

  // Select
  selUserRoleGroupInit: SelectConfig;
  selStatusInit: SelectConfig;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private userService: UserService,
    private reference: ReferenceService,
    private logger: NGXLogger,
    private keyValuePipe: KeyValuePipe
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

  // Sort value in ascending order
  valueAscOrder = (
    a: KeyValue<string, string>,
    b: KeyValue<string, string>
  ): number => {
    return a.value.localeCompare(b.value);
  }

  public ngOnInit(): void {
    this.userRoleGroupCodeDropDown = this.authService.hasAnyRole(
      IdmRoleConstants.SYSTEM_ADMIN,
      IdmRoleConstants.DQ_ADMIN,
      IdmRoleConstants.JIM_ADMIN,
      IdmRoleConstants.OSC_ADMIN,
      IdmRoleConstants.RA_ADMIN
    );

    this.isPortalAdmin = this.authService.hasAnyRole(
      IdmRoleConstants.SYSTEM_ADMIN,
      IdmRoleConstants.DQ_ADMIN
    );

    // Select Initialization - User Status
    this.selStatusInitialization();

    // Select Initialization - User Role Group
    this.selUserRoleGroupInitialization();

    // Table Initialization
    this.tableInitialization();
  }

  // Form Search
  onSubmit() {
    // Assigning values to datatable for searching
    this.tableInit.colSearch = [
      { colIndex: 2, value: this.f.firstName.value },
      { colIndex: 3, value: this.f.lastName.value },
      { colIndex: 4, value: this.f.userRoleGroupCode.value },
      { colIndex: 5, value: this.f.status.value }
    ];
    // Triggers the datatable search
    this.tableInit.refresh();
  }

  // Form Refresh same as demo
  onReset() {
    this.manageusers.reset();

    // Triggers the datatable reset
    this.tableInit.reload();
  }

  // Datatable row selected event
  rowSelected($event) {
    this.selectedFld = $event;
    this.router.navigate(['auth/users/update', this.selectedFld.userId]);
  }

  private tableInitialization() {
    this.tableInit = {
      columns: [
        { field: 'SlNo', headerText: 'No.', allowSorting: false, width: 10 },
        { field: 'userId', headerText: 'User Name', width: 20 },
        {
          field: 'firstName',
          headerText: 'First Name',
          width: 30
        },
        {
          field: 'lastName',
          headerText: 'Last Name',
          width: 30
        },
        {
          field: 'userRoleGroup.userGroupCode',
          headerText: 'User Role Group',
          allowSorting: false,
          width: 20,
          render: (data, cell, row) => {
            if (row.userRoleGroup && row.userRoleGroup.userGroupDesc) {
              cell.textContent = row.userRoleGroup.userGroupDesc.toUpperCase();
            }
          }
        },
        {
          field: 'status',
          headerText: 'Status',
          allowSorting: false,
          width: 20,
          render: (data, cell, row) => {
            cell.textContent = this.userStatusList.get(data);
          }
        }
      ],
      searchForm: this.f,
      data: dtRequest => {
        
        if (dtRequest.userRoleGroupCode) {
          dtRequest.userRoleGroup = {
            "userGroupCode": dtRequest.userRoleGroupCode
          }          
        }

        // Add filter for the roles logged in user can create
        if(this.currentUser.roles) {
          dtRequest.userRoleGroupCodeList = [];
          for(var role of this.currentUser.roles) {
            dtRequest.userRoleGroupCodeList.push(role.roleCode);
          }      
        }
      
        // Add filter by country, if exists in logged in user
        if(this.currentUser.cntryCd) {
          dtRequest.cntryCd = this.currentUser.cntryCd;
        }

        // Add filter by role branch, if exists in logged in user
        if(this.currentUser.userGroupRoleBranchCode) {
          dtRequest.userGroupRoleBranchCode = this.currentUser.userGroupRoleBranchCode;
        }        

        // Add filter by profId, if exists in logged in user
        if(this.currentUser.profId) {
          dtRequest.profId = this.currentUser.profId;
        }

        // Add filter by branchId, if exists in logged in user
        if(this.currentUser.branchId) {
          dtRequest.branchId = this.currentUser.branchId;
        }
        return this.userService.searchPaginated(dtRequest, this.isPortalAdmin);
      }
    };
  }

  private selUserRoleGroupInitialization() {
    this.selUserRoleGroupInit = {
      bindLabel: 'userGroupDesc',
      bindValue: 'userGroupCode',
      searchable: true,
      data: () => {
        if (this.userRoleGroupCodeDropDown) {
          return this.userService.getUserRoleGroups(
            this.currentUser.userType.userTypeCode,
            this.currentUser.userRoleGroup.userGroupCode,
            false, false
          );
        }
        return of([]);
      }
    };
  }

  private selStatusInitialization() {
    this.selStatusInit = {
      bindLabel: 'value',
      bindValue: 'key',
      searchable: true,
      data: () => {
        return this.reference.userStatusMap().pipe(map(data => {
          this.userStatusList = data;
          return this.keyValuePipe.transform(this.userStatusList);
        }))
      }
    };
  }
}
