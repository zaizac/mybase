import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WizardComponent } from 'projects/ui-master/src/lib/custom/wizard/public_api';
import { WizardWorkflowGuardService } from 'projects/ui-master/src/public-api';
import { StepFourComponent } from './step-four/step-four.component';
import { StepOneComponent } from './step-one/step-one.component';
import { StepThreeComponent } from './step-three/step-three.component';
import { StepTwoComponent } from './step-two/step-two.component';

const formWizardRoutes: Routes = [
  {
    path: '',
    component: WizardComponent,
    data: {
      breadcrumb: 'Form Wizard',
      title: 'Form-Wizard',
      mode: 'horizontal',
      statColour: {
        active: 'primary',
        previous: 'inactive',
        next: 'secondary',
        current: 'danger'
      }
    },
    children: [
      {
        path: 'stepOne',
        component: StepOneComponent,
        data: {
          title: 'StepOne Form',
          stepper: {
            icon: 'fa fa-users',
            label: 'My Detail'
          },
          formName: 'stepOneTest',
          button: {
            next: {}
          }
        },
        canActivate: [WizardWorkflowGuardService]
      },
      {
        path: 'stepTwo',
        component: StepTwoComponent,
        data: {
          title: 'StepTwo Form',
          stepper: {
            icon: 'fa fa-tasks',
            label: 'My Task'
          },
          formName: 'stepTwo',
          button: {
            next: {
              text: 'Next',
              icon: 'fa fa-chevron-right'
            },
            previous: {
              text: 'Previous',
              icon: 'fa fa-chevron-left'
            }
          }
        }
      },
      {
        path: 'stepThree',
        component: StepThreeComponent,
        data: {
          title: 'StepThree Form',
          stepper: {
            icon: 'fa fa-power-off',
            label: 'My Address'
          },
          formName: 'stepThree',
          button: {
            next: {
              text: 'Confirm',
              icon: 'fa fa-check-circle'
            },
            previous: {
              text: 'Previous',
              icon: 'fa fa-chevron-left'
            }
          }
        }
      },
      {
        path: 'stepFour',
        component: StepFourComponent,
        data: {
          title: 'StepFour Form',
          stepper: {
            icon: 'fa fa-power-off',
            label: 'Confirmation'
          },
          pageName: 'stepFour',
          button: {
            otp: {
              text: 'Confirm',
              icon: 'fa fa-check-circle'
            },
            previous: {
              text: 'Previous',
              icon: 'fa fa-chevron-left'
            }
          }
        },
        canActivate: [WizardWorkflowGuardService]
      },
      { path: '', redirectTo: 'stepOne', pathMatch: 'full' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(formWizardRoutes)],
  exports: [RouterModule]
})
export class FormWizardRoutingModule {}

export const routedComponents = [WizardComponent];
