import { HttpClientModule } from '@angular/common/http';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoggerModule } from 'ngx-logger';
import { PerfectScrollbarConfigInterface, PerfectScrollbarModule, PERFECT_SCROLLBAR_CONFIG } from 'ngx-perfect-scrollbar';
import { SessionExpirationAlert, SessionInteruptService } from 'session-expiration-alert';

// SHARED COMPONENTS
import { environment } from '@environments/environment';
import { BffApiModule } from 'bff-api';
import { UiMasterModule } from 'ui-master';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// DEFAULT MODULE
import { SharedModule } from '@appshared/shared.module';
import { IdmModule } from '@appmodule/idm/idm.module';
import { AppSessionInteruptService } from '@appmodule/idm/session/session.interupt.service';
import { WfwModule } from '@appmodule/wfw/wfw.module';
import { CmnModule } from '@appmodule/cmn/cmn.module';
import { ERRORS } from '@appshared/error/errors-mapping';
import { KeyValuePipe } from '@angular/common';

const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
    suppressScrollX: true,
    wheelSpeed: 2,
    wheelPropagation: true
};

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule.withServerTransition({ appId: 'serverApp' }),
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        AppRoutingModule,
        NgbModule.forRoot(),
        LoggerModule.forRoot({
            serverLoggingUrl: environment.apiUrl + '/api/logs',
            level: environment.logLevel,
            serverLogLevel: environment.serverLogLevel,
            disableConsoleLogging: false
        }),
        UiMasterModule.forRoot({
            serverLoggingUrl: environment.apiUrl + '/api/logs',
            webLogLevel: environment.logLevel,
            serverLogLevel: environment.serverLogLevel,
            disableConsoleLogging: false,
            url: environment.apiUrl,
            portalType: environment.portalType,
            ekey: environment.ekey,
            client: environment.client,
            skey: environment.skey,
            ERRORS: ERRORS
        }),
        BffApiModule.forRoot({
            serverLoggingUrl: environment.apiUrl + '/api/logs',
            webLogLevel: environment.logLevel,
            serverLogLevel: environment.serverLogLevel,
            disableConsoleLogging: false,
            url: environment.apiUrl,
            portalType: environment.portalType,
            client: environment.client,
            skey: environment.skey,
            isProd: environment.production
        }),
        SharedModule,
        IdmModule,
        WfwModule,
        CmnModule,
        PerfectScrollbarModule,
        SessionExpirationAlert.forRoot({ totalMinutes: environment.sessionTimeout })
    ],
    schemas: [
        CUSTOM_ELEMENTS_SCHEMA
    ],
    providers: [{
        provide: SessionInteruptService,
        useClass: AppSessionInteruptService
    }, {
        provide: PERFECT_SCROLLBAR_CONFIG,
        useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
    }, KeyValuePipe],
    bootstrap: [AppComponent]
})
export class AppModule { }
