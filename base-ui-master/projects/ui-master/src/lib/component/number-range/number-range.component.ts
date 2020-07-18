import { Component, Host, Input, OnChanges, OnInit, Optional, SkipSelf } from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR
} from '@angular/forms';
import { ChangeContext, Options } from 'ng5-slider';

@Component({
  selector: 'ui-number-range',
  templateUrl: './number-range.component.html',
  styleUrls: ['./number-range.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: NumberRangeComponent, multi: true }]
})
export class NumberRangeComponent implements OnInit, OnChanges, ControlValueAccessor {
  constructor(
    @Optional()
    @Host()
    @SkipSelf()
    private controlContainer: ControlContainer
  ) {
    this.fcNumber = new FormControl();
    this.fcNumberFrom = new FormControl();
    this.fcNumberTo = new FormControl();
  }

  get hasValue() {
    return this.fcNumber.value;
  }

  private control: AbstractControl;

  @Input() label: string = 'Number range';
  @Input() submitted: any;
  @Input() reset: boolean;
  @Input() disabled = false;
  @Input() formControlName: string;
  @Input() min: number;
  @Input() max: number;

  DASH_SEPARATOR: string = ' - ';
  fcNumber: FormControl;
  public fcNumberFrom: FormControl;
  public fcNumberTo: FormControl;

  options: Options = {
    floor: 0,
    ceil: 100,
    showTicks: true,
    showTicksValues: true,
    tickStep: 10,
    tickValueStep: 10,
    noSwitching: true
  };

  private from: number;
  private to: number;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  ngOnInit() {
    // Retrieve parent form control and assign the defined validations
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.fcNumber.setValidators(this.control.validator);
      this.fcNumber.setAsyncValidators(this.control.asyncValidator);
    }
    this.fcNumberFrom.setValue(this.min);
    this.fcNumberTo.setValue(this.max);
    this.from = this.min;
    this.to = this.max;
    this.options = Object.assign({}, this.options, { floor: this.min, ceil: this.max });
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

    if (obj && obj.from && obj.to) {
      this.fcNumber.setValue(obj.from + this.DASH_SEPARATOR + obj.to);
      this.from = obj.from;
      this.to = obj.to;
    } else {
      this.fcNumber.setValue(obj);
    }
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
    if (isDisabled) {
      this.fcNumber.disable();
    } else {
      this.fcNumber.enable();
    }
  }

  onSliderChange(changeContext: ChangeContext) {
    this.fcNumberFrom.setValue(changeContext.value);
    this.fcNumberTo.setValue(changeContext.highValue);
  }

  valueChange() {
    this.fcNumber.setValue(this.fcNumberFrom.value + this.DASH_SEPARATOR + this.fcNumberTo.value);
    this.from = this.fcNumberFrom.value;
    this.to = this.fcNumberTo.value;
    this.propagateChange({
      from: this.fcNumberFrom.value,
      to: this.fcNumberTo.value
    });
  }

  toggled(e) {
    if (e) {
      this.fcNumberFrom.setValue(this.from);
      this.fcNumberTo.setValue(this.to);
    }
  }

  valueChangeFrom() {
    if (!this.fcNumberFrom.value || this.fcNumberFrom.value < this.min || this.fcNumberFrom.value > this.max) {
      // If value is remove, assign the last known value or min value
      this.fcNumberFrom.setValue(this.from);
    }
  }

  valueChangeTo() {
    if (!this.fcNumberTo.value || this.fcNumberTo.value < this.min || this.fcNumberTo.value > this.max) {
      // If value is remove, assign the last known value or min value
      this.fcNumberTo.setValue(this.to);
    }
  }

  valueClear() {
    this.from = this.min;
    this.to = this.max;
    this.fcNumber.setValue(null);
    this.fcNumberFrom.setValue(this.min);
    this.fcNumberTo.setValue(this.max);
  }

  showClear() {
    return this.hasValue && !this.disabled;
  }
}
