import { Component, OnInit, SimpleChanges } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserGroupRole, UserRole, IdmReferenceService, UserGroup } from 'bff-api';
import { SelectConfig, DatatableConfig, ModalService } from 'ui-master';
import { Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { map } from 'rxjs/operators';

@Component({
  selector: 'ui-group-role',
  templateUrl: './group-role.component.html'
})
export class GroupRoleComponent implements OnInit {

  userGroupRoleFrom: FormGroup;
  submitted: boolean = false;
  reset: boolean;
  loading: boolean = false;
  button: string = "Create";
  userGroupRoleDT: UserGroupRole[];
  result: any;
  selGroupCodeInit: SelectConfig;
  selRoleCodeInit: SelectConfig;
  noDataConfig: DatatableConfig;
  role: UserRole;
  groupCodeDropDown = false;
  roleDropDown = false;
  selectedCode: any;

  constructor(
    private formBuilder: FormBuilder,
    private idmRefService: IdmReferenceService,
    private modalService: ModalService,
    private router: Router,
    private logger: NGXLogger
  ) { 
    this.userGroupRoleFrom = this.formBuilder.group({
      userGroupRoleCode: [null, [Validators.required]],
      userRoleCode: [null, [Validators.required]]
    });

    this.noDataConfig = {
      columns: [
        { field: 'SlNo', headerText: 'No.', width: '10%' },
        { field: 'userGroup.userGroupCode', headerText: 'User Group Role Code', width: '30%' },
        { field: 'role.roleCode', headerText: 'Role' },
        { field: '', headerText: 'Action', customAttributes: {
            'no-row-click': true
          },
          columnTemplate: {
            id: 'actionDelete',
          },
          render: (data, cell, row) => {
            this.noDataConfig.columns[3].columnTemplate.context = {
              row
            }
          }
        }
      ]
    };
  }

  get f() { return this.userGroupRoleFrom.controls; }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    const uGroupRole = new UserGroupRole
    const uGroup = new UserGroup
    const uRole = new UserRole({roleCode: this.f.userRoleCode.value as string});
    uGroup.userGroupCode = this.f.userGroupRoleCode.value;
    uGroupRole.userGroup = uGroup;
    uGroupRole.role = uRole;
    this.idmRefService.createUserGroupRole(uGroupRole).subscribe(
      data => {
          this.modalService.success("User Group Role Created");
          this.noDataConfig.redraw();
          this.onReset();
      },
      error => {
        this.logger.error(error.error.message);
        this.modalService.error("ERROR.");
      });
  }

  ngOnInit() {
    this.idmRefService.retrieveUserGroupRoles()
    .subscribe(
      data => { 
        this.userGroupRoleDT = data;
      })
    this.selGroupCodeInitialization();
    this.selRoleCodeInitialization();
  }

  ngOnChanges(changes: SimpleChanges) {
    this.logger.debug(changes);
    if (changes.userGroupRoleDT) {
      this.noDataConfig.redraw();
    }
  }

  private selGroupCodeInitialization() {
    this.selGroupCodeInit = {
      bindLabel: "userGroupCode",
      bindValue: "userGroupCode",
      searchable: true,
      data: () => {
        this.result = this.idmRefService.findAllUserGroupList().pipe(map((res: UserGroup[]) => {
          this.groupCodeDropDown = res.constructor.length > 0;
          return res;
        }))
        return this.result;
      }
    }
  }

  private selRoleCodeInitialization() {
    this.selRoleCodeInit = {
      bindLabel: "roleCode",
      bindValue: "roleCode",
      searchable: true,
      data: () => {
        this.result = this.idmRefService.retrieveUserRoles().pipe(map((res: UserRole[]) => {
          this.roleDropDown = res.constructor.length > 0;
          return res;
        }))
        return this.result;
      }
    }
  }

  onReset() {
    this.reset = true;
    this.submitted = false;
    this.noDataConfig.redraw();
    this.userGroupRoleFrom.reset();
  }

  cancel() {
    this.router.navigate(['maintenance/groupRole']);
  }

  delete(selectedRow: any) {
    this.modalService.confirm('Do you want to delete the User Group Role: ' + selectedRow.userGroup.userGroupCode + '?').then(confirm => {
      if (confirm) {
        this.idmRefService.deleteUserGroupRole(selectedRow).subscribe( res => {
          if (res) {
            this.logger.debug('Deleting ', selectedRow);
            this.userGroupRoleDT.splice(this.userGroupRoleDT.indexOf(selectedRow), 1);
            this.noDataConfig.redraw();
          }
        });
      }
    });
  }

  rowSelected($event) {
    this.selectedCode = $event;
    this.router.navigate(['maintenance/groupRole/', this.selectedCode.id]);
  }

}
