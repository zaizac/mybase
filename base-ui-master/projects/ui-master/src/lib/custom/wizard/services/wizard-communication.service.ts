import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ButtonConfig, OtpConfig, Stepper } from '../stepper.config';

@Injectable({
  providedIn: 'root'
})
export class WizardCommunicationService {
  private emitButtonConfigSource = new Subject<any>();
  private emitNextBtnClickSource = new Subject<any>();
  private emitPrevBtnClickSource = new Subject<any>();
  private emitOtpBtnClickSource = new Subject<any>();
  private emitCurrentStatIndSource = new Subject<any>();
  private emitUpdateStepperSource = new Subject<any>();
  private emitstepperConfigSource = new Subject<any>();
  private emitUpdateWizardSource = new Subject<any>();

  buttonConfigEmitted$ = this.emitButtonConfigSource.asObservable();
  nextBtnClickEmitted$ = this.emitNextBtnClickSource.asObservable();
  prevBtnClickEmitted$ = this.emitPrevBtnClickSource.asObservable();
  otpBtnClickEmitted$ = this.emitOtpBtnClickSource.asObservable();
  currentStatIndEmitted$ = this.emitCurrentStatIndSource.asObservable();
  updateStepperEmitted$ = this.emitUpdateStepperSource.asObservable();
  stepperConfigEmitted$ = this.emitstepperConfigSource.asObservable();
  updateWizardEmitted$ = this.emitUpdateWizardSource.asObservable();
  steppers: Stepper[];

  /**
   *
   * @param nextButton Accept buttonConfig object or boolean. empty object will set to default value.
   * Set to True also will set to default value. Set to false to hide button
   * @param prevButton Accept buttonConfig object or boolean. empty object will set to default value.
   * Set to True also will set to default value. Set to false to hide button
   * @param otpButton Accept OtpConfig object or boolean. empty object will set to default value.
   * Set to True also will set to default value. Set to false to hide button
   */
  emitButtonConfigEvent(
    nextButton: ButtonConfig | boolean,
    prevButton?: ButtonConfig | boolean,
    otpButton?: OtpConfig | boolean
  ) {
    if ((typeof nextButton === 'boolean' && nextButton) || (typeof nextButton !== 'boolean' && !nextButton)) {
      nextButton = {};
    }

    if ((typeof prevButton === 'boolean' && prevButton) || (typeof prevButton !== 'boolean' && !prevButton)) {
      prevButton = {};
    }

    if ((typeof otpButton === 'boolean' && otpButton) || (typeof otpButton !== 'boolean' && !otpButton)) {
      otpButton = {};
    }

    let buttons = {};
    if (nextButton) {
      buttons = Object.assign(buttons, { next: nextButton });
    }

    if (prevButton) {
      buttons = Object.assign(buttons, { previous: prevButton });
    }

    if (otpButton) {
      buttons = Object.assign(buttons, { otp: otpButton });
    }
    this.emitButtonConfigSource.next(buttons);
  }

  emitNextBtnClickEvent() {
    this.emitNextBtnClickSource.next();
  }

  emitPrevBtnClickEvent() {
    this.emitPrevBtnClickSource.next();
  }

  emitOtpBtnClickEvent() {
    this.emitOtpBtnClickSource.next();
  }

  currentStatIndEvent(index) {
    this.emitCurrentStatIndSource.next(index);
  }

  updateStepperEvent(steppers: Stepper[]) {
    this.emitUpdateStepperSource.next(steppers);
  }

  updateWizardEvent() {
    this.emitUpdateWizardSource.next();
  }

  set stepperConfig(steppers: Stepper[]) {
    this.steppers = steppers;
  }

  get stepperConfig() {
    return this.steppers;
  }

  constructor() {}
}
