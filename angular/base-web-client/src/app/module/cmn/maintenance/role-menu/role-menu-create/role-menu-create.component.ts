import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { RoleMenu, IdmReferenceService, UserRole, UserMenu } from 'bff-api';
import { SelectConfig, ModalService } from 'ui-master';
import { NGXLogger } from 'ngx-logger';

@Component({
  selector: 'ui-role-menu-create',
  templateUrl: './role-menu-create.component.html',
  styleUrls: ['./role-menu-create.component.scss']
})
export class RoleMenuCreateComponent implements OnInit {
  submitted = false;
  reset: boolean;
  loading = false;
  roleMenuId: any;
  roleMenu: FormGroup;
  currRoleMenu: RoleMenu;
  selRoleInit: SelectConfig;
  selMenuInit: SelectConfig;
  button: string;
  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private idmService: IdmReferenceService,
    private modalService: ModalService,
    private logger: NGXLogger
  ) {
    this.roleMenu = this.formBuilder.group({
      roleCode: [null, [Validators.required]],
      menuCode: [null, [Validators.required]]
    });
  }

  get f() { return this.roleMenu.controls; }

  ngOnInit() {
    this.roleMenuId = this.route.snapshot.params.id;

    // Select Initialization - Role
    this.selRoleInitialization();

    // Select Initialization - Menu
    this.selMenuInitialization();

    if (this.roleMenuId !== 'NEW') {
      this.getRoleMenu();
      this.button = 'Update';
    } else {
      this.f.roleCode.setValue(null);
      this.f.menuCode.setValue(null);
      this.button = 'Create';
    }

  }
  private selRoleInitialization() {
    this.selRoleInit = {
      bindLabel: 'roleDesc.en',
      bindValue: 'roleCode',
      searchable: true,
      data: () => {
        return this.idmService.retrieveUserRoles();
      }
    };
  }

  private selMenuInitialization() {
    this.selMenuInit = {
      bindLabel: 'menuDesc.en',
      bindValue: 'menuCode',
      searchable: true,
      data: () => {
        return this.idmService.findAllMenuList();
      }
    };
  }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;

    if (this.roleMenuId === "NEW") {
      const rolMen = new RoleMenu();
      const role = new UserRole({ roleCode: this.f.roleCode.value as string });
      const menu = new UserMenu();
  
      menu.menuCode = this.f.menuCode.value;
      rolMen.role = role;
      rolMen.menu = menu;
      this.logger.info('Form Data: ', rolMen);
      this.idmService.createRoleMenu(rolMen)
        .subscribe(
          data => {
            if (data) {
              this.modalService.success('User Role Menu created Successfully');
              this.router.navigate(['maintenance/roleMenu']);
            }
          },
          error => {
            this.logger.error(error.error.message);
            this.modalService.error('Failed to process the transaction. Please contact system administrator.');
          }
        );
    }else{
      const rolMen = new RoleMenu();
      const role = new UserRole({ roleCode: this.f.roleCode.value as string });
      const menu = new UserMenu();
  
      menu.menuCode = this.f.menuCode.value;
      rolMen.roleMenuId = this.roleMenuId;
      rolMen.role = role;
      rolMen.menu = menu;
      this.logger.info('Form Data: ', rolMen);
      this.idmService.updateRoleMenu(rolMen)
        .subscribe(
          data => {
            if (data) {
              this.modalService.success('User Role Menu Update Successfully');
              this.router.navigate(['maintenance/roleMenu']);
            }
          },
          error => {
            this.logger.error(error.error.message);
            this.modalService.error('Failed to process the transaction. Please contact system administrator.');
          }
        );
    }
  }

  private getRoleMenu() {
    this.idmService.findRoleMenuById(this.roleMenuId).subscribe(
      data => {
        this.currRoleMenu = data;
        this.f.roleCode.setValue(this.currRoleMenu.role.roleCode);
        this.f.menuCode.setValue(this.currRoleMenu.menu.menuCode);
      },
      error => {
        this.logger.error(error);
      }
    );
  }
  
  cancel() {
    this.router.navigate(['maintenance/roleMenu']);
  }
}
