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
  selector: 'ui-time',
  templateUrl: './time.component.html',
  styleUrls: ['./time.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => TimeComponent), multi: true }]
})
export class TimeComponent implements OnInit {
  constructor(@Optional() @Host() @SkipSelf() private controlContainer: ControlContainer) {
    this.timeForm = new FormControl();
  }
  @Input() showMeridian: boolean;
  @Input() showSeconds: boolean;
  @Input() formControlName: string;
  @Input() hourStep: any;
  @Input() minuteStep: any;
  @Input() reset: boolean;
  @ViewChild(TimepickerComponent) timepickercust: TimepickerComponent;

  timeForm: FormControl;
  time: Date;
  timeVal: any;
  timeLocal: 'en-US';
  placeholder = 'HH:MM:SS a';
  maxlength: any;

  private control: AbstractControl;

  onTouched = () => {};
  propagateChange = (_: any) => {};

  writeValue(obj: any): void {
    if (this.reset && this.timeForm.touched) {
      this.timeForm.markAsUntouched();
    }
    this.timeForm.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (isDisabled) {
      this.timeForm.disable();
    } else {
      this.timeForm.enable();
    }
  }

  applyValue(myDropdown: HTMLElement) {
    if (this.time === undefined) {
      this.time = new Date();
    }
    if (this.showMeridian === true) {
      this.timeVal = this.time.toLocaleTimeString();
    } else if (this.showMeridian === false) {
      this.timeVal = this.time.toLocaleTimeString(this.timeLocal, { hour12: false });
    }
    this.timeForm.setValue(this.timeVal);
    this.propagateChange(this.timeVal);
    myDropdown.classList.remove('show');
  }

  onClear() {
    this.timeForm.setValue('');
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
      this.timeForm.setValidators(this.control.validator);
      this.timeForm.setAsyncValidators(this.control.asyncValidator);
    }
    this.timepickercust.showMeridian = this.showMeridian;
    this.timepickercust.showSeconds = this.showSeconds;
  }
}
