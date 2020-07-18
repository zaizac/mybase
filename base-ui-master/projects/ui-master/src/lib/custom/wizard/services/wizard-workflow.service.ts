import { Injectable } from '@angular/core';
import { Stepper } from '../stepper.config';

@Injectable({
  providedIn: 'root'
})
export class WizardWorkflowService {
  private workflow: { step: string; valid: boolean; isView: boolean; hide: boolean }[] = [];

  constructor() {}

  initialiseWorkFlow(stepper: any) {
    (stepper as Array<any>).forEach(stepp => {
      const sessionData = JSON.parse(sessionStorage.getItem(`wizard-${stepp.stepFormName}`));
      this.workflow.push({
        step: stepp.linkUrl,
        isView: stepp.isView,
        hide: stepp.hide,
        valid: sessionData ? sessionData.valid : false
      });
    });
  }

  updateWorkFlow(steppers: Stepper[]) {
    this.workflow.forEach(flow => {
      flow.hide = !!steppers.find(step => step.linkUrl === flow.step).hide;
    });
  }

  validateStep(step: string) {
    // If the state is found, set the valid field to true
    let found = false;
    for (let i = 0; i < this.workflow.length && !found; i++) {
      if (this.workflow[i].step === step) {
        found = this.workflow[i].valid = true;
      }
    }
  }

  resetSteps() {
    // Reset all the steps in the Workflow to be invalid
    this.workflow.forEach(element => {
      element.valid = false;
    });
  }

  getFirstInvalidStep(step: string): string {
    // If all the previous steps are validated, return blank
    // Otherwise, return the first invalid step
    let found = false;
    let valid = true;
    let redirectToStep = '';

    for (let i = 0; i < this.workflow.length && !found && valid; i++) {
      const item = this.workflow[i];
      if (item.step === step) {
        found = true;
        redirectToStep = '';
      } else {
        valid = item.valid || item.isView || item.hide;
        if (!item.hide) {
          redirectToStep = item.step;
        }
      }
    }
    return redirectToStep;
  }

  getFirstStep() {
    return this.workflow[0].step;
  }
}
