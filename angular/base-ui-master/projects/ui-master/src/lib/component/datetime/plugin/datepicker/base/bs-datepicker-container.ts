// datepicker container component
/* tslint:disable:no-empty */
import { BsCustomDates, ViewDateValue, Timevalue } from '../themes/bs/bs-custom-dates-view.component';
import { BsDatepickerEffects } from '../reducer/bs-datepicker.effects';
import { Observable } from 'rxjs';
import {
  BsDatepickerViewMode,
  BsNavigationEvent,
  CalendarCellViewModel,
  CellHoverEvent,
  DatepickerRenderOptions,
  DaysCalendarViewModel,
  DayViewModel,
  MonthsCalendarViewModel,
  WeekViewModel,
  YearsCalendarViewModel,
  DatepickerDateCustomClasses
} from '../models';

export abstract class BsDatepickerAbstractComponent {
  containerClass: string;
  isOtherMonthsActive: boolean;

  _effects: BsDatepickerEffects;
  _customRangesFish: BsCustomDates[] = [];

  customDates: BsCustomDates[] = [];
  viewDate: ViewDateValue[] = [];
  showTime: boolean;
  rangeShowTime: boolean;
  dateButtons: boolean;
  time: Timevalue[] = [];
  //dateCustomClasses: DatepickerDateCustomClasses[] = [];

  set minDate(value: Date) {
    this._effects.setMinDate(value);
  }

  set maxDate(value: Date) {
    this._effects.setMaxDate(value);
  }
  set daysDisabled(value: number[]) {
    this._effects.setDaysDisabled(value);
  }
  set datesDisabled(value: Date[]) {
    this._effects.setDatesDisabled(value);
  }

  set isDisabled(value: boolean) {
    this._effects.setDisabled(value);
  }

  set dateCustomClasses(value: DatepickerDateCustomClasses[]) {
    this._effects.setDateCustomClasses(value);
  }

  viewMode: Observable<BsDatepickerViewMode>;
  daysCalendar: Observable<DaysCalendarViewModel[]>;
  monthsCalendar: Observable<MonthsCalendarViewModel[]>;
  yearsCalendar: Observable<YearsCalendarViewModel[]>;
  options: Observable<DatepickerRenderOptions>;

  setViewMode(event: BsDatepickerViewMode): void {}

  navigateTo(event: BsNavigationEvent): void {}

  dayHoverHandler(event: CellHoverEvent): void {}

  weekHoverHandler(event: WeekViewModel): void {}

  monthHoverHandler(event: CellHoverEvent): void {}

  yearHoverHandler(event: CellHoverEvent): void {}

  daySelectHandler(day: DayViewModel): void {}

  monthSelectHandler(event: CalendarCellViewModel): void {}

  yearSelectHandler(event: CalendarCellViewModel): void {}

  /* tslint:disable-next-line: no-any */
  _stopPropagation(event: any): void {
    event.stopPropagation();
  }
}
