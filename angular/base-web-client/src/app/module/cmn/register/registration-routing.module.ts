import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegDetailsComponent } from './reg-details/reg-details.component';
import { RegConfirmComponent } from './reg-confirm/reg-confirm.component';
import { Routes, RouterModule } from '@angular/router';
import { WizardComponent, WizardWorkflowGuardService } from 'ui-master';

const formWizardRoutes: Routes = [
  {
    path: '',
    component: WizardComponent,
    data: {
      breadcrumb: 'Form Wizard',
      title: 'Form-Wizard',
      statColour: {
        previous: 'success'
      }
    },
    children: [
      {
        path: 'details',
        component: RegDetailsComponent,
        data: {
          title: 'StepOne Form',
          stepper: {
            icon: 'fa fa-users',
            label: 'Registration Details'
          },
          isView: true,
          formName: 'regDetails',
          button: {
            next: {
              text: 'Next',
              icon: 'fa fa-chevron-right'
            }
          }
        }
      },
      {
        path: 'confirm',
        component: RegConfirmComponent,
        data: {
          title: 'Confirm Registration',
          stepper: {
              icon: 'fa fa-user-plus',
              label: 'Confirm Candidate'
          },
          formName: 'regConfirm',
          button: {
              next: {
                  text: 'Confirm',
                  icon: 'fa fa-plus-circle'
              },
              previous: {
                  text: 'Previous',
                  icon: 'fa fa-chevron-left'
              }
          }
        },
        // canActivate: [WizardWorkflowGuardService]
      },
      { path: '', redirectTo: 'details', pathMatch: 'full' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(formWizardRoutes)],
  exports: [RouterModule]
})
export class RegistrationRoutingModule { }

export const routedComponents = [WizardComponent];
