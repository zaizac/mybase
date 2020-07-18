import { Component, OnInit } from '@angular/core';
import { SelectConfig, DatatableConfig, ModalService } from 'ui-master';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthService, IdmReferenceService, IdmRoleConstants, RoleMenu } from 'bff-api';
import { NGXLogger } from 'ngx-logger';
import { Router } from '@angular/router';

@Component({
  selector: 'ui-role-menu',
  templateUrl: './role-menu.component.html',
  styleUrls: ['./role-menu.component.scss']
})
export class RoleMenuComponent implements OnInit {
  roleMenuList: RoleMenu[];
  roleMenu: FormGroup;
  selStatusInit: SelectConfig;
  isPortalAdmin: boolean;
  roleTableInit: DatatableConfig;
  roleMenuId: any;

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private idmService: IdmReferenceService,
    private logger: NGXLogger,
    private modalService: ModalService
  ) { 
    this.roleMenu = this.formBuilder.group({
      //roleMenuId: null,
      idmRole: null,
      idmMenu: null
    });
  }

  get f() {
    return this.roleMenu.controls;
  }

  ngOnInit() {
    this.isPortalAdmin = this.authService.hasAnyRole(
      IdmRoleConstants.SYSTEM_ADMIN,
      IdmRoleConstants.DQ_ADMIN
    );

    // Table Initialization
    this.tableInitialization();
  }

  private tableInitialization() {
    this.roleTableInit = {
      columns: [
        { field: 'SlNo', headerText: 'No.', allowSorting: false, width: 10 },
        //{ field: 'roleMenuId', headerText: 'User Type Code', width: 20 },
        { field: 'role.roleCode', headerText: 'Role Code', width: 40 },
        { field: 'menu.menuCode', headerText: 'Menu Code', width: 40 },
        {
          field: '', headerText: 'Action', width: 10, customAttributes: {
            'no-row-click': true
          },
          columnTemplate: {
            id: 'actionDelete',
          },
          render: (data, cell, row) => {
            this.roleTableInit.columns[3].columnTemplate.context = {
              row
            };
          }
        }
      ],
      searchForm: this.f,
      data: dtRequest => {
        return this.idmService.searchRoleMenuPaginated(dtRequest);
      }
    };
  }

  rowSelected($event) {
    this.roleMenuId = $event;
    this.router.navigate(['maintenance/roleMenu/', this.roleMenuId.roleMenuId]);
  }

  deleteRow(selectedRow: any) {
    this.modalService.confirm('Do you want to delete User Type: ' + selectedRow.role.roleCode + '?').then(confirm => {
      if (confirm) {
        this.idmService.deleteRoleMenu(selectedRow).subscribe(res => {
          if (res) {
            this.logger.debug('Deleting ', selectedRow);
            this.roleTableInit.reload();
          }
        });
      }
    });
  }

}
