import {
  ChangeDetectorRef,
  Component,
  forwardRef,
  Host,
  Input,
  OnInit,
  Optional,
  SkipSelf,
  ViewChild
} from '@angular/core';
import { AbstractControl, ControlContainer, FormControl, NG_VALUE_ACCESSOR } from '@angular/forms';
import { BsDatepickerConfig } from '../plugin/datepicker/bs-datepicker.config';
import { DateFormatter } from '../plugin/datepicker/date-formatter';
import { DatePickerComponent } from '../plugin/datepicker/datepicker.module';
import { DatepickerService } from '../plugin/datepicker/datepicker.service';

@Component({
  selector: 'ui-daterangecustom',
  templateUrl: './daterangecustom.component.html',
  styleUrls: ['./daterangecustom.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => DaterangecustomComponent), multi: true }]
})
export class DaterangecustomComponent implements OnInit {
  constructor(
    @Optional() @Host() @SkipSelf() private controlContainer: ControlContainer,
    private _datepickerService: DatepickerService,
    private cdRef: ChangeDetectorRef
  ) {
    this.customdate = new FormControl();

    this._datepickerService.valueDate$.subscribe(valueDate => {
      this.daterange = valueDate;
    });

    this.today.setDate(this.minDateRange.getDate() + 0);
    this.yesterday.setDate(this.minDateRange.getDate() - 1);
    this.last7Days.setDate(this.last7Days.getDate() - 6);
    this.last30Days.setDate(this.last30Days.getDate() - 30);
    this.thismonth.setMonth(this.thismonth.getMonth(), +1);

    this.singleDate = new DateFormatter().format(new Date(this.today), this.placeholder, '');
  }
  @Input() label: string = 'Date';
  @Input() reset: boolean;
  @Input() minDate: Date;
  @Input() maxDate: Date;
  @Input() format: string;
  @Input() type: any;
  @Input() disabled: boolean;
  @Input() formControlName: string;
  @ViewChild(forwardRef(() => DatePickerComponent)) datepicker: DatePickerComponent;

  daterange: Date[];
  minDateRange: Date = new Date();
  today: Date = new Date();
  yesterday: Date = new Date();
  last7Days: Date = new Date();
  last30Days: Date = new Date();
  thismonth: Date = new Date();

  lastMonthDate: Date = new Date();
  firstMonthDate: Date = new Date();

  myDateValue: string;
  singleDate: string;
  dateFrom: string;
  dateTo: string;
  dateNow: Date;

  colorTheme = 'theme-blue';
  placeholder = 'DD/MM/YYYY';
  maxlength: any;
  isShow = true;

  customdate: FormControl;
  bsConfig: Partial<BsDatepickerConfig>;

  private control: AbstractControl;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  writeValue(obj: any): void {
    if (this.reset && this.customdate.touched) {
      this.customdate.markAsUntouched();
    }
    this.customdate.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (isDisabled) {
      this.customdate.disable();
    } else {
      this.customdate.enable();
    }
  }

  showCal() {
    this.isShow = !this.isShow;
  }

  todayDate() {
    this.customdate.setValue(this.singleDate + ' - ' + this.singleDate);
    this.propagateChange({ from: this.singleDate, to: this.singleDate });
    this.isShow = true;
  }

  yesterdayDate() {
    this.formatDate(this.yesterday);
    this.customdate.setValue(this.myDateValue + ' - ' + this.myDateValue);
    this.propagateChange({ from: this.myDateValue, to: this.myDateValue });
    this.isShow = true;
  }

  last7Dates() {
    this.formatDate(this.last7Days);
    this.customdate.setValue(this.myDateValue + ' - ' + this.singleDate);
    this.propagateChange({ from: this.myDateValue, to: this.singleDate });
    this.isShow = true;
  }

  last30Dates() {
    this.formatDate(this.last30Days);
    this.customdate.setValue(this.myDateValue + ' - ' + this.singleDate);
    this.propagateChange({ from: this.myDateValue, to: this.singleDate });
    this.isShow = true;
  }

  thisMonthDates() {
    this.formatDate(this.thismonth);
    this.customdate.setValue(this.myDateValue + ' - ' + this.singleDate);
    this.propagateChange({ from: this.myDateValue, to: this.singleDate });
    this.isShow = true;
  }

  lastMonthDates() {
    this.lastMonthDate = new Date(this.minDateRange.getFullYear(), this.minDateRange.getMonth(), 0);
    this.firstMonthDate = new Date(this.lastMonthDate.getFullYear(), this.lastMonthDate.getMonth(), 1);
    this.dateFrom = new DateFormatter().format(new Date(this.firstMonthDate), this.placeholder, '');
    this.dateTo = new DateFormatter().format(new Date(this.lastMonthDate), this.placeholder, '');
    this.customdate.setValue(this.dateFrom + ' - ' + this.dateTo);
    this.propagateChange({ from: this.dateFrom, to: this.dateTo });
    this.isShow = true;
  }

  formatDate(valueDate: any) {
    this.myDateValue = new DateFormatter().format(new Date(valueDate), this.placeholder, '');
  }

  applyValue(myDropdown: HTMLElement) {
    if (this.daterange && this.daterange != null && this.daterange[0] && this.daterange[1]) {
      this.dateFrom = new DateFormatter().format(new Date(this.daterange[0]), this.placeholder, '');
      this.dateTo = new DateFormatter().format(new Date(this.daterange[1]), this.placeholder, '');
      this.customdate.setValue(this.dateFrom + ' - ' + this.dateTo);
    }
    this.propagateChange({ from: this.dateFrom, to: this.dateTo });

    myDropdown.classList.remove('show');
  }

  onClear() {
    this.customdate.setValue('');
  }

  ngOnInit() {
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.customdate.setValidators(this.control.validator);
      this.customdate.setAsyncValidators(this.control.asyncValidator);
    }
    this.bsConfig = Object.assign(
      {},
      {
        containerClass: this.colorTheme,
        showWeekNumbers: false,
        //dateInputFormat: this.format,
        //maxDate: this.maxDate,
        //minDate: this.minDate,
        adaptivePosition: true
      }
    );

    if (this.format) {
      this.placeholder = this.format;
    }

    this.maxlength = this.placeholder.length;
    this.ngAfterViewChecked();
  }

  ngAfterViewChecked() {
    this.dateNow = new Date();
    this.cdRef.detectChanges();
  }
}
