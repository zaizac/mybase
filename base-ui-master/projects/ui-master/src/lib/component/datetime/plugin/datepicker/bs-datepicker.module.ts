import { CommonModule } from '@angular/common';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { ComponentLoaderFactory } from 'ngx-bootstrap/component-loader';
import { PositioningService } from 'ngx-bootstrap/positioning';

import { BsDatepickerInputDirective } from './bs-datepicker-input.directive';
import { BsDatepickerDirective } from './bs-datepicker.component';
import { BsDatepickerConfig } from './bs-datepicker.config';
import { BsDaterangepickerInputDirective } from './bs-daterangepicker-input.directive';
import { BsDaterangepickerDirective } from './bs-daterangepicker.component';
import { BsDaterangepickerConfig } from './bs-daterangepicker.config';
import { BsDatepickerInlineDirective } from './bs-datepicker-inline.component';
import { BsDatepickerInlineConfig } from './bs-datepicker-inline.config';

import { BsLocaleService } from './bs-locale.service';
import { BsDatepickerActions } from './reducer/bs-datepicker.actions';
import { BsDatepickerEffects } from './reducer/bs-datepicker.effects';
import { BsDatepickerStore } from './reducer/bs-datepicker.store';
import { BsCalendarLayoutComponent } from './themes/bs/bs-calendar-layout.component';
import { BsCurrentDateViewComponent } from './themes/bs/bs-current-date-view.component';
import { BsCustomDatesViewComponent } from './themes/bs/bs-custom-dates-view.component';
import { BsDatepickerContainerComponent } from './themes/bs/bs-datepicker-container.component';
import { BsDatepickerDayDecoratorComponent } from './themes/bs/bs-datepicker-day-decorator.directive';
import { BsDatepickerNavigationViewComponent } from './themes/bs/bs-datepicker-navigation-view.component';
import { BsDaterangepickerContainerComponent } from './themes/bs/bs-daterangepicker-container.component';
import { BsDaysCalendarViewComponent } from './themes/bs/bs-days-calendar-view.component';
import { BsMonthCalendarViewComponent } from './themes/bs/bs-months-calendar-view.component';
import { BsTimepickerViewComponent } from './themes/bs/bs-timepicker-view.component';
import { BsYearsCalendarViewComponent } from './themes/bs/bs-years-calendar-view.component';
import { BsDatepickerInlineContainerComponent } from './themes/bs/bs-datepicker-inline-container.component';
import { DateTimepickerModule } from '../datetimepicker/datetimepicker.module';
import { BsDaterangepickerInlineContainerComponent } from './themes/bs/bs-daterangepicker-inline-container.component';
import { BsDaterangepickerInlineDirective } from './bs-daterangepicker-inline.component';
import { BsDaterangepickerInlineConfig } from './bs-daterangepicker-inline.config';

const _exports = [
  BsDatepickerContainerComponent,
  BsDaterangepickerContainerComponent,
  BsDatepickerInlineContainerComponent,
  BsDaterangepickerInlineContainerComponent,

  BsDatepickerDirective,
  BsDatepickerInputDirective,

  BsDaterangepickerInputDirective,
  BsDaterangepickerDirective,

  BsDatepickerInlineDirective,
  BsDaterangepickerInlineDirective
];

@NgModule({
  imports: [CommonModule, DateTimepickerModule.forRoot()],
  declarations: [
    BsDatepickerDayDecoratorComponent,
    BsCurrentDateViewComponent,
    BsDatepickerNavigationViewComponent,
    BsTimepickerViewComponent,

    BsCalendarLayoutComponent,
    BsDaysCalendarViewComponent,
    BsMonthCalendarViewComponent,
    BsYearsCalendarViewComponent,

    BsCustomDatesViewComponent,

    ..._exports
  ],
  entryComponents: [
    BsDatepickerContainerComponent,
    BsDaterangepickerContainerComponent,
    BsDatepickerInlineContainerComponent,
    BsDaterangepickerInlineContainerComponent
  ],
  exports: _exports
})
export class BsDatepickerModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: BsDatepickerModule,
      providers: [
        ComponentLoaderFactory,
        PositioningService,
        BsDatepickerStore,
        BsDatepickerActions,
        BsDatepickerConfig,
        BsDaterangepickerConfig,
        BsDatepickerInlineConfig,
        BsDatepickerEffects,
        BsLocaleService,
        BsDaterangepickerInlineConfig
      ]
    };
  }
}

export { BsDatepickerConfig } from './bs-datepicker.config';
export { BsDatepickerDirective } from './bs-datepicker.component';
export { BsDatepickerInlineConfig } from './bs-datepicker-inline.config';
export { BsDatepickerInlineDirective } from './bs-datepicker-inline.component';
export { BsDatepickerViewMode, DatepickerDateCustomClasses } from './models';
export { BsDaterangepickerConfig } from './bs-daterangepicker.config';
export { BsDaterangepickerDirective } from './bs-daterangepicker.component';
export { BsLocaleService } from './bs-locale.service';
export { BsDaterangepickerInlineConfig } from './bs-daterangepicker-inline.config';
