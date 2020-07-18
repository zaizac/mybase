import { ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DatetimepickerComponent } from './datetimepicker.component';
import { DateTimepickerActions } from './reducer/datetimepicker.actions';
import { DatetimepickerConfig } from './datetimepicker.config';
import { DatetimepickerStore } from './reducer/datetimepicker.store';

@NgModule({
  imports: [CommonModule],
  declarations: [DatetimepickerComponent],
  exports: [DatetimepickerComponent]
})
export class DateTimepickerModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: DateTimepickerModule,
      providers: [DatetimepickerConfig, DateTimepickerActions, DatetimepickerStore]
    };
  }
}

export { DatetimepickerComponent } from './datetimepicker.component';
export { DateTimepickerActions } from './reducer/datetimepicker.actions';
export { DatetimepickerStore } from './reducer/datetimepicker.store';
export { DatetimepickerConfig } from './datetimepicker.config';