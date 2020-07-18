import { Component, OnInit, SimpleChanges } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserGroupRole, UserRole, IdmReferenceService, UserGroup } from 'bff-api';
import { SelectConfig, DatatableConfig, ModalService } from 'ui-master';
import { Router, ActivatedRoute } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { map } from 'rxjs/operators';

@Component({
  selector: 'ui-group-role-update',
  templateUrl: './group-role-update.component.html'
})
export class GroupRoleUpdateComponent implements OnInit {

  userGroupRoleUpdateFrom: FormGroup;
  submitted: boolean = false;
  reset: boolean;
  loading: boolean = false;
  button: string = "Update";
  result: any;
  selGroupCodeInit: SelectConfig;
  selRoleCodeInit: SelectConfig;
  role: UserRole;
  groupCodeDropDown = false;
  roleDropDown = false;
  groupId: any;
  groupRole: UserGroupRole;

  constructor(
    private formBuilder: FormBuilder,
    private idmRefService: IdmReferenceService,
    private modalService: ModalService,
    private logger: NGXLogger,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.userGroupRoleUpdateFrom = this.formBuilder.group({
      userGroupRoleCode: [null, [Validators.required]],
      userRoleCode: [null, [Validators.required]]
    });
   }

   get f() { return this.userGroupRoleUpdateFrom.controls; }

   onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    const uGroupRole = new UserGroupRole
    const uGroup = new UserGroup
    const uRole = new UserRole({roleCode: this.f.userRoleCode.value as string});
    
    uGroup.userGroupCode = this.f.userGroupRoleCode.value;
    uGroupRole.id = this.groupId
    uGroupRole.userGroup = uGroup;
    uGroupRole.role = uRole;
    this.idmRefService.updateUserGroupRole(uGroupRole).subscribe(
      data => {
          this.modalService.success("User Group Role Updated");
          this.router.navigate(['maintenance/groupRole']);
          this.onReset();
      },
      error => {
        this.logger.error(error.error.message);
        this.modalService.error("ERROR.");
      });
  }

  ngOnInit() {
    this.groupId = this.route.snapshot.params.cd;
    this.getGroupRole();
    this.selGroupCodeInitialization();
    this.selRoleCodeInitialization();
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
    this.userGroupRoleUpdateFrom.reset();
  }

  private getGroupRole() {
    this.idmRefService.findGroupRoleById(this.groupId).subscribe(
      data => {
        this.groupRole = data;
        this.f.userGroupRoleCode.setValue(this.groupRole.userGroup.userGroupCode);
        this.f.userRoleCode.setValue(this.groupRole.role.roleCode);
      },
      error => {
        this.logger.error(error);
      }
    );
  }

  cancel() {
    this.router.navigate(['maintenance/groupRole']);
  }

}
