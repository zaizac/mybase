import { NgModule, ModuleWithProviders } from '@angular/core';
import { HTTP_INTERCEPTORS } from '@angular/common/http';

import { BffApiComponent } from './bff-api.component';
import { BffApiConfig } from './bff-api.config';
import { ApiHttpInterceptor } from './http/http.interceptor';

@NgModule({
  declarations: [BffApiComponent],
  imports: [
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: ApiHttpInterceptor, multi: true }
  ],
  exports: [BffApiComponent]
})
export class BffApiModule {
  static forRoot(config: BffApiConfig | null | undefined): ModuleWithProviders {
    return {
      ngModule: BffApiModule,
      providers: [{ provide: BffApiConfig, useValue: config || {} },],
    };
  }

  static forChild(): ModuleWithProviders {
    return {
      ngModule: BffApiModule
    };
  }

  constructor() { }

}
export * from './http';
export * from './models';
export * from './constants';