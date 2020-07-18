import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule, Routes } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Ng5SliderModule } from 'ng5-slider';
import { LoggerModule } from 'ngx-logger';
import { NgxSmartModalModule, NgxSmartModalService } from 'ngx-smart-modal';
import { UiMasterModule } from 'projects/ui-master/src/public-api';
import { environment } from '../environments/environment';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
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
import { DemodateComponent } from './sample-pages/demodate/demodate.component';
import { FileUploadComponent } from './sample-pages/file-upload/file-upload.component';
import { FormBasicComponent } from './sample-pages/form/form-basic/form-basic.component';
import { FormValidationComponent } from './sample-pages/form/form-validation/form-validation.component';
import { ErrorComponent, HomeComponent } from './shared/page';
import { DateComponent } from './sample-pages/component/datetime/date/date.component';
import { DaterangeComponent } from './sample-pages/component/datetime/daterange/daterange.component';
import { TimeComponent } from './sample-pages/component/datetime/time/time.component';
import { TimerangeComponent } from './sample-pages/component/datetime/timerange/timerange.component';
import { DatetimeComponent } from './sample-pages/component/datetime/datetime/datetime.component';
import { DatetimerangeComponent } from './sample-pages/component/datetime/datetimerange/datetimerange.component';
import { FormWizardGuideComponent } from './sample-pages/form/form-wizard-guide/form-wizard-guide.component';
import { DaterangecustomComponent } from './sample-pages/component/datetime/daterangecustom/daterangecustom.component';
import { RatingComponent } from './sample-pages/component/rating/rating.component';
import { CalendarComponent } from './sample-pages/component/calendar/calendar.component';
import { ImagecropperComponent } from './sample-pages/component/imagecropper/imagecropper.component';
import { ERRORS } from './shared/error/errors-mapping';
import { SpinningComponent } from './sample-pages/component/spinning/spinning.component';
import { DuallistComponent } from './sample-pages/component/duallist/duallist.component';
import { QrcodeComponent } from './sample-pages/component/qrcode/qrcode.component';

const appRoutes: Routes = [];

@NgModule({
  declarations: [
    AppComponent,
    DtbasicComponent,
    FileUploadComponent,
    DemodateComponent,
    HomeComponent,
    ErrorComponent,
    FormBasicComponent,
    SelectDefaultComponent,
    SelectMultiComponent,
    FormValidationComponent,
    OthersComponent,
    DtConfigComponent,
    DtSearchResetComponent,
    DtCheckboxRadioComponent,
    DtEventsComponent,
    DtRenderComponent,
    DtTemplateComponent,
    DtExpandablerowComponent,
    DtCardComponent,
    DtExpandablerowComponent,
    TabsetComponent,
    AccordionsComponent,
    ButtonsComponent,
    DateComponent,
    DaterangeComponent,
    TimeComponent,
    TimerangeComponent,
    DatetimeComponent,
    DatetimerangeComponent,
    FormWizardGuideComponent,
    DaterangecustomComponent,
    RatingComponent,
    CalendarComponent,
    ImagecropperComponent,
    SpinningComponent,
    DuallistComponent,
    QrcodeComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot(appRoutes),
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    RouterModule.forRoot(appRoutes),
    LoggerModule.forRoot({
      serverLoggingUrl: `${environment.apiUrl}/api/logs`,
      level: environment.logLevel,
      serverLogLevel: environment.serverLogLevel,
      disableConsoleLogging: false
    }),
    UiMasterModule.forRoot({
      url: environment.apiUrl,
      portalType: environment.portalType,
      ekey: environment.ekey,
      client: environment.client,
      skey: environment.skey,
      ERRORS: ERRORS
    }),
    NgbModule.forRoot(),
    NgxSmartModalModule.forRoot(),
    // BsDatepickerModule.forRoot(),
    Ng5SliderModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [NgxSmartModalService],
  bootstrap: [AppComponent]
})
export class AppModule { }
