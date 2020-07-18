import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UiMasterModule } from 'projects/ui-master/src/public-api';
import { WizardFormDataService } from 'projects/ui-master/src/lib/custom/wizard/services/wizard-form-data.service';
import { WizardWorkflowService } from 'projects/ui-master/src/lib/custom/wizard/services/wizard-workflow.service';
import { environment } from '../../../../environments/environment';
import { FormWizardRoutingModule } from './form-wizard-routing.module';
import { StepOneComponent } from './step-one/step-one.component';
import { StepTwoComponent } from './step-two/step-two.component';
import { StepThreeComponent } from './step-three/step-three.component';
import { StepFourComponent } from './step-four/step-four.component';
import { StepFiveComponent } from './step-five/step-five.component';
import { WizardWorkflowGuardService } from 'projects/ui-master/src/lib/custom/wizard/services/wizard-workflow-guard.service';
import { WizardComponent } from 'projects/ui-master/src/lib/custom/wizard/public_api';

@NgModule({
  imports: [CommonModule, FormsModule, ReactiveFormsModule, FormWizardRoutingModule, UiMasterModule.forChild()],
  providers: [
    { provide: WizardFormDataService, useClass: WizardFormDataService },
    { provide: WizardWorkflowService, useClass: WizardWorkflowService },
    { provide: WizardWorkflowGuardService, useClass: WizardWorkflowGuardService }
  ],
  declarations: [StepOneComponent, StepTwoComponent, StepThreeComponent, StepFourComponent, StepFiveComponent],
  bootstrap: [WizardComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FormWizardModule { }
