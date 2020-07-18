import { Component, Host, Input, OnChanges, OnInit, Optional, SkipSelf } from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR,
  Validators
} from '@angular/forms';

@Component({
  selector: 'ui-number',
  templateUrl: './number.component.html',
  styleUrls: ['./number.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: NumberComponent, multi: true }]
})
export class NumberComponent implements OnInit, OnChanges, ControlValueAccessor {
  
  registerOnValidatorChange?(fn: () => void): void {
    throw new Error("Method not implemented.");
  }
  private control: AbstractControl;

  @Input() submitted: any;
  @Input() reset: boolean;
  @Input() disabled = false;
  @Input() formControlName: string;
  @Input() min: number;
  @Input() max: number;

  fcNumber: FormControl;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  constructor(
    @Optional()
    @Host()
    @SkipSelf()
    private controlContainer: ControlContainer
  ) {
    this.fcNumber = new FormControl();
  }

  ngOnInit() {

    let validatorArg = [];

    if (this.min) {
      validatorArg.push(Validators.min(this.min))
    }

    if (this.max) {
      validatorArg.push(Validators.max(this.max))
    }

    // Retrieve parent form control and assign the defined validations
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      validatorArg.push(this.control.validator);
      this.fcNumber.setValidators(validatorArg);
      this.fcNumber.setAsyncValidators(this.control.asyncValidator);
    } else if (validatorArg) {
      this.fcNumber.setValidators(validatorArg);
    }
    
  }

  ngOnChanges() {
    if (this.reset && this.fcNumber.touched) {
      this.fcNumber.markAsUntouched();
    }
  }

  writeValue(obj: any): void {
    if (this.reset && this.fcNumber.touched) {
      this.fcNumber.markAsUntouched();
    }
    this.fcNumber.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (isDisabled) {
      this.fcNumber.disable();
    } else {
      this.fcNumber.enable();
    }
  }

  valueChange() {
    this.propagateChange(this.fcNumber.value);
  }
}
