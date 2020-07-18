import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { DatatableConfig, SelectConfig, ModalService } from 'ui-master';
import { UserRole, IdmReferenceService, PortalType, ReferenceService, LangDesc } from 'bff-api';
import { NGXLogger } from 'ngx-logger';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'ui-mtnc-role-list',
  templateUrl: './mtnc-role-list.component.html'
})
export class MtncRoleListComponent implements OnInit {

  mtncRoleForm: FormGroup;
  noDataConfig: DatatableConfig;
  roleList: UserRole[] = new Array();
  roleInitList: UserRole[] = new Array();
  selPortalTypeCodeInit: SelectConfig;
  submitted = false;
  reset: boolean;
  loading = false;
  buttonCreateUpdate = 'Create';
  portalTypeCodeDropDown = false;

  constructor(private idmReferenceService: IdmReferenceService,
              private referenceService: ReferenceService,
              private logger: NGXLogger,
              private router: Router,
              public modalSvc: ModalService) { }

  ngOnInit() {
    this.idmReferenceService.retrieveUserRolesIncludePortalType()
      .subscribe(data => {
        // copy original reference
        this.roleInitList = data;
        // clone value into new reference
        if (data) {
          // reassign value - maintain roleList reference
          data.forEach(element => {
            this.roleList.push(element);
          });
        }
        this.noDataConfig.redraw();
      });

    this.noDataConfig = {
      columns: [
        { field: 'SlNo', headerText: 'No.', width: '10%' },
        { field: 'roleCode', headerText: 'Role Code', width: '20%' },
        { field: 'roleDesc.en', headerText: 'Role Description', width: '40%' },
        { field: 'portalType.portalTypeCode', headerText: 'Portal Type Code' },
        { field: '', headerText: 'Action', customAttributes: {
            'no-row-click': true
          },
          columnTemplate: {
            id: 'actionDelete',
          },
          render: (data, cell, row) => {
            this.noDataConfig.columns[4].columnTemplate.context = {
              row
            };
          }
        }
      ]
    };

    this.mtncRoleForm = new FormGroup({
       roleCode: new FormControl(),
       roleDesc: new FormControl(),
       portalTypeCode: new FormControl()
    });

    this.selPortalTypeCodeInitialization();
  }

  get f() { return this.mtncRoleForm.controls; }


  // Form Search
  onSubmit() {
    // Assigning values to datatable for searching
    this.submitted = true;
    this.loading = true;
    this.reset = false;
    // reset array roleList - maintain reference
    this.roleList.length = 0;
    this.noDataConfig.redraw();

    const roleToSearch = new UserRole({
      roleCode: this.mtncRoleForm.value.roleCode as string,
      roleDesc: this.mtncRoleForm.value.roleDesc ?
                  new LangDesc({en: this.mtncRoleForm.value.roleDesc as string}) :
                  null,
      portalType: this.mtncRoleForm.value.portalTypeCode ?
                  new PortalType ({portalTypeCode: this.mtncRoleForm.value.portalTypeCode as string}) :
                  null
    });

    // Triggers the datatable search
    this.idmReferenceService.searchUserRole(roleToSearch)
    .subscribe(data => {
      if (data) {
        // reassign value - maintain roleList reference
        data.forEach(element => {
          this.roleList.push(element);
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
    this.roleList.length = 0;
    // reinitialize ugbList - maintain reference
    this.roleInitList.forEach(element => {
      this.roleList.push(element);
    });
    this.noDataConfig.redraw();
    this.mtncRoleForm.reset();
  }


  // Datatable row selected event
  rowSelected($event) {
    const selectedFld = $event;
    this.router.navigate(['/maintenance/role/update', selectedFld.roleCode]);
  }

  // Datatable delete row event
  deleteRow(selectedRow: any) {
    this.logger.debug('delete Row', selectedRow);
    this.modalSvc.confirm('Do you want to delete this Role Code: ' + selectedRow.roleCode + '?').then(confirm => {
      if (confirm) {
        this.idmReferenceService.deleteUserRole(selectedRow).subscribe(res => {
          if (res) {
            this.logger.debug('Deleting ', selectedRow);
            this.roleList.splice(this.roleList.indexOf(selectedRow), 1);
            this.roleInitList.splice(this.roleList.indexOf(selectedRow), 1);
            this.noDataConfig.redraw();
          }
        });
      }
    });
  }

  private selPortalTypeCodeInitialization() {
    this.selPortalTypeCodeInit = {
      bindLabel: 'portalTypeCode',
      bindValue: 'portalTypeCode',
      searchable: true,
      data: () => {
        return this.referenceService.retrievePortalTypeAll().pipe(map((res: PortalType[]) => {
          this.portalTypeCodeDropDown = res.constructor.length > 0;
          return res;
        }));
      }
    };
  }

}
