import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './module/auth/login/login.component';
import { AppRoutingModule } from './app-routing.module';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './module/common/home/home.component';
import { AuthService, BffApiModule } from 'bff-api';
import { LoggerModule } from 'ngx-logger';
import { environment } from '@environments/environment';

const appRoutes: Routes = [
]

@NgModule({
    declarations: [
        AppComponent,
        LoginComponent,
        HomeComponent
    ],
    imports: [
        HttpClientModule,
        BrowserModule,
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
        BffApiModule.forRoot({
            url: environment.apiUrl,
            portalType: environment.portalType
        })
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {}
