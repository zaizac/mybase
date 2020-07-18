import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UiMasterModule, WizardFormDataService, WizardWorkflowService } from 'projects/ui-master/src/public-api';
import { environment } from '../../../../environments/environment';
import { FormWizardRoutingModule } from './form-wizard-routing.module';
import { StepFourComponent } from './step-four/step-four.component';
import { StepOneComponent } from './step-one/step-one.component';
import { StepThreeComponent } from './step-three/step-three.component';
import { StepTwoComponent } from './step-two/step-two.component';

@NgModule({
  imports: [CommonModule, FormsModule, ReactiveFormsModule, FormWizardRoutingModule, UiMasterModule.forChild()],
  providers: [
    { provide: WizardFormDataService, useClass: WizardFormDataService },
    { provide: WizardWorkflowService, useClass: WizardWorkflowService }
  ],
  declarations: [StepOneComponent, StepTwoComponent, StepThreeComponent, StepFourComponent],
  bootstrap: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FormWizardVerticalModule { }
