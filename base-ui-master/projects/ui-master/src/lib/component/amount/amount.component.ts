import { Component, EventEmitter, Host, Input, OnChanges, OnInit, Optional, Output, SkipSelf } from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR
} from '@angular/forms';

@Component({
  selector: 'ui-amount',
  templateUrl: './amount.component.html',
  styleUrls: ['./amount.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: AmountComponent, multi: true }]
})
export class AmountComponent implements OnInit, OnChanges, ControlValueAccessor {
  constructor(
    @Optional()
    @Host()
    @SkipSelf()
    private controlContainer: ControlContainer
  ) {
    this.fcAmount = new FormControl();
  }

  @Input() submitted: boolean;
  @Input() reset: boolean;
  @Input() disabled: boolean = false;
  @Input() formControlName: string;
  @Input() label: string = 'Amount';
  @Input() currency: string;
  @Input() placeholder: string;
  @Input() min: any;
  @Input() max: any;
  @Output() change = new EventEmitter<Object>();

  fcAmount: FormControl;

  private control: AbstractControl;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  ngOnInit() {
    // Retrieve parent form control and assign the defined validations
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.fcAmount.setValidators(this.control.validator);
      this.fcAmount.setAsyncValidators(this.control.asyncValidator);
    }
  }

  ngOnChanges() {
    if (this.reset && this.fcAmount.touched) {
      this.fcAmount.markAsUntouched();
    }
  }

  writeValue(obj: any): void {
    if (this.reset && this.fcAmount.touched) {
      this.fcAmount.markAsUntouched();
    }
    this.fcAmount.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (isDisabled) {
      this.fcAmount.disable();
    } else {
      this.fcAmount.enable();
    }
  }

  valueChange(e: any) {
    this.propagateChange(this.fcAmount.value);
    this.change.emit(e);
  }
}
