import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';
import { NgSelectModule } from '@ng-select/ng-select';
import { Ng5SliderModule } from 'ng5-slider';
import { GridAllModule } from '@syncfusion/ej2-angular-grids';

import { NumberComponent } from './number/number.component';
import { ButtonComponent } from './button/button.component';
import { SelectComponent } from './select/select.component';
import { PasswordComponent } from './password/password.component';
import { CheckboxComponent } from './checkbox/checkbox.component';
import { RadioComponent } from './radio/radio.component';
import { RadioGroupDirective } from './radio/radio-group.directive';
import { ChecklistComponent } from './checklist/checklist.component';
import { TextComponent } from './text/text.component';
import { TabComponent } from './tabs/tab/tab.component';
import { TabsDirective } from './tabs/tabs.directive';
import { AccordionComponent } from './accordion/accordion.component';
import { AccordionGroupComponent } from './accordion/group/accordion-group.component';
import { TextareaComponent } from './textarea/textarea.component';
import { TabGroupComponent } from './tabs/tab-group.component';
import { NumberRangeComponent } from './number-range/number-range.component';
import { IntlTelModule } from '../custom/mobile/intl-tel/intl-tel.module';
import { FileComponent } from './file/file.component';
import { DateComponent } from './datetime/date/date.component';
import { DateRangeComponent } from './datetime/daterange/date-range.component';
import { DatetimeComponent } from './datetime/datetime/datetime.component';
import { DateTimeRangeComponent } from './datetime/datetimerange/datetime-range.component';
import { DaterangecustomComponent } from './datetime/daterangecustom/daterangecustom.component';
import { UtilityModule } from '../utility/utility.module';
import { TimeComponent } from './datetime/time/time.component';
import { TimeRangeComponent } from './datetime/timerange/time-range.component';
import { DatatableComponent } from './datatable/datatable.component';
import { CellTemplateDirective } from './datatable/cell-template.directive';
import { DateTimepickerModule } from './datetime/plugin/datetimepicker/datetimepicker.module';
import { TimepickerModule } from './datetime/plugin/timepicker/timepicker.module';
import { DatepickerModule } from './datetime/plugin/datepicker/datepicker.module';
import { BsDatepickerModule } from './datetime/plugin/datepicker/bs-datepicker.module';
import { CardComponent } from './card/card/card.component';
import { CardTitleComponent } from './card/card-title/card-title.component';
import { RatingComponent } from './rating/rating.component';
import { CalendarComponent } from './calendar/calendar.component';
import { CalendarModule } from 'angular-calendar';
import { CalendarYearViewComponent } from './calendar/calendar-year-view/calendar-year-view.component';
import { ImageCropperComponent } from './image-cropper/image-cropper.component';
import { OpenDayEventPipe } from './calendar/open-day-event.pipe';
import { DuallistboxComponent } from './duallistbox/duallistbox.component';
import { CheckboxGroupComponent } from './checkbox-group/checkbox-group.component';
import { QrcodeComponent } from './qrcode/qrcode.component';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule.forRoot(),
    RouterModule,
    CalendarModule.forRoot(),
    IntlTelModule.forRoot(),
    NgSelectModule, // for Select dropdown
    BsDatepickerModule.forRoot(),
    DatepickerModule.forRoot(),
    DateTimepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    Ng5SliderModule,
    GridAllModule,
    UtilityModule
  ],
  declarations: [
    NumberComponent,
    CardComponent,
    CardTitleComponent,
    ButtonComponent,
    SelectComponent,
    PasswordComponent,
    CheckboxComponent,
    RadioComponent,
    RadioGroupDirective,
    ChecklistComponent,
    TextComponent,
    TabComponent,
    TabsDirective,
    AccordionComponent,
    AccordionGroupComponent,
    TextareaComponent,
    TabGroupComponent,
    NumberRangeComponent,
    FileComponent,
    DateComponent,
    DateRangeComponent,
    DatetimeComponent,
    DateTimeRangeComponent,
    DaterangecustomComponent,
    TimeComponent,
    TimeRangeComponent,
    DatatableComponent,
    CellTemplateDirective,
    RatingComponent,
    CalendarComponent,
    CalendarYearViewComponent,
    ImageCropperComponent,
    OpenDayEventPipe,
    DuallistboxComponent,
    CheckboxGroupComponent,
    QrcodeComponent
  ],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  bootstrap: [DateComponent],
  exports: [
    NumberComponent,
    CardComponent,
    CardTitleComponent,
    ButtonComponent,
    SelectComponent,
    PasswordComponent,
    CheckboxComponent,
    RadioComponent,
    RadioGroupDirective,
    ChecklistComponent,
    TextComponent,
    TabComponent,
    TabsDirective,
    AccordionComponent,
    AccordionGroupComponent,
    TextareaComponent,
    TabGroupComponent,
    NumberRangeComponent,
    FileComponent,
    DateComponent,
    DateRangeComponent,
    DatetimeComponent,
    DateTimeRangeComponent,
    DaterangecustomComponent,
    TimeComponent,
    TimeRangeComponent,
    DatatableComponent,
    CellTemplateDirective,
    RatingComponent,
    CalendarComponent,
    ImageCropperComponent,
    OpenDayEventPipe,
    DuallistboxComponent,
    CheckboxGroupComponent,
    QrcodeComponent
  ]
})
export class ComponentModule {}
