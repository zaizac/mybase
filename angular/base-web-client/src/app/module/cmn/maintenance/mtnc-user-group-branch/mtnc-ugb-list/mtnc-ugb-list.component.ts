import { Component, OnInit, SimpleChanges } from '@angular/core';
import { IdmReferenceService, UserGroupBranch, UserType, ReferenceService, City, State, Country } from 'bff-api';
import { NGXLogger } from 'ngx-logger';
import { ModalService, DatatableConfig, SelectConfig } from 'ui-master';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'ui-mtnc-ugb-list',
  templateUrl: './mtnc-ugb-list.component.html'
})
export class MtncUgbListComponent implements OnInit {

  mtncUGBForm: FormGroup;
  noDataConfig: DatatableConfig;
  ugbList: UserGroupBranch[] = new Array();
  ugbInitList: UserGroupBranch[] = new Array();
  selUserTypeCodeInit: SelectConfig;
  selCityCdInit: SelectConfig;
  selStateCdInit: SelectConfig;
  selCntryCdInit: SelectConfig;
  submitted = false;
  reset: boolean;
  loading = false;
  button = 'Create';
  userTypeCodeDropDown = false;
  cityCdDropDown = false;
  stateCdDropDown = false;
  cntryCdDropDown = false;

  constructor(private idmReferenceService: IdmReferenceService,
              private referenceService: ReferenceService,
              private router: Router,
              private logger: NGXLogger,
              public modalSvc: ModalService) { }

  ngOnInit() {
    this.idmReferenceService.retrieveUserGroupBranch()
      .subscribe(data => {
        // copy original reference
        this.ugbInitList = data;
        // clone value into new reference
        if (data) {
          // reassign value - maintain ugbList reference
          data.forEach(element => {
            this.ugbList.push(element);
          });
        }
        this.noDataConfig.redraw();
      });

    this.noDataConfig = {
      columns: [
        { field: 'SlNo', headerText: 'No.', width: '6%' },
        { field: 'branchId', headerText: 'Branch Id', width: '9%' },
        { field: 'userType.userTypeCode', headerText: 'User Type Code' },
        { field: 'userGroupCode', headerText: 'User Group Code' },
        { field: 'branchCode', headerText: 'Branch Code' },
        { field: 'branchName', headerText: 'Branch Name', width: '20%' },
        { field: 'branchDept', headerText: 'Branch Dept' },
        { field: 'addr1', headerText: 'Address 1', visible: false },
        { field: 'addr2', headerText: 'Address 2', visible: false },
        { field: 'addr3', headerText: 'Address 3', visible: false },
        { field: 'addr4', headerText: 'Address 4', visible: false },
        { field: 'addr5', headerText: 'Address 5', visible: false },
        { field: 'cityCd', headerText: 'City Code', visible: false },
        { field: 'stateCd', headerText: 'State Code', visible: false },
        { field: 'cntryCd', headerText: 'Country Code', visible: false },
        { field: 'zipcode', headerText: 'Zip Code', visible: false },
        { field: 'email', headerText: 'Email', visible: false },
        { field: 'faxNo', headerText: 'Fax No', visible: false },
        { field: 'contactNo', headerText: 'Contact No', visible: false },
        { field: 'status', headerText: 'Status' },
        {
          field: '', headerText: 'Action', customAttributes: {
            'no-row-click': true
          },
          columnTemplate: {
            id: 'actionDelete',
          },
          render: (data, cell, row) => {
            this.noDataConfig.columns[20].columnTemplate.context = {
              row
            };
          }
        }
      ]
    };

    this.mtncUGBForm = new FormGroup({
      userTypeCode: new FormControl(),
      userGroupCode: new FormControl(),
      branchCode: new FormControl(),
      branchName: new FormControl(),
      branchDept: new FormControl(),
      status: new FormControl()
    });

    // initialise drop-down select
    this.selUserTypeCodeInitialization();
    this.selCityCodeInitialization();
    this.selStateCodeInitialization();
    this.selCountryCodeInitialization();
  }


