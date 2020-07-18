import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegistrationModule } from './register/registration.module';
import { RefreshstaticComponent } from './refresh/refreshstatic/refreshstatic.component';
import { BffApiModule } from 'bff-api';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UiMasterModule } from 'ui-master';

export const routes = [
  { path: 'maintenance', loadChildren: './maintenance/maintenance.module#MaintenanceModule' },
  { path: 'refresh', component: RefreshstaticComponent }
];

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    RegistrationModule,
    BffApiModule.forChild(),
    UiMasterModule.forChild(),
    RouterModule.forChild(routes)
  ],
  declarations: [
    RefreshstaticComponent,    
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
],
})
export class CmnModule { }
