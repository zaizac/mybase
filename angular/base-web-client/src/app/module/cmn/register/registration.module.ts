import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UiMasterModule, WizardFormDataService, WizardWorkflowService, WizardWorkflowGuardService, WizardComponent } from 'ui-master';
import { RegistrationRoutingModule } from './registration-routing.module';
import { RegConfirmComponent } from './reg-confirm/reg-confirm.component';
import { RegDetailsComponent } from './reg-details/reg-details.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RegistrationRoutingModule,
    UiMasterModule.forChild()
  ],
  providers: [
    { provide: WizardFormDataService, useClass: WizardFormDataService },
    { provide: WizardWorkflowService, useClass: WizardWorkflowService },
    { provide: WizardWorkflowGuardService, useClass: WizardWorkflowGuardService }
  ],
  declarations: [RegDetailsComponent, RegConfirmComponent],
  bootstrap: [WizardComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RegistrationModule { }
