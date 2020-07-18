import { DatePipe } from '@angular/common';
import { Component, forwardRef, Host, Input, OnChanges, OnInit, Optional, SkipSelf, ViewChild } from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR
} from '@angular/forms';
import { BsDaterangepickerConfig } from 'ngx-bootstrap';
import { BsDatepickerConfig } from '../plugin/datepicker/bs-datepicker.config';
import { DateFormatter, DatePickerComponent } from '../plugin/datepicker/datepicker.module';

@Component({
  selector: 'ui-date-range',
  templateUrl: './date-range.component.html',
  styleUrls: ['./date-range.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => DateRangeComponent), multi: true }, DatePipe]
})
export class DateRangeComponent implements OnInit, OnChanges, ControlValueAccessor {
  constructor(@Optional() @Host() @SkipSelf() private controlContainer: ControlContainer) {
    this.fcDate = new FormControl();
  }

  @Input() label: string = 'Date';
  @Input() submitted: boolean;
  @Input() reset: boolean;
  @Input() minDate: Date;
  @Input() maxDate: Date;
  @Input() mode: any;
  @Input() format: string;
  @Input() type: any;
  @Input() disabled: boolean;
  @Input() formControlName: string;
  @Input() readOnly: string;
  @ViewChild(forwardRef(() => DatePickerComponent)) datepicker: DatePickerComponent;

  myDateValue: Date;
  pattern = 'dd/MM/yyyy';
  dateFrom: string;
  dateTo: string;
  dateNow: Date;

  bsRangeConfig: Partial<BsDaterangepickerConfig>;
  bsValue: Partial<BsDatepickerConfig>;
  colorTheme = 'theme-blue';
  placeholder = 'DD/MM/YYYY';
  maxlength: any;
  isReadonly = false;

  fcDate: FormControl;

  private control: AbstractControl;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  ngOnInit() {
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.fcDate.setValidators(this.control.validator);
      this.fcDate.setAsyncValidators(this.control.asyncValidator);
    }

    this.bsRangeConfig = Object.assign(
      {},
      {
        containerClass: this.colorTheme,
        showWeekNumbers: false,
        rangeInputFormat: this.format,
        dateInputFormat: this.format,
        maxDate: this.maxDate,
        minDate: this.minDate,
        minMode: this.mode
      }
    );

    if (this.format) {
      this.placeholder = this.format;
    }

    if (this.readOnly) {
      this.isReadonly = !this.isReadonly;
    }

    this.maxlength = this.placeholder.length;
  }

  ngOnChanges() {
    if (this.reset && this.fcDate.touched) {
      this.fcDate.markAsUntouched();
    }
  }

  writeValue(obj: any): void {
    if (this.reset && this.fcDate.touched) {
      this.fcDate.markAsUntouched();
    }
    this.fcDate.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (isDisabled) {
      this.fcDate.disable();
    } else {
      this.fcDate.enable();
    }
  }

  changeValue(value: string): void {
    if (value && value != null && value[0] && value[1]) {
      let dateFormatter = new DateFormatter();
      this.dateFrom = dateFormatter.format(new Date(value[0]), this.placeholder, '');
      this.dateTo = dateFormatter.format(new Date(value[1]), this.placeholder, '');
      this.propagateChange({ from: this.dateFrom, to: this.dateTo });
    }
  }
}
