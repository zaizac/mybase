import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from '@appmodule/cmn';
import { ChangepasswordComponent, ForgotpasswordComponent, HomeComponent, LoginComponent, LogoutComponent, UserUpdateComponent } from '@appmodule/idm';
import { InboxInfoComponent } from '@appmodule/mailbox/inbox/inbox-info/inbox-info.component';
import { BlankComponent, FullComponent, PlainComponent } from '@appshared/layouts';
import { ErrorComponent } from '@appshared/page';
import { AuthGuard } from 'bff-api';

const routes: Routes = [
  {
    path: '',
    component: FullComponent,
    canActivateChild: [AuthGuard],
    children: [
      { path: '', redirectTo: 'home', pathMatch: 'full' },
      { path: 'workflow', loadChildren: './module/wfw/wfw.module#WfwModule' },      
      { path: 'auth', loadChildren: './module/idm/idm.module#IdmModule' },      
      { path: 'common', loadChildren: './module/cmn/cmn.module#CmnModule' },      
      { path: 'application', loadChildren: './module/mailbox/mail.module#MailModule' },
      { path: 'register', loadChildren: './module/cmn/register/registration.module#RegistrationModule' },
      { path: 'home', component: HomeComponent },
      { path: 'changePassword', component: ChangepasswordComponent, data: { breadcrumb: 'Change Password' } },
      { path: 'firstTimeLogin', component: ChangepasswordComponent, data: { breadcrumb: 'First Time Login' } },
      { path: 'profile', component: UserUpdateComponent, data: { breadcrumb: 'My Profile', type: 'PROFILE' } },
      { path: 'inbox/wfw/redirect', component: InboxInfoComponent },
    ]
  },
  {
    path: '',
    component: BlankComponent,
    children: [
      {
        path: '',
        component: PlainComponent,
        children: [
          { path: 'signup', component: SignupComponent },
          { path: 'forgotPassword', component: ForgotpasswordComponent },
          { path: 'login', component: LoginComponent },
          { path: 'logout', component: LogoutComponent }
        ]
      }
    ]
  },
  {
    path: '**',
    data: { breadcrumb: 'Error' },
    component: ErrorComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
