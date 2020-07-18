import {
  BsDatepickerViewMode,
  DatepickerFormatOptions,
  DatepickerRenderOptions,
  DaysCalendarModel,
  DaysCalendarViewModel,
  MonthsCalendarViewModel,
  MonthViewOptions,
  YearsCalendarViewModel,
  DatepickerDateCustomClasses
} from '../models';
import { defaultMonthOptions } from './_defaults';
import { BsDatepickerConfig } from '../bs-datepicker.config';

export interface BsDatepickerViewState {
  date: Date;
  mode: BsDatepickerViewMode;
}

export class BsDatepickerState
  implements DatepickerRenderOptions, DatepickerFormatOptions {
  // date picker
  selectedDate?: Date;
  // daterange picker
  selectedRange?: Date[];

  // initial date of calendar, today by default
  view: BsDatepickerViewState;

  isDisabled?: boolean;
  // bounds
  minDate?: Date;
  maxDate?: Date;
  daysDisabled?: number[];
  datesDisabled?: Date[];
  minMode?: BsDatepickerViewMode;
  dateCustomClasses?: DatepickerDateCustomClasses[];

  hoveredDate?: Date;
  hoveredMonth?: Date;
  hoveredYear?: Date;

  // days calendar
  monthsModel?: DaysCalendarModel[];
  formattedMonths?: DaysCalendarViewModel[];
  flaggedMonths?: DaysCalendarViewModel[];
  selectFromOtherMonth?: boolean;

  // months calendar
  monthsCalendar?: MonthsCalendarViewModel[];
  flaggedMonthsCalendar?: MonthsCalendarViewModel[];

  // years calendar
  yearsCalendarModel?: YearsCalendarViewModel[];
  yearsCalendarFlagged?: YearsCalendarViewModel[];

  // options
  monthViewOptions: MonthViewOptions;

  // DatepickerRenderOptions
  showWeekNumbers?: boolean;
  displayMonths?: number;

  // DatepickerFormatOptions
  locale: string;

  monthTitle: string;
  yearTitle: string;

  dayLabel: string;
  monthLabel: string;
  yearLabel: string;

  weekNumbers: string;
}

const _initialView: BsDatepickerViewState = { date: new Date(), mode: 'day' };

export const initialDatepickerState: BsDatepickerState = Object.assign(
  new BsDatepickerConfig(),
  {
    locale: 'en',
    view: _initialView,
    selectedRange: [],
    monthViewOptions: defaultMonthOptions
  }
);