  // Form Search
  onSubmit() {
    // Assigning values to datatable for searching
    this.submitted = true;
    this.loading = true;
    this.reset = false;
    // reset array ugbList - maintain reference
    this.ugbList.length = 0;
    this.noDataConfig.redraw();

    const ugbToSearch = new UserGroupBranch({
      userType: this.mtncUGBForm.value.userTypeCode ?
                new UserType({ userTypeCode: this.mtncUGBForm.value.userTypeCode as string }) :
                null,
      userGroupCode: this.mtncUGBForm.value.userGroupCode,
      branchCode: this.mtncUGBForm.value.branchCode,
      branchName: this.mtncUGBForm.value.branchName,
      branchDept: this.mtncUGBForm.value.branchDept,
      status: this.mtncUGBForm.value.status
    });

    // Triggers the datatable search
    this.idmReferenceService.searchUserGroupBranch(ugbToSearch)
    .subscribe(data => {
      if (data) {
        // reassign value - maintain ugbList reference
        data.forEach(element => {
          this.ugbList.push(element);
        });
      }
      this.noDataConfig.redraw();
    });

  }

  // reset the form data
  onReset() {
    this.reset = true;
    this.submitted = false;
    // reset ugbList - maintain reference
    this.ugbList.length = 0;
    // reinitialize ugbList - maintain reference
    this.ugbInitList.forEach(element => {
      this.ugbList.push(element);
    });
    this.noDataConfig.redraw();
    this.mtncUGBForm.reset();
  }


  // Datatable row selected event
  rowSelected($event) {
    const selectedFld = $event;
    this.router.navigate(['/maintenance/userGroupBranch/update', selectedFld.branchId]);
  }

  // Datatable delete row event
  deleteRow(selectedRow: any) {
    this.logger.debug('delete Row', selectedRow);
    this.modalSvc.confirm('Do you want to delete this Branch Code: ' + selectedRow.branchCode + '?').then(confirm => {
      if (confirm) {
        this.idmReferenceService.deleteUserGroupBranch(selectedRow).subscribe(res => {
          if (res) {
            this.logger.debug('Deleting ', selectedRow);
            this.ugbList.splice(this.ugbList.indexOf(selectedRow), 1);
            this.ugbInitList.splice(this.ugbList.indexOf(selectedRow), 1);
            this.noDataConfig.redraw();
          }
        });
      }
    });
  }

  private selUserTypeCodeInitialization() {
    this.selUserTypeCodeInit = {
      bindLabel: 'userTypeCode',
      bindValue: 'userTypeCode',
      searchable: true,
      data: () => {
        return this.idmReferenceService.retrieveUserTypes().pipe(map((res: UserType[]) => {
          this.userTypeCodeDropDown = res.constructor.length > 0;
          return res;
        }));
      }
    };
  }

  private selCityCodeInitialization() {
    this.selCityCdInit = {
      bindLabel: 'cityCd',
      bindValue: 'cityCd',
      searchable: true,
      data: () => {
        return this.referenceService.retrieveCities().pipe(map((res: City[]) => {
          this.cityCdDropDown = res.constructor.length > 0;
          return res;
        }));
      }
    };
  }

  private selStateCodeInitialization() {
    this.selStateCdInit = {
      bindLabel: 'stateCd',
      bindValue: 'stateCd',
      searchable: true,
      data: () => {
        return this.referenceService.retrieveStates().pipe(map((res: State[]) => {
          this.stateCdDropDown = res.constructor.length > 0;
          return res;
        }));
      }
    };
  }

  private selCountryCodeInitialization() {
    this.selCntryCdInit = {
      bindLabel: 'cntryCd',
      bindValue: 'cntryCd',
      searchable: true,
      data: () => {
        return this.referenceService.retrieveCountries().pipe(map((res: Country[]) => {
          this.cntryCdDropDown = res.constructor.length > 0;
          return res;
        }));
      }
    };
  }

}
