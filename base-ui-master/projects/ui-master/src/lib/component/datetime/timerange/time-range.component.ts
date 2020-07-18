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
import { TimepickerComponent } from '../plugin/timepicker/timepicker.module';

@Component({
  selector: 'ui-time-range',
  templateUrl: './time-range.component.html',
  styleUrls: ['./time-range.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => TimeRangeComponent), multi: true }]
})
export class TimeRangeComponent implements OnInit {
  constructor(@Optional() @Host() @SkipSelf() private controlContainer: ControlContainer) {
    this.time = new FormControl();
  }

  @Input() showMeridian: boolean;
  @Input() showSeconds: boolean;
  @Input() hourStep: any;
  @Input() minuteStep: any;
  @Input() formControlName: string;
  @ViewChild(TimepickerComponent) timepickercust: TimepickerComponent;

  reset: boolean = false;
  timefrom: Date;
  timeto: Date;

  timef: any;
  timet: any;

  timeLocal: 'en-US';

  hourFrom: any;
  minuteFrom: any;
  secondFrom: any;
  pmFrom: any;
  valueFrom: any;

  hourTo: any;
  minuteTo: any;
  secondTo: any;
  pmTo: any;
  valueTo: any;

  result: string | object;

  time: FormControl;

  private control: AbstractControl;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  writeValue(obj: any): void {
    if (this.reset && this.time.touched) {
      this.time.markAsUntouched();
    }
    this.time.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (isDisabled) {
      this.time.disable();
    } else {
      this.time.enable();
    }
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
      this.timeFormat(this.timef, this.timet);
    } else if (this.showMeridian === false) {
      this.timef = this.timefrom.toLocaleTimeString(this.timeLocal, { hour12: false });
      this.timet = this.timeto.toLocaleTimeString(this.timeLocal, { hour12: false });
      this.timeFormat(this.timef, this.timet);
    }
    myDropdown.classList.remove('show');
  }

  timeFormat(timeF: any, timeT: any) {
    const timeSplitFrom = timeF.split(':');
    this.hourFrom = parseInt(timeSplitFrom[0]);
    this.minuteFrom = parseInt(timeSplitFrom[1]);
    this.secondFrom = parseInt(timeSplitFrom[2].split(' ')[0]);
    this.pmFrom = timeSplitFrom[2].split(' ')[1];
    const jTimeFrom = {
      value: timeF,
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
      value: timeT,
      hour: this.hourTo,
      minute: this.minuteTo,
      second: this.secondTo,
      meridian: this.pmTo
    };
    this.valueTo = timeT;

    if (this.timeto.getTime() < this.timefrom.getTime()) {
      this.timeto.setDate(this.timeto.getDate() + 1);
    }
    const Diff = Math.abs(this.timefrom.getTime() - this.timeto.getTime());
    const HourDiff = Math.floor(Diff / (60 * 60 * 1000));
    const MinuteDiff = Math.floor((Diff % (60 * 60 * 1000)) / (60 * 1000));
    const SecDiff = Math.floor(((Diff % (60 * 60 * 1000)) % (60 * 1000)) / 1000);

    const TimeDiff = HourDiff + ':' + MinuteDiff + ':' + SecDiff;
    const jTimeDiff = { value: TimeDiff, hour: HourDiff, minute: MinuteDiff, second: SecDiff };

    //this.result = JSON.stringify({ from: jTimeFrom, to: jTimeTo, difference: jTimeDiff });
    this.propagateChange({ from: jTimeFrom, to: jTimeTo, difference: jTimeDiff });
    this.time.setValue(this.valueFrom + ' - ' + this.valueTo);
  }

  onClear() {
    this.time.setValue('');
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
      this.time.setValidators(this.control.validator);
      this.time.setAsyncValidators(this.control.asyncValidator);
    }
    this.timepickercust.showMeridian = this.showMeridian;
    this.timepickercust.showSeconds = this.showSeconds;
  }
}
