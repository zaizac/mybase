import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { WizardComponent } from 'projects/ui-master/src/lib/custom/wizard/public_api';
import { WizardWorkflowGuardService } from 'projects/ui-master/src/lib/custom/wizard/services/wizard-workflow-guard.service';
import { StepFourComponent } from './step-four/step-four.component';
import { StepOneComponent } from './step-one/step-one.component';
import { StepThreeComponent } from './step-three/step-three.component';
import { StepTwoComponent } from './step-two/step-two.component';
import { StepFiveComponent } from './step-five/step-five.component';

const formWizardRoutes: Routes = [
  {
    path: '',
    component: WizardComponent,
    data: {
      breadcrumb: 'Form Wizard'
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
          isView: true,
          pageName: 'stepOne',
          button: {
            next: {}
          }
        }
      },
      {
        path: 'stepTwo',
        component: StepTwoComponent,
        data: {
          stepper: {
            icon: 'fa fa-tasks',
            label: 'My Task'
          },
          formName: 'stepTwo'
        },
        canActivate: [WizardWorkflowGuardService]
      },
      {
        path: 'stepThree',
        component: StepThreeComponent,
        data: {
          title: 'StepThree Form',
          stepper: {
            icon: 'fa fa-address-card',
            label: 'My Address'
          },
          pageName: 'stepThree',
          button: {
            next: {
              text: 'Next',
              icon: 'fa fa-chevron-right'
            },
            previous: {
              text: 'Previous',
              icon: 'fa fa-chevron-left'
            }
          },
          hide: true
        },
        canActivate: [WizardWorkflowGuardService]
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
            next: {
              text: 'Confirm',
              icon: 'fa fa-check-circle'
            },
            previous: {
              text: 'Previous',
              icon: 'fa fa-chevron-left'
            }
          },
          hide: true
        },
        canActivate: [WizardWorkflowGuardService]
      },
      {
        path: 'stepFive',
        component: StepFiveComponent,
        data: {
          title: 'StepFive Form',
          stepper: {
            icon: 'fa fa-power-off',
            label: 'Confirmation Extra1'
          },
          pageName: 'stepFive',
          button: {
            next: {
              text: 'Confirm',
              icon: 'fa fa-check-circle'
            },
            previous: {
              text: 'Previous',
              icon: 'fa fa-chevron-left'
            }
          },
          hide: false
        }
      },
      {
        path: 'stepSix',
        component: StepFiveComponent,
        data: {
          title: 'stepSix Form',
          stepper: {
            icon: 'fa fa-power-off',
            label: 'Confirmation Extra2'
          },
          pageName: 'stepSix',
          button: {
            next: {
              text: 'Confirm',
              icon: 'fa fa-check-circle'
            },
            previous: {
              text: 'Previous',
              icon: 'fa fa-chevron-left'
            }
          },
          hide: false
        }
      },
      {
        path: 'stepSeven',
        component: StepFiveComponent,
        data: {
          title: 'stepSeven Form Long Title',
          stepper: {
            icon: 'fa fa-power-off',
            label: 'Confirmation Extra3 Long Title'
          },
          pageName: 'stepSeven',
          button: {
            next: {
              text: 'Confirm',
              icon: 'fa fa-check-circle'
            },
            previous: {
              text: 'Previous',
              icon: 'fa fa-chevron-left'
            }
          },
          hide: false
        }
      },
      {
        path: 'stepEight',
        component: StepFiveComponent,
        data: {
          title: 'stepEight Form Long Title',
          stepper: {
            icon: 'fa fa-power-off',
            label: 'Confirmation Extra4 Long Title'
          },
          pageName: 'stepEight',
          button: {
            next: {
              text: 'Confirm',
              icon: 'fa fa-check-circle'
            },
            previous: {
              text: 'Previous',
              icon: 'fa fa-chevron-left'
            }
          },
          hide: false
        }
      },
      {
        path: 'stepNine',
        component: StepFiveComponent,
        data: {
          title: 'stepNine Form Long Title',
          stepper: {
            icon: 'fa fa-power-off',
            label: 'Confirmation Extra5 Long Title'
          },
          pageName: 'stepNine',
          button: {
            next: {
              text: 'Confirm',
              icon: 'fa fa-check-circle'
            },
            previous: {
              text: 'Previous',
              icon: 'fa fa-chevron-left'
            }
          },
          hide: false
        }
      },
      {
        path: 'stepNine',
        component: StepFiveComponent,
        data: {
          title: 'stepNine Form Long Title',
          stepper: {
            icon: 'fa fa-power-off',
            label: 'Confirmation Extra6 Long Title'
          },
          pageName: 'stepNine',
          button: {
            next: {
              text: 'Confirm',
              icon: 'fa fa-check-circle'
            },
            previous: {
              text: 'Previous',
              icon: 'fa fa-chevron-left'
            }
          },
          hide: false
        }
      },
      {
        path: 'stepTen',
        component: StepFiveComponent,
        data: {
          title: 'stepTen Form Long Title',
          stepper: {
            icon: 'fa fa-power-off',
            label: 'Confirmation Extra7 Long Title'
          },
          pageName: 'stepTen',
          button: {
            next: {
              text: 'Confirm',
              icon: 'fa fa-check-circle'
            },
            previous: {
              text: 'Previous',
              icon: 'fa fa-chevron-left'
            }
          },
          hide: false
        }
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
