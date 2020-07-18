import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BffApiModule, AuthTypeConstants, IdmMenuCodeConstants } from 'bff-api';
import { UiMasterModule } from 'ui-master';

import { ConfigListComponent } from './config/config-list/config-list.component';
import { TypeDetailsComponent } from './config/type/type-details/type-details.component';
import { StatusDetailsComponent } from './config/status/status-details/status-details.component';
import { LevelDetailsComponent } from './config/level/level-details/level-details.component';

export const routes = [
    {
        path: 'configuration',
        component: ConfigListComponent,
        data: { 
            authorization: {
                roletype: AuthTypeConstants.AUTHENTICATED,
                menuCode: IdmMenuCodeConstants.WFW_CONFIG
            },
            breadcrumb: 'Workflow Configuration' 
        },
    },
    {
        path: 'configuration',
        data: { 
            authorization: {
                roletype: AuthTypeConstants.AUTHENTICATED,
                menuCode: IdmMenuCodeConstants.WFW_CONFIG
            },
            breadcrumb: 'Workflow Configuration' 
        },
        children: [
            {
                path: 'type',
                component: TypeDetailsComponent,
                data: { breadcrumb: 'Type' },
            },
            {
                path: 'level',
                component: LevelDetailsComponent,
                data: { breadcrumb: 'Level' },
            },
            {
                path: 'status',
                component: StatusDetailsComponent,
                data: { breadcrumb: 'Status' },
            }
        ]
    },
];

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        NgbModule.forRoot(),
        BffApiModule.forChild(),
        UiMasterModule.forChild(),
        RouterModule.forChild(routes)
    ],
    declarations: [
        ConfigListComponent,
        TypeDetailsComponent,
        StatusDetailsComponent,
        LevelDetailsComponent
    ],
    schemas: [
        CUSTOM_ELEMENTS_SCHEMA
    ]
})
export class WfwModule { }
