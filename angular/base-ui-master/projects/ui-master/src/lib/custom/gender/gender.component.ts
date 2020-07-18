import { Component, Host, Input, OnChanges, OnInit, Optional, SkipSelf } from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR
} from '@angular/forms';

@Component({
  selector: 'ui-gender',
  templateUrl: './gender.component.html',
  styleUrls: ['./gender.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: GenderComponent, multi: true }]
})
export class GenderComponent implements OnInit, OnChanges, ControlValueAccessor {
  private control: AbstractControl;

  @Input() submitted: boolean;
  @Input() reset: boolean;
  @Input() disabled: boolean;
  @Input() hasBoth: boolean;
  @Input() formControlName: string;

  fcGender: FormControl;

  onTouched = () => {};
  propagateChange = (_: any) => {};
  genderOpt = [
    { key: 'M', label: 'Male', show: true },
    { key: 'F', label: 'Female', show: true },
    { key: 'B', label: 'Both', show: this.hasBoth }
  ];

  constructor(@Optional() @Host() @SkipSelf() private controlContainer: ControlContainer) {
    this.fcGender = new FormControl();
  }

  ngOnInit(): void {
    // Retrieve parent form control and assign the defined validations
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.fcGender.setValidators(this.control.validator);
      this.fcGender.setAsyncValidators(this.control.asyncValidator);
    }
  }

  ngOnChanges() {
    if (this.reset && this.fcGender.touched) {
      this.fcGender.markAsUntouched();
    }
  }

  writeValue(obj: any): void {
    if (this.reset && this.fcGender.touched) {
      this.fcGender.markAsUntouched();
    }
    this.fcGender.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (isDisabled) {
      this.fcGender.disable();
    } else {
      this.fcGender.enable();
    }
  }

  getValue() {
    this.propagateChange(this.fcGender.value);
  }
}
