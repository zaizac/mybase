import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { UiMasterModule } from 'ui-master';
import { BffApiModule } from 'bff-api';

import { ErrorComponent } from './page';
import { PlainComponent, FullComponent, BlankComponent } from './layouts';
import { RouterModule } from '@angular/router';
import { SpinnerComponent } from './spinner.component';

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        NgbModule.forRoot(),
        RouterModule,
        BffApiModule.forChild(),
        UiMasterModule.forChild()
    ],
    declarations: [
        BlankComponent,
        FullComponent,
        PlainComponent,
        ErrorComponent,
        SpinnerComponent
    ],
    schemas: [
        CUSTOM_ELEMENTS_SCHEMA
    ]
})
export class SharedModule { }
