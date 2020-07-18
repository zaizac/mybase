import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccordionsComponent } from './sample-pages/component/accordions/accordions.component';
import { ButtonsComponent } from './sample-pages/component/buttons/buttons.component';
import { DtCardComponent } from './sample-pages/component/datatable/dt-card/dt-card.component';
import { DtCheckboxRadioComponent } from './sample-pages/component/datatable/dt-checkbox-radio/dt-checkbox-radio.component';
import { DtConfigComponent } from './sample-pages/component/datatable/dt-config/dt-config.component';
import { DtEventsComponent } from './sample-pages/component/datatable/dt-events/dt-events.component';
import { DtExpandablerowComponent } from './sample-pages/component/datatable/dt-expandablerow/dt-expandablerow.component';
import { DtRenderComponent } from './sample-pages/component/datatable/dt-render/dt-render.component';
import { DtSearchResetComponent } from './sample-pages/component/datatable/dt-search-reset/dt-search-reset.component';
import { DtTemplateComponent } from './sample-pages/component/datatable/dt-template/dt-template.component';
import { DtbasicComponent } from './sample-pages/component/datatable/dtbasic/dtbasic.component';
import { OthersComponent } from './sample-pages/component/others/others.component';
import { SelectDefaultComponent } from './sample-pages/component/select/select-default/select-default.component';
import { SelectMultiComponent } from './sample-pages/component/select/select-multi/select-multi.component';
import { TabsetComponent } from './sample-pages/component/tabset/tabset/tabset.component';
import { FileUploadComponent } from './sample-pages/file-upload/file-upload.component';
import { FormBasicComponent } from './sample-pages/form/form-basic/form-basic.component';
import { FormValidationComponent } from './sample-pages/form/form-validation/form-validation.component';
import { FormWizardGuideComponent } from './sample-pages/form/form-wizard-guide/form-wizard-guide.component';
import { ErrorComponent, HomeComponent } from './shared/page';
import { DateComponent } from './sample-pages/component/datetime/date/date.component';
import { TimeComponent } from './sample-pages/component/datetime/time/time.component';
import { TimerangeComponent } from './sample-pages/component/datetime/timerange/timerange.component';
import { DaterangeComponent } from './sample-pages/component/datetime/daterange/daterange.component';
import { DatetimeComponent } from './sample-pages/component/datetime/datetime/datetime.component';
import { DatetimerangeComponent } from './sample-pages/component/datetime/datetimerange/datetimerange.component';
import { DaterangecustomComponent } from './sample-pages/component/datetime/daterangecustom/daterangecustom.component';
import { RatingComponent } from './sample-pages/component/rating/rating.component';
import { CalendarComponent } from './sample-pages/component/calendar/calendar.component';
import { ImagecropperComponent } from './sample-pages/component/imagecropper/imagecropper.component';
import { SpinningComponent } from './sample-pages/component/spinning/spinning.component';
import { DuallistComponent } from './sample-pages/component/duallist/duallist.component';
import { QrcodeComponent } from './sample-pages/component/qrcode/qrcode.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  {
    path: 'components',
    data: { breadcrumb: 'Components' },
    children: [
      {
        path: 'datatable',
        data: { breadcrumb: 'Datatable' },
        children: [
          {
            path: 'basic',
            component: DtbasicComponent,
            data: { breadcrumb: 'Basic Datatable' }
          },
          {
            path: 'configuration',
            component: DtConfigComponent,
            data: { breadcrumb: 'Datatable Configuration' }
          },
          {
            path: 'searchandreset',
            component: DtSearchResetComponent,
            data: { breadcrumb: 'Search and Reset' }
          },
          {
            path: 'checkboxandradio',
            component: DtCheckboxRadioComponent,
            data: { breadcrumb: 'Checkbox and Radio Button' }
          },
          {
            path: 'render',
            component: DtRenderComponent,
            data: { breadcrumb: 'Column Rendering' }
          },
          {
            path: 'templating',
            component: DtTemplateComponent,
            data: { breadcrumb: 'Templating' }
          },
          {
            path: 'events',
            component: DtEventsComponent,
            data: { breadcrumb: 'Datatable Events' }
          },
          {
            path: 'expandablerow',
            component: DtExpandablerowComponent,
            data: { breadcrumb: 'Expandable Row' }
          },
          {
            path: 'card',
            component: DtCardComponent,
            data: { breadcrumb: 'Card' }
          }
        ]
      },
      {
        path: 'picker',
        data: { breadcrumb: 'Date & Time Picker' },
        children: [
          {
            path: 'date',
            component: DateComponent,
            data: { breadcrumb: 'Date' }
          },
          {
            path: 'daterange',
            component: DaterangeComponent,
            data: { breadcrumb: 'Date Range' }
          },
          {
            path: 'time',
            component: TimeComponent,
            data: { breadcrumb: 'Time' }
          },
          {
            path: 'timerange',
            component: TimerangeComponent,
            data: { breadcrumb: 'Time Range' }
          },
          {
            path: 'datetime',
            component: DatetimeComponent,
            data: { breadcrumb: 'Date & Time' }
          },
          {
            path: 'datetimerange',
            component: DatetimerangeComponent,
            data: { breadcrumb: 'Date & Time Range' }
          },
          {
            path: 'daterangecustom',
            component: DaterangecustomComponent,
            data: { breadcrumb: 'Custom Date Range' }
          }
        ]
      },
      { path: 'fileupload', data: { breadcrumb: 'File Upload' }, component: FileUploadComponent },
      { path: 'imagecropper', data: { breadcrumb: 'Image Cropper' }, component: ImagecropperComponent },
      { path: 'others', data: { breadcrumb: 'Others' }, component: OthersComponent },
      { path: 'tabs', data: { breadcrumb: 'Tabs' }, component: TabsetComponent },
      { path: 'accordion', data: { breadcrumb: 'Accordion' }, component: AccordionsComponent },
      { path: 'buttons', data: { breadcrumb: 'Buttons' }, component: ButtonsComponent },
      { path: 'rating', data: { breadcrumb: 'Rating' }, component: RatingComponent },
      { path: 'spinner', data: { breadcrumb: 'Spinner' }, component: SpinningComponent },
      { path: 'duallist', data: { breadcrumb: 'Dual List Box'}, component: DuallistComponent},
      { path: 'qrcode', data: { breadcrumb: 'QrCode'}, component: QrcodeComponent},
      {
        path: 'select',
        data: { breadcrumb: 'Select' },
        children: [
          {
            path: 'basic',
            component: SelectDefaultComponent,
            data: { breadcrumb: 'Basic Select' }
          },
          {
            path: 'multi',
            component: SelectMultiComponent,
            data: { breadcrumb: 'Multiselect' }
          }
        ]
      },
      { path: 'calendar', data: { breadcrumb: 'Calendar' }, component: CalendarComponent }
    ]
  },
  {
    path: 'form/basic',
    component: FormBasicComponent,
    data: { breadcrumb: 'Basic Form' }
  },
  {
    path: 'form/validation',
    component: FormValidationComponent,
    data: { breadcrumb: 'Form Validation' }
  },
  {
    path: 'form/wizard',
    data: {
      breadcrumb: 'Form Wizard',
      title: 'Form-Wizard'
    },
    children: [
      {
        path: 'guide',
        component: FormWizardGuideComponent,
        data: { breadcrumb: 'Wizard Step By Step Guide' }
      },
      {
        path: 'horizontal',
        loadChildren: './sample-pages/form/form-wizard-horizontal/form-wizard.module#FormWizardModule'
      },
      {
        path: 'vertical',
        loadChildren: './sample-pages/form/form-wizard-vertical/form-wizard-vertical.module#FormWizardVerticalModule'
      }
    ]
  },
  {
    path: '**',
    component: ErrorComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {}
