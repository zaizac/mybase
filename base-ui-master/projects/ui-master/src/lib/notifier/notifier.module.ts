import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbModule, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';
import { ToastrModule } from 'ngx-toastr';
import { NgxSmartModalModule } from 'ngx-smart-modal';

import { UtilityModule } from '../utility/utility.module';
import { ToastrComponent } from './toastr/toastr.component';
import { ModalComponent } from './modal/modal.component';
import { AlertComponent } from './alert/alert.component';
import { ComponentModule } from '../component/component.module';

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        NgbModule.forRoot(),
        RouterModule,
        NgxSmartModalModule.forRoot(),
        ToastrModule,
        UtilityModule,
        ComponentModule
    ],
    declarations: [
        ToastrComponent,
        AlertComponent,
        ModalComponent,
    ],
    schemas: [
        CUSTOM_ELEMENTS_SCHEMA
    ],
    providers: [NgbActiveModal],
    entryComponents: [ModalComponent],
    exports: [
        ToastrComponent,
        AlertComponent,
        ModalComponent,
    ],
})
export class NotifierModule { }
