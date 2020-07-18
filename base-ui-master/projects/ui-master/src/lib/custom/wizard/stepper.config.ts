import { FormGroup } from '@angular/forms';

export class StepperConfig {
  clickable?: boolean;
  steps?: Stepper[];
}

export class Stepper {
  linkUrl: string;
  pageName: string;
  title?: string;
  icon?: string;
  stepFormName: string;
  label?: string;
  isView?: boolean;
  hide?: boolean;
}

export class ButtonConfig {
  buttonType?: string;
  text?: string;
  display?: boolean;
  icon?: string;
  disabled?: boolean;
}

export class OtpConfig {
  buttonType?: string;
  submitted?: any;
  otpService?: any;
  email?: any;
  redirectURL?: string;
  icon?: string;
  text?: string;
  formGroup?: FormGroup;
  disabled?: boolean;
  display?: boolean;
  required?: any;
  formControlName?: string;
}
