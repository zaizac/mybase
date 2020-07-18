import { NgModule, ModuleWithProviders } from '@angular/core';
import { IntlTelComponent } from './intl-tel.component';
import { CommonModule } from '@angular/common';
import { BsDropdownModule, TooltipModule } from 'ngx-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IntlTelService } from './intl-tel.service';

@NgModule({
	declarations: [IntlTelComponent],
	imports: [
		CommonModule,
		FormsModule,
		ReactiveFormsModule,
		BsDropdownModule.forRoot(),
		TooltipModule.forRoot()
	],
	exports: [IntlTelComponent]
})
export class IntlTelModule {
	static forRoot(): ModuleWithProviders {
		return {
			ngModule: IntlTelModule,
			providers: [IntlTelService]
		};
	}
}
