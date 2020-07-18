import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BffApiModule } from 'bff-api';
import { UiMasterModule } from 'ui-master';
import { AppState } from './app.state';
import { HistoryInfoComponent } from './history/history-info/history-info.component';
import { HistoryListComponent } from './history/history-list/history-list.component';
import { InboxComposeComponent } from './inbox/inbox-compose/inbox-compose.component';
import { InboxDetailComponent } from './inbox/inbox-detail/inbox-detail.component';
import { InboxListComponent } from './inbox/inbox-list/inbox-list.component';
import { InboxComponent } from './inbox/inbox.component';
import { PipesModule } from './pipes/pipes.module';
import { PoolInfoComponent } from './pool/pool-info/pool-info.component';
import { PoolListComponent } from './pool/pool-list/pool-list.component';

export const routes = [
    {
        path: '', component: InboxComponent,
        children: [
            { path: 'inbox', component: InboxListComponent },
            { path: 'pool', component: PoolListComponent },
            { path: 'history', component: HistoryListComponent },
            { path: 'inboxCompose', component: InboxComposeComponent },
            { path: 'starred', component: InboxDetailComponent },
            { path: 'sent', component: InboxDetailComponent },
            { path: 'drafts', component: InboxDetailComponent },
            { path: 'trash', component: InboxDetailComponent },
        ]
    },
];

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        PipesModule,
        NgbModule.forRoot(),
        BffApiModule.forChild(),
        UiMasterModule.forChild(),
        RouterModule.forChild(routes)
    ],
    declarations: [
        InboxComponent,
        InboxListComponent,
        PoolListComponent,
        HistoryListComponent,
        InboxComposeComponent,
        InboxDetailComponent,
        HistoryInfoComponent,
        PoolInfoComponent
    ],
    schemas: [
        CUSTOM_ELEMENTS_SCHEMA
    ],
    providers: [AppState]
})
export class MailModule { }
