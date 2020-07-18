import { DatePipe } from '@angular/common';
import {
  ChangeDetectorRef,
  Component,
  forwardRef,
  Host,
  Input,
  OnChanges,
  OnInit,
  Optional,
  SkipSelf,
  ViewChild,
  Output,
  EventEmitter
} from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR
} from '@angular/forms';
import { DateFormatter } from 'ngx-bootstrap';
import { BsDatepickerConfig } from '../plugin/datepicker/bs-datepicker.config';
import { DatePickerComponent } from '../plugin/datepicker/datepicker.module';
import { ViewDateValue } from '../plugin/datepicker/themes/bs/bs-custom-dates-view.component';

@Component({
  selector: 'ui-date',
  templateUrl: './date.component.html',
  styleUrls: ['./date.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => DateComponent), multi: true }, DatePipe]
})
export class DateComponent implements OnInit, OnChanges, ControlValueAccessor {
  constructor(
    @Optional() @Host() @SkipSelf() private controlContainer: ControlContainer,
    private cdRef: ChangeDetectorRef
  ) {
    this.fcDate = new FormControl();
  }

  @Input() label: string = 'Date';
  @Input() submitted: boolean;
  @Input() reset: boolean;
  @Input() minDate: Date;
  @Input() maxDate: Date;
  @Input() mode: any;
  @Input() format: string;
  @Input() disabled: boolean;
  @Input() formControlName: string;
  @Input() readOnly: string;
  @ViewChild(forwardRef(() => DatePickerComponent)) datepicker: DatePickerComponent;
  @Output() bsValueChange = new EventEmitter<any>();

  viewdatevalue: ViewDateValue[] = [];
  bsConfig: Partial<BsDatepickerConfig>;
  colorTheme = 'theme-blue';
  placeholder = 'DD/MM/YYYY';
  maxlength: any;
  isReadonly = false;

  fcDate: FormControl;

  private control: AbstractControl;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  ngOnInit() {
    // Retrieve parent form control and assign the defined validations
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.fcDate.setValidators(this.control.validator);
      this.fcDate.setAsyncValidators(this.control.asyncValidator);
    }

    this.bsConfig = Object.assign(
      {},
      {
        containerClass: this.colorTheme,
        showWeekNumbers: false,
        dateInputFormat: this.format,
        maxDate: this.maxDate,
        minDate: this.minDate,
        minMode: this.mode,
        adaptivePosition: true
      }
    );

    if (this.format) {
      this.placeholder = this.format;
    }

    if (this.readOnly) {
      this.isReadonly = !this.isReadonly;
    }

    this.maxlength = this.placeholder.length;
    this.viewdatevalue.push({ value: this.maxDate });
    this.ngAfterViewChecked();
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
    if(this.fcDate){
      this.propagateChange(new DateFormatter().format(new Date(value), this.placeholder, ''));
    }else{
      this.propagateChange(null);
    }
    this.bsValueChange.emit(value);
  }

  // tslint:disable-next-line: use-life-cycle-interface
  ngAfterViewChecked() {
    this.cdRef.detectChanges();
  }
}
