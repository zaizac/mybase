import { Component, EventEmitter, forwardRef, Host, Input, OnInit, Optional, Output, SkipSelf } from '@angular/core';
import { AbstractControl, ControlContainer, FormControl, NG_VALUE_ACCESSOR, Validators } from '@angular/forms';

@Component({
  selector: 'ui-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => RatingComponent), multi: true }]
})
export class RatingComponent implements OnInit {
  @Input() type: any;
  @Input() rateValue: number | string;
  @Input() max: number;
  @Input() mode: number | string;
  @Input() readonly: boolean;
  @Input() formControlName: string;
  @Input() reset: boolean;
  @Output() click = new EventEmitter<Object>();

  index: any;
  rating: any;

  currentRate = 0;
  currentRate2 = 0;
  ratebtn: any;
  selected = 0;
  hovered = 0;
  fcRate: FormControl;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  private control: AbstractControl;

  constructor(@Optional() @Host() @SkipSelf() private controlContainer: ControlContainer) {
    this.fcRate = new FormControl(null, Validators.required);
    this.propagateChange;
  }

  toggle() {
    if (this.fcRate.disabled) {
      this.fcRate.enable();
    } else {
      this.fcRate.disable();
    }
  }

  changeValue(e) {
    this.propagateChange(this.fcRate.value);
    this.click.emit(e);
  }

  ratePercentage(e) {
    const percent = (this.fcRate.value / 5) * 100 + '%';
    this.propagateChange(percent);
    this.click.emit(e);
  }

  ngOnInit() {
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.fcRate.setValidators(this.control.validator);
      this.fcRate.setAsyncValidators(this.control.asyncValidator);
    }
  }

  ngOnChanges() {
    if (this.reset && this.fcRate.touched) {
      this.fcRate.markAsUntouched();
    }
  }

  writeValue(obj: any): void {
    if (this.reset && this.fcRate.touched) {
      this.fcRate.markAsUntouched();
    }
    this.fcRate.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (isDisabled) {
      this.fcRate.disable();
    } else {
      this.fcRate.enable();
    }
  }
}
