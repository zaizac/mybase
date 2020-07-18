import { Component, Host, Input, OnChanges, OnInit, Optional, SkipSelf } from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
  ValidationErrors,
  Validator
} from '@angular/forms';
import { UiMasterConfig } from '../../ui-master.config';
import { PasswordUtil } from './util/password';

@Component({
  selector: 'ui-pass',
  templateUrl: './password.component.html',
  providers: [
    { provide: NG_VALUE_ACCESSOR, useExisting: PasswordComponent, multi: true },
    { provide: NG_VALIDATORS, useExisting: PasswordComponent, multi: true }
  ]
})
export class PasswordComponent implements OnInit, OnChanges, Validator, ControlValueAccessor {
  private control: AbstractControl;

  @Input() submitted: boolean;
  @Input() reset: boolean;
  @Input() disabled: boolean;
  @Input() formControlName: string;
  @Input() public isPlaceholder: boolean = false;

  fcPassword: FormControl;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  constructor(
    @Optional() @Host() @SkipSelf() private controlContainer: ControlContainer,
    private config: UiMasterConfig
  ) {
    this.fcPassword = new FormControl();
  }

  ngOnInit(): void {
    // Retrieve parent form control and assign the defined validations
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.fcPassword.setValidators(this.control.validator);
      this.fcPassword.setAsyncValidators(this.control.asyncValidator);
    }
  }

  ngOnChanges() {
    if (this.reset && this.fcPassword.touched) {
      this.fcPassword.markAsUntouched();
    }
  }

  writeValue(obj: any): void {
    if (this.reset && this.fcPassword.touched) {
      this.fcPassword.markAsUntouched();
    }

    this.fcPassword.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    if (isDisabled) {
      this.fcPassword.disable();
    } else {
      this.fcPassword.enable();
    }
  }

  validate(c: AbstractControl): ValidationErrors | null {
    return this.fcPassword.valid ? null : { invalidForm: { valid: false, message: 'Password is invalid.' } };
  }

  getValue() {
    this.propagateChange(PasswordUtil.hash(this.fcPassword.value, this.config.ekey));
  }
}
