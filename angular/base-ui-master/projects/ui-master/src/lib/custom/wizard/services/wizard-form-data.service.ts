import { Injectable } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Stepper } from '../stepper.config';
import { WizardWorkflowService } from './wizard-workflow.service';

@Injectable({
  providedIn: 'root'
})
export class WizardFormDataService {
  private formData: any = {};
  private initialstepIndex = 0;
  private steps: any = [];
  constructor(private workflowService: WizardWorkflowService) {}

  /**
   * Initialise stepper form data
   * @param steps Stepper settings from App Routing.
   */
  initialiseFormData(steps: any[]) {
    this.steps = steps;
    steps.forEach(step => {
      const sessionData = JSON.parse(sessionStorage.getItem(`wizard-${step.stepFormName}`));
      if (!sessionData) {
        this.formData[step.stepFormName] = {
          valid: false,
          isView: step.isView,
          hide: step.hide
        };
        sessionStorage.setItem(`wizard-${step.stepFormName}`, JSON.stringify(this.formData[step.stepFormName]));
      } else {
        this.formData[step.stepFormName] = sessionData;
      }
    });
  }

  updateFormData(steps: Stepper[]) {
    steps.forEach(step => {
      this.formData[step.stepFormName].hide = step.hide;
      sessionStorage.setItem(`wizard-${step.stepFormName}`, JSON.stringify(this.formData[step.stepFormName]));
    });
  }
  /**
   * Return reactive form controllers for particular page in the stepper.
   * @param formName Form name for particular page in the stepper. MUST BE SAME WITH ONE DEFINED IN APP ROUTING
   */
  getReactiveFormControl(formName: string): any {
    const formData = JSON.parse(sessionStorage.getItem(`wizard-${formName}`));
    return formData && formData.formControlsValues ? this.constructFormControl(formData.formControlsValues) : null;
  }

  /**
   * Return reactive form group for particular page in the stepper.
   * @param formName Form name for particular page in the stepper. MUST BE SAME WITH ONE DEFINED IN APP ROUTING
   */
  getReactiveFormGroup(formName: string): any {
    const formData = JSON.parse(sessionStorage.getItem(`wizard-${formName}`));
    return formData && formData.formControlsValues ? this.constructFormGroup(formData.formControlsValues) : null;
  }

  /**
   * Return non-reactive form data for particular page in the stepper.
   * @param formName Form name for particular page in the stepper. MUST BE SAME WITH ONE DEFINED IN APP ROUTING
   */
  getFormData(formName: string): any {
    const formData = JSON.parse(sessionStorage.getItem(`wizard-${formName}`));
    return formData && formData.data ? formData.data : null;
  }

  /**
   * Set reactive form data for particular page in the stepper.
   * @param formName Form name for particular page in the stepper. MUST BE SAME WITH ONE DEFINED IN APP ROUTING
   * @param formGroup Reactive form form group
   */
  setReactiveFormData(formName: string, formGroup: FormGroup) {
    this.formData[formName] = Object.assign(this.formData[formName], {
      formControlsValues: formGroup.getRawValue(),
      valid: true
    });
    this.workflowService.validateStep(formName);
    sessionStorage.setItem(`wizard-${formName}`, JSON.stringify(this.formData[formName]));
  }

  /**
   * Set non-reactive form data for particular page in the stepper.
   * @param formName Form name for particular page in the stepper. MUST BE SAME WITH ONE DEFINED IN APP ROUTING
   * @param data Any non-reactive form data
   */
  setFormData(formName: string, data: any) {
    this.formData[formName] = Object.assign(this.formData[formName], {
      data,
      valid: true
    });
    this.workflowService.validateStep(formName);
    sessionStorage.setItem(`wizard-${formName}`, JSON.stringify(this.formData[formName]));
  }

  /**
   * Return all Steppers data in form of { formName: {data: object, formControls: object, formGroup: formGroup}}
   */
  getAllStepperData(): any {
    let finalForm = {};

    for (const key in this.formData) {
      if (this.formData[key]) {
        let tempForm = {};
        if (this.formData[key].data) {
          tempForm = Object.assign({}, tempForm, { data: this.formData[key].data });
        }

        if (this.formData[key].formControlsValues) {
          tempForm = Object.assign({}, tempForm, {
            formControls: this.constructFormControl(this.formData[key].formControlsValues)
          });

          tempForm = Object.assign({}, tempForm, {
            formGroup: this.constructFormGroup(this.formData[key].formControlsValues)
          });
        }

        finalForm = Object.assign({}, finalForm, { [key]: tempForm });
      }
    }
    return finalForm;
  }

  /**
   * Reset all data from service and session
   */
  resetFormData() {
    // Reset the workflow
    this.workflowService.resetSteps();
    this.initialstepIndex = 0;
    // Return the form data after all this.* members had been reset
    for (const key in this.formData) {
      if (this.formData[key]) {
        this.formData[key] = { valid: false };
        sessionStorage.removeItem(`wizard-${key}`);
      }
    }
  }

  /**
   * Check validity of all steps in stepper
   */
  isFormValid() {
    // Return true if all forms had been validated successfully; otherwise, return false
    for (const key in this.formData) {
      if ((this.formData[key].data || this.formData[key].formControlsValues) && !!!this.formData[key].valid) {
        return false;
      }
    }

    return true;
  }

  /**
   * Create form control object from FormControl raw values
   * @param controlValues FormControl raw values
   */
  private constructFormControl(controlValues) {
    let newFormControl = {};
    Object.keys(controlValues).forEach(key => {
      newFormControl = Object.assign(newFormControl, { [key]: new FormControl(controlValues[key]) });
    });

    return newFormControl;
  }

  /**
   * Create form group object from FormControl raw values
   * @param controlValues FormControl raw values
   */
  private constructFormGroup(controlValues) {
    const newFormGroup = new FormGroup({});
    Object.keys(controlValues).forEach(key => {
      newFormGroup.addControl(key, new FormControl(controlValues[key]));
    });

    return newFormGroup;
  }

  /**
   * Get the latest step index
   */
  getStepperInitialStepIndex(pageIndex?) {
    this.initialstepIndex = 0;
    this.steps.forEach((step, idx) => {
      if (idx < pageIndex && !this.formData[step.stepFormName].valid && this.formData[step.stepFormName].isView) {
        this.formData[step.stepFormName].valid = true;
        sessionStorage.setItem(`wizard-${step.stepFormName}`, JSON.stringify(this.formData[step.stepFormName]));
      }
      if (this.formData[step.stepFormName].valid) {
        this.initialstepIndex++;
      }
    });

    return this.initialstepIndex;
  }
}
