import {
  ChangeDetectorRef,
  Component,
  forwardRef,
  Host,
  Input,
  OnInit,
  Optional,
  SimpleChanges,
  SkipSelf,
  ViewChild
} from '@angular/core';
import { AbstractControl, ControlContainer, FormControl, NG_VALUE_ACCESSOR } from '@angular/forms';
import { BsDatepickerConfig } from '../plugin/datepicker/bs-datepicker.config';
import { BsDaterangepickerConfig } from '../plugin/datepicker/bs-datepicker.module';
import { DateFormatter } from '../plugin/datepicker/date-formatter';
import { DatePickerComponent } from '../plugin/datepicker/datepicker.module';
import { DatepickerService } from '../plugin/datepicker/datepicker.service';
import { TimepickerComponent } from '../plugin/timepicker/timepicker.component';

@Component({
  selector: 'ui-datetime',
  templateUrl: './datetime.component.html',
  styleUrls: ['./datetime.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => DatetimeComponent), multi: true }]
})
export class DatetimeComponent implements OnInit {
  constructor(
    @Optional() @Host() @SkipSelf() private controlContainer: ControlContainer,
    private cdRef: ChangeDetectorRef,
    private _datepickerService: DatepickerService
  ) {
    this.datetime = new FormControl();
    // this.minDate = new Date();
    // this.maxDate = new Date();

    this._datepickerService.valueDate$.subscribe(valueDate => {
      this.myDateValue = valueDate;
    });
  }
  @Input() label: string = 'Date';
  @Input() submitted: boolean;
  @Input() reset: boolean;
  @Input() minDate: Date;
  @Input() maxDate: Date;
  @Input() format: string;
  @Input() disabled: boolean;
  @Input() formControlName: string;

  @Input() showMeridian: boolean;
  @Input() showSeconds: boolean;
  @Input() hourStep: any;
  @Input() minuteStep: any;
  @ViewChild(forwardRef(() => TimepickerComponent)) timepickercust: TimepickerComponent;
  @ViewChild(forwardRef(() => DatePickerComponent)) datepicker: DatePickerComponent;

  myDateValue: string;
  pattern = 'DD/MM/YYYY';
  singleDate: string;
  dateNow: Date;

  bsInlineValue = new Date();
  datetime: FormControl;
  time: Date;
  timeVal: any;
  timeLocal: 'en-US';

  bsConfig: Partial<BsDatepickerConfig>;
  bsRangeConfig: Partial<BsDaterangepickerConfig>;
  bsValue: Partial<BsDatepickerConfig>;
  colorTheme = 'theme-blue';
  placeholder = 'DD/MM/YYYY';
  maxlength: any;

  private control: AbstractControl;

  onTouched = () => {};
  propagateChange = (_: any) => {};

  ngOnInit() {
    // Retrieve parent form control and assign the defined validations
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.datetime.setValidators(this.control.validator);
      this.datetime.setAsyncValidators(this.control.asyncValidator);
    }

    this.bsConfig = Object.assign(
      {},
      {
        containerClass: this.colorTheme,
        showWeekNumbers: false,
        dateInputFormat: this.format,
        maxDate: this.maxDate,
        minDate: this.minDate,
        adaptivePosition: true
      }
    );

    if (this.format) {
      this.placeholder = this.format;
    }
    this.maxlength = this.placeholder.length;
    this.ngAfterViewChecked();
  }

  applyValue(myDropdown: HTMLElement) {
    if (this.myDateValue === undefined) {
      this.myDateValue = new Date().toISOString().split('T')[0];
    }
    if (this.time === undefined) {
      this.time = new Date();
    }
    this.singleDate = new DateFormatter().format(new Date(this.myDateValue), this.pattern, '');
    if (this.showMeridian === true) {
      this.timeVal = this.time.toLocaleTimeString();
    } else if (this.showMeridian === false) {
      this.timeVal = this.time.toLocaleTimeString(this.timeLocal, { hour12: false });
    }
    this.datetime.setValue(this.singleDate + '  ' + this.timeVal);
    this.propagateChange(this.singleDate + '  ' + this.timeVal);

    myDropdown.classList.remove('show');
  }

  writeValue(obj: any): void {
    if (this.reset && this.datetime.touched) {
      this.datetime.markAsUntouched();
    }
    this.datetime.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (isDisabled) {
      this.datetime.disable();
    } else {
      this.datetime.enable();
    }
  }

  onClear() {
    this.datetime.setValue('');
  }

  ngOnChanges(changes: SimpleChanges) {
    if (this.reset && this.datetime.touched) {
      this.datetime.markAsUntouched();
    }

    for (const timeValue in changes) {
      const change = changes[timeValue];

      const valueTo = JSON.stringify(change.currentValue);
      const valueFrom = JSON.stringify(change.previousValue);
      const changeValue = `${timeValue}: currentValue = ${valueTo}, previousValue = ${valueFrom}`;
    }
  }

  ngAfterViewChecked() {
    this.dateNow = new Date();
    this.cdRef.detectChanges();
  }
}
