import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { InboxInfoComponent } from '@appmodule/mailbox/inbox/inbox-info/inbox-info.component';
import { SessionInteruptService } from 'session-expiration-alert';
import { BffApiModule, AuthTypeConstants, IdmMenuCodeConstants } from 'bff-api';
import { UiMasterModule } from 'ui-master';
import { SignupComponent } from '../cmn/signup/signup.component';
import { ChangepasswordComponent } from './changepassword/changepassword.component';
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { AppSessionInteruptService } from './session/session.interupt.service';
import { UserCreateComponent, UserListComponent, UserUpdateComponent } from './user';

export const routes = [
  {
    path: 'users',
    component: UserListComponent,
    data: {
      authorization: {
        roletype: AuthTypeConstants.AUTHENTICATED,
        menuCode: IdmMenuCodeConstants.USER_MGT
      },
      breadcrumb: 'Manage Users'
    }
  },
  {
    path: 'users',
    data: {
      authorization: {
        roletype: AuthTypeConstants.AUTHENTICATED,
        menuCode: IdmMenuCodeConstants.USER_MGT
      },
      breadcrumb: 'Manage Users'
    },
    children: [
      {
        path: 'create',
        component: UserCreateComponent,
        data: { breadcrumb: 'Create User' }
      },
      {
        path: 'update/:id',
        component: UserUpdateComponent,
        data: { breadcrumb: 'Update User' }
      }
    ]
  },
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
        ChangepasswordComponent,
        ForgotpasswordComponent,
        LoginComponent,
        LogoutComponent,
        SignupComponent,
        HomeComponent,
        UserListComponent,
        UserUpdateComponent,
        UserCreateComponent,
        InboxInfoComponent
    ],
    exports: [
        ChangepasswordComponent,
        ForgotpasswordComponent,
        LoginComponent,
        LogoutComponent,
        SignupComponent,
        HomeComponent,
        UserListComponent,
        UserUpdateComponent,
        UserCreateComponent,
        InboxInfoComponent
    ],
    schemas: [
        CUSTOM_ELEMENTS_SCHEMA
    ],
    providers: [{
        provide: SessionInteruptService,
        useClass: AppSessionInteruptService
    }]
})
export class IdmModule { }
