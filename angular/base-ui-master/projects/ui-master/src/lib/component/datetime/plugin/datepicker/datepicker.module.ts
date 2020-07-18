import { CommonModule } from '@angular/common';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { DatePickerInnerComponent } from './datepicker-inner.component';
import { DatePickerComponent } from './datepicker.component';
import { DatepickerConfig } from './datepicker.config';
import { DayPickerComponent } from './daypicker.component';
import { MonthPickerComponent } from './monthpicker.component';
import { YearPickerComponent } from './yearpicker.component';
import { BsDatepickerModule } from './bs-datepicker.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    BsDatepickerModule
  ],
  declarations: [
    DatePickerComponent,
    DatePickerInnerComponent,
    DayPickerComponent,
    MonthPickerComponent,
    YearPickerComponent
  ],
  exports: [
    DatePickerComponent,
    DatePickerInnerComponent,
    DayPickerComponent,
    MonthPickerComponent,
    YearPickerComponent
  ],
  entryComponents: [DatePickerComponent]
})
export class DatepickerModule {
  static forRoot(): ModuleWithProviders {
    return { ngModule: DatepickerModule, providers: [DatepickerConfig] };
  }
}

export { DateFormatter } from './date-formatter';
export { DatePickerComponent } from './datepicker.component';
export { DatepickerConfig } from './datepicker.config';
export { DayPickerComponent } from './daypicker.component';
export { MonthPickerComponent } from './monthpicker.component';
export { YearPickerComponent } from './yearpicker.component';
export { BsCustomDates, ViewDateValue, ShowTimes } from './themes/bs/bs-custom-dates-view.component';
