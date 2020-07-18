import { Component, forwardRef, Host, Input, OnChanges, OnInit, Optional, SkipSelf } from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  FormGroupDirective,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
  ValidationErrors,
  Validator
} from '@angular/forms';

@Component({
  selector: 'ui-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.scss'],
  providers: [
    { provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => EmailComponent), multi: true },
    { provide: NG_VALIDATORS, useExisting: EmailComponent, multi: true }
  ],
  viewProviders: [{ provide: ControlContainer, useExisting: FormGroupDirective }]
})
export class EmailComponent implements OnInit, OnChanges, Validator, ControlValueAccessor {
  private control: AbstractControl;

  @Input() submitted: boolean;
  @Input() reset: boolean;
  @Input() disabled: boolean;
  @Input() formControlName: string;
  @Input() cssClass: string;

  fcEmail: FormControl;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  constructor(
    @Optional()
    @Host()
    @SkipSelf()
    private controlContainer: ControlContainer
  ) {
    this.fcEmail = new FormControl();
  }

  ngOnInit(): void {
    // Retrieve parent form control and assign the defined validations
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.fcEmail.setValidators(this.control.validator);
      this.fcEmail.setAsyncValidators(this.control.asyncValidator);
    }
  }

  ngOnChanges() {
    if (this.reset && this.fcEmail.touched) {
      this.fcEmail.markAsUntouched();
    }
  }

  writeValue(obj: any): void {
    if (this.reset && this.fcEmail.touched) {
      this.fcEmail.markAsUntouched();
    }
    this.fcEmail.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    if (isDisabled) {
      this.fcEmail.disable();
    } else {
      this.fcEmail.enable();
    }
  }

  validate(c: AbstractControl): ValidationErrors | null {
    return this.fcEmail.valid
      ? null
      : { invalidForm: { valid: false, message: 'Email Address is invalid. (ex. johndoe@company.com)' } };
  }

  valueChange() {
    this.propagateChange(this.fcEmail.value);
  }
}
