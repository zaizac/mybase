import { DatePipe } from '@angular/common';
import {
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
import { DateFormatter } from '../plugin/datepicker/date-formatter';
import { DatePickerComponent } from '../plugin/datepicker/datepicker.module';
import { DatepickerService } from '../plugin/datepicker/datepicker.service';
import { TimepickerComponent } from '../plugin/timepicker/timepicker.component';

@Component({
  selector: 'ui-datetime-range',
  templateUrl: './datetime-range.component.html',
  styleUrls: ['./datetime-range.component.scss'],
  providers: [
    { provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => DateTimeRangeComponent), multi: true },
    DatePipe
  ]
})
export class DateTimeRangeComponent implements OnInit {
  constructor(
    @Optional() @Host() @SkipSelf() private controlContainer: ControlContainer,
    private datePipe: DatePipe,
    private _datepickerService: DatepickerService
  ) {
    this.datetimerange = new FormControl();

    this._datepickerService.valueDate$.subscribe(valueDate => {
      this.daterange = valueDate;
    });
  }

  @Input() label: string = 'Date';

  //current
  @Input() showMeridian: boolean;
  @Input() reset: boolean;
  @Input() showSeconds: boolean;
  @Input() hourStep: any;
  @Input() minuteStep: any;
  @Input() formControlName: string;
  @Input() minDate: Date;
  @Input() maxDate: Date;
  @Input() format: string;
  @ViewChild(forwardRef(() => TimepickerComponent)) timepickercust: TimepickerComponent;
  @ViewChild(forwardRef(() => DatePickerComponent)) datepicker: DatePickerComponent;
  datetimerange: FormControl;

  daterange: string;

  timefrom: Date;
  timeto: Date;

  hourFrom: string | number;
  minuteFrom: string | number;
  secondFrom: string | number;
  pmFrom: string;
  valueFrom: any;

  hourTo: string | number;
  minuteTo: string | number;
  secondTo: string | number;
  pmTo: string;
  valueTo: any;

  dayF: string | number;
  monthF: string | number;
  yearF: string | number;
  dayT: string | number;
  monthT: string | number;
  yearT: string | number;

  result: any;
  timeLocal: 'en-US';

  timef: any;
  timet: any;

  bsConfig: Partial<BsDatepickerConfig>;

  myDateValue: any;
  pattern = 'dd/MM/yyyy';
  dateFrom: string;
  dateTo: string;

  colorTheme = 'theme-blue';
  placeholder = 'DD/MM/YYYY';
  maxlength: any;

  private control: AbstractControl;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  writeValue(obj: any): void {
    if (this.reset && this.datetimerange.touched) {
      this.datetimerange.markAsUntouched();
    }
    this.datetimerange.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  onClear() {
    this.datetimerange.setValue('');
  }

  applyValue(myDropdown: HTMLElement) {
    if (this.timefrom === undefined) {
      this.timefrom = new Date();
    }
    if (this.timeto === undefined) {
      this.timeto = new Date();
    }

    if (this.showMeridian === true) {
      this.timef = this.timefrom.toLocaleTimeString();
      this.timet = this.timeto.toLocaleTimeString();
      this.dateTimeFormat(this.timef, this.timet, this.daterange);
    } else if (this.showMeridian === false) {
      this.timef = this.timefrom.toLocaleTimeString(this.timeLocal, { hour12: false });
      this.timet = this.timeto.toLocaleTimeString(this.timeLocal, { hour12: false });
      this.dateTimeFormat(this.timef, this.timet, this.daterange);
    }

    this.datetimerange.setValue(this.dateFrom + ' ' + this.valueFrom + ' - ' + this.dateTo + ' ' + this.valueTo);
    //this.propagateChange(this.dateFrom + ' ' + this.valueFrom + ' - ' + this.dateTo + ' ' + this.valueTo);
    myDropdown.classList.remove('show');
  }

  dateTimeFormat(timeF: any, timeT: any, date: string) {
    if (date && date != null && date[0] && date[1]) {
      this.dateFrom = new DateFormatter().format(new Date(date[0]), this.placeholder, '');
      this.dateTo = new DateFormatter().format(new Date(date[1]), this.placeholder, '');

      const dateSplitFrom = this.dateFrom.split('/');
      this.dayF = parseInt(dateSplitFrom[0]);
      this.monthF = parseInt(dateSplitFrom[1]);
      this.yearF = parseInt(dateSplitFrom[2]);

      const dateSplitTo = this.dateTo.split('/');
      this.dayT = parseInt(dateSplitTo[0]);
      this.monthT = parseInt(dateSplitTo[1]);
      this.yearT = parseInt(dateSplitTo[2]);
    } else if (date === undefined) {
      this.daterange = new Date().toISOString().split('T')[0];
      this.dateFrom = new DateFormatter().format(new Date(this.daterange), this.placeholder, '');
      this.dateTo = new DateFormatter().format(new Date(this.daterange), this.placeholder, '');
    }
    const timeSplitFrom = timeF.split(':');
    this.hourFrom = parseInt(timeSplitFrom[0]);
    this.minuteFrom = parseInt(timeSplitFrom[1]);
    this.secondFrom = parseInt(timeSplitFrom[2].split(' ')[0]);
    this.pmFrom = timeSplitFrom[2].split(' ')[1];
    const jTimeFrom = {
      value: this.dateFrom + ' ' + timeF,
      date: this.dateFrom,
      time: timeF,
      year: this.yearF,
      month: this.monthF,
      day: this.dayF,
      hour: this.hourFrom,
      minute: this.minuteFrom,
      second: this.secondFrom,
      meridian: this.pmFrom
    };
    this.valueFrom = timeF;

    const timeSplitTo = timeT.split(':');
    this.hourTo = parseInt(timeSplitTo[0]);
    this.minuteTo = parseInt(timeSplitTo[1]);
    this.secondTo = parseInt(timeSplitTo[2].split(' ')[0]);
    this.pmTo = timeSplitTo[2].split(' ')[1];
    const jTimeTo = {
      value: this.dateTo + ' ' + timeT,
      date: this.dateTo,
      time: timeT,
      year: this.yearT,
      month: this.monthT,
      day: this.dayT,
      hour: this.hourTo,
      minute: this.minuteTo,
      second: this.secondTo,
      meridian: this.pmTo
    };
    this.valueTo = timeT;

    this.result = JSON.stringify({ from: jTimeFrom, to: jTimeTo });
    this.propagateChange({ from: jTimeFrom, to: jTimeTo });
  }

  ngOnChanges(changes: SimpleChanges) {
    for (const timeValue in changes) {
      const change = changes[timeValue];

      const valueTo = JSON.stringify(change.currentValue);
      const valueFrom = JSON.stringify(change.previousValue);
      const changeValue = `${timeValue}: currentValue = ${valueTo}, previousValue = ${valueFrom}`;
    }
  }

  ngOnInit() {
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.datetimerange.setValidators(this.control.validator);
      this.datetimerange.setAsyncValidators(this.control.asyncValidator);
    }

    this.timepickercust.showMeridian = this.showMeridian;
    this.timepickercust.showSeconds = this.showSeconds;

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
  }
}
