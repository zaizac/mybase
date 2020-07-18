import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MtncOauthClientDetailsComponent } from '@appmodule/cmn/maintenance/mtnc-oauth-client-details/mtnc-oauth-client-details.component';
import { UserConfigUpdateComponent } from '@appmodule/cmn/maintenance/user-config/user-config-update/user-config-update.component';
import { UserConfigComponent } from '@appmodule/cmn/maintenance/user-config/user-config.component';
import { MtncUgbUpdateComponent, MtncUgbCreateComponent, MtncUgbListComponent } from '@appmodule/cmn/maintenance/mtnc-user-group-branch';
import { UserGroupUpdateComponent } from '@appmodule/cmn/maintenance/user-group/user-group-update/user-group-update.component';
import { UserGroupCreateComponent } from '@appmodule/cmn/maintenance/user-group/user-group-create/user-group-create.component';
import { UserGroupComponent } from '@appmodule/cmn/maintenance/user-group/user-group.component';
import { MenuUpdateComponent } from '@appmodule/cmn/maintenance/menu/menu-update/menu-update.component';
import { MenuCreateComponent } from '@appmodule/cmn/maintenance/menu/menu-create/menu-create.component';
import { MenuAddComponent } from '@appmodule/cmn/maintenance/menu/menu-add.component';
import { GroupRoleUpdateComponent } from '@appmodule/cmn/maintenance/group-role/group-role-update/group-role-update.component';
import { GroupRoleComponent } from '@appmodule/cmn/maintenance/group-role/group-role.component';
import { RoleMenuCreateComponent } from '@appmodule/cmn/maintenance/role-menu/role-menu-create/role-menu-create.component';
import { RoleMenuComponent } from '@appmodule/cmn/maintenance/role-menu/role-menu.component';
import { UserTypeUpdateComponent } from '@appmodule/cmn/maintenance/user-type/user-type-update/user-type-update.component';
import { UserTypeComponent } from '@appmodule/cmn/maintenance/user-type/user-type.component';
import { MtncRoleUpdateComponent, MtncRoleCreateComponent, MtncRoleListComponent } from '@appmodule/cmn/maintenance/mtnc-role';
import { AuthTypeConstants, IdmMenuCodeConstants, BffApiModule } from 'bff-api';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MtncPortalTypeComponent } from '@appmodule/cmn/maintenance/mtnc-portal-type/mtnc-portal-type.component';
import { UiMasterModule } from 'ui-master';

export const routes = [
  {
    path: '',
    data: {
      authorization: {
        roletype: AuthTypeConstants.AUTHENTICATED,
        menuCode: IdmMenuCodeConstants.MTNC_MGMT
      },
      breadcrumb: 'Maintenance'
    },
    /** routing sorted alphabetically */
    children: [
      {
        path: 'groupRole',
        children: [
          {
            path: '',
            component: GroupRoleComponent,
            data: { breadcrumb: 'Group Role' }
          },
          {
            path: ':cd',
            component: GroupRoleUpdateComponent,
            data: { breadcrumb: 'Group Role Update' }
          }
        ]
      },
      {
        path: 'oauthClientDetails',
        component: MtncOauthClientDetailsComponent, data: { breadcrumb: 'Oauth Client Details' }
      },
      {
        path: 'portalType',
        component: MtncPortalTypeComponent,
        data: { breadcrumb: 'Portal Type' }
      },
      {
        path: 'role',
        children: [
          {
            path: '',
            component: MtncRoleListComponent,
            data: { breadcrumb: 'Role' }
          },
          {
            path: 'create',
            component: MtncRoleCreateComponent,
            data: { breadcrumb: 'Create Role' }
          },
          {
            path: 'update/:id',
            component: MtncRoleUpdateComponent,
            data: { breadcrumb: 'Update Role' }
          }
        ]
      },
      {
        path: 'roleMenu',
        children: [
          {
            path: '',
            component: RoleMenuComponent,
            data: { breadcrumb: 'Role Menu' }
          },
          {
            path: ':id',
            component: RoleMenuCreateComponent,
            data: { breadcrumb: 'Create Role Menu' }
          }
        ]
      },
      {
        path: 'userType',
        children: [
          {
            path: '',
            component: UserTypeComponent,
            data: { breadcrumb: 'User Type' }
          },
          {
            path: ':cd',
            component: UserTypeUpdateComponent,
            data: { breadcrumb: 'User Type Update' }
          }
        ]
      },
      {
        path: 'userMenu',
        children: [
          {
            path: '',
            component: MenuAddComponent,
            data: { breadcrumb: 'Menu' }
          },
          {
            path: 'create',
            component: MenuCreateComponent,
            data: { breadcrumb: 'Menu Create' }
          },
          {
            path: 'update/:cd',
            component: MenuUpdateComponent,
            data: { breadcrumb: 'Menu Update' }
          }
        ]
      },
      {
        path: 'userGroup',
        children: [
          {
            path: '',
            component: UserGroupComponent,
            data: { breadcrumb: 'User Group' }
          },
          {
            path: 'create',
            component: UserGroupCreateComponent,
            data: { breadcrumb: 'User Group Create' }
          },
          {
            path: 'update/:cd',
            component: UserGroupUpdateComponent,
            data: { breadcrumb: 'User Group Update' }
          }
        ]
      },
      {
        path: 'userGroupBranch',
        children: [
          {
            path: '',
            component: MtncUgbListComponent,
            data: { breadcrumb: 'User Group Branch' }
          },
          {
            path: 'create',
            component: MtncUgbCreateComponent,
            data: { breadcrumb: 'Create User Group Branch' }
          },
          {
            path: 'update/:id',
            component: MtncUgbUpdateComponent,
            data: { breadcrumb: 'Update User Group Branch' }
          }
        ]
      },
      {
        path: 'userConfig',
        children: [
          {
            path: '',
            component: UserConfigComponent,
            data: { breadcrumb: 'User Configuration' }
          },
          {
            path: ':cd',
            component: UserConfigUpdateComponent,
            data: { breadcrumb: 'User Configuration Update' }
          }
        ]
      },
    ]
  }
];

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    BffApiModule.forChild(),
    UiMasterModule.forChild(),
    RouterModule.forChild(routes)
  ],
  declarations: [
    MtncPortalTypeComponent,
    MenuAddComponent,
    UserTypeComponent,
    UserTypeUpdateComponent,
    UserGroupComponent,
    UserConfigComponent,
    UserConfigUpdateComponent,
    RoleMenuComponent,
    RoleMenuCreateComponent,
    GroupRoleComponent,
    UserGroupUpdateComponent,
    MenuUpdateComponent,
    GroupRoleUpdateComponent,
    MenuCreateComponent,
    UserGroupCreateComponent,
    MtncOauthClientDetailsComponent,
    MtncUgbCreateComponent,
    MtncUgbListComponent,
    MtncUgbUpdateComponent,
    MtncRoleCreateComponent,
    MtncRoleListComponent,
    MtncRoleUpdateComponent
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
],
})
export class MaintenanceModule { }