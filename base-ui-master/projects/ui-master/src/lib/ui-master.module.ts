import { CommonModule } from '@angular/common';
import { CUSTOM_ELEMENTS_SCHEMA, ModuleWithProviders, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ComponentModule } from './component/component.module';
import { CustomModule } from './custom/custom.module';
import { NotifierModule } from './notifier/notifier.module';
import { SharedModule } from './shared/shared.module';
import { UiMasterComponent } from './ui-master.component';
import { UiMasterConfig } from './ui-master.config';
import { UtilityModule } from './utility/utility.module';

@NgModule({
  declarations: [UiMasterComponent],
  imports: [
    CommonModule,
    NgbModule,
    RouterModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
    NotifierModule,
    ComponentModule,
    CustomModule,
    UtilityModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  // providers: [NgbActiveModal],
  exports: [UiMasterComponent, SharedModule, NotifierModule, ComponentModule, CustomModule, UtilityModule]
})
export class UiMasterModule {
  static forRoot(config: UiMasterConfig | null | undefined): ModuleWithProviders {
    return {
      ngModule: UiMasterModule,
      providers: [{ provide: UiMasterConfig, useValue: config || {} }]
    };
  }

  static forChild(): ModuleWithProviders {
    return {
      ngModule: UiMasterModule
    };
  }

  constructor() { }
}
export * from './component/datatable/datatable.config';
export * from './component/select/select.config';
export * from './shared/theme-switcher/theme-switcher.config';
export * from './custom/wizard/public_api';
export * from './component/calendar/calendar.config';
export * from './component/image-cropper/image-cropper.config';
