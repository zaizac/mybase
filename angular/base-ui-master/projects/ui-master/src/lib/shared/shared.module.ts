import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';
import { PERFECT_SCROLLBAR_CONFIG, PerfectScrollbarConfigInterface, PerfectScrollbarModule } from 'ngx-perfect-scrollbar';

import { BreadcrumbComponent } from './breadcrumb/breadcrumb.component';
import { MenuComponent } from './menu/menu.component';
import { NavbarComponent } from './navbar/navbar.component';
import { NavlangComponent } from './navlang/navlang.component';
import { NavuserComponent } from './navuser/navuser.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { ThemeSwitcherComponent } from './theme-switcher/theme-switcher.component';
import { NotifierModule } from '../notifier/notifier.module';
import { ComponentModule } from '../component/component.module';
import { IntlTelModule } from '../custom/mobile/intl-tel/intl-tel.module';
import { SpinnerOverlayComponent } from './spinner/spinner-overlay/spinner-overlay.component';
import { SpinnerComponent } from './spinner/spinner/spinner.component';


const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
    suppressScrollX: true,
    wheelSpeed: 2,
    wheelPropagation: true
};

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        NgbModule.forRoot(),
        RouterModule,
        PerfectScrollbarModule,
        NotifierModule,
        IntlTelModule,
        ComponentModule
    ],
    declarations: [
        BreadcrumbComponent,
        MenuComponent,
        ThemeSwitcherComponent,
        NavbarComponent,
        NavlangComponent,
        NavuserComponent,
        HeaderComponent,
        FooterComponent,
        SpinnerComponent,
        SpinnerOverlayComponent
    ],
    providers: [
        {
            provide: PERFECT_SCROLLBAR_CONFIG,
            useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
        }
    ],
    schemas: [
        CUSTOM_ELEMENTS_SCHEMA
    ],
    exports: [
        BreadcrumbComponent,
        MenuComponent,
        ThemeSwitcherComponent,
        NavbarComponent,
        NavlangComponent,
        NavuserComponent,
        HeaderComponent,
        FooterComponent,
        SpinnerOverlayComponent
    ],
})
export class SharedModule { }
