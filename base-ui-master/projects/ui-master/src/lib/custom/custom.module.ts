import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';

import { AmountComponent } from '../component/amount/amount.component';
import { OtpComponent } from './otp/otp.component';
import { CaptchaComponent } from './captcha/captcha.component';
import { EmailComponent } from './email/email.component';
import { MobileComponent } from './mobile/mobile.component';
import { GenderComponent } from './gender/gender.component';
import { IntlTelModule } from './mobile/intl-tel/intl-tel.module';
import { ComponentModule } from '../component/component.module';
import { UtilityModule } from '../utility/utility.module';
import { NotifierModule } from '../notifier/notifier.module';
import { TitleComponent } from './title/title.component';
import { OtpEmailComponent } from './otp-email/otp-email.component';
import { OtpTemplateComponent } from './otp-template/otp-template.component';
import { TooltipsDirective } from './tooltip/tooltips.directive';
import { VerticalWizardDirective } from './wizard/directives/vertical-wizard.directive';
import { WizardNavbarComponent } from './wizard/wizard-navbar/wizard-navbar.component';
import { WizardComponent } from './wizard/wizard.component';
import { HorizontalWizardDirective } from './wizard/directives/horizontal-wizard.directive';
import { VisibleStepperPipe } from './wizard/wizard-navbar/visible-stepper.pipe';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    NgbModule.forRoot(),
    IntlTelModule.forRoot(),
    RouterModule,
    ComponentModule,
    UtilityModule,
    NotifierModule
  ],
  declarations: [
    AmountComponent,
    OtpComponent,
    CaptchaComponent,
    EmailComponent,
    MobileComponent,
    GenderComponent,
    TitleComponent,
    OtpEmailComponent,
    OtpTemplateComponent,
    TooltipsDirective,
    WizardNavbarComponent,
    VerticalWizardDirective,
    WizardComponent,
    HorizontalWizardDirective,
    VisibleStepperPipe
  ],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  exports: [
    AmountComponent,
    OtpComponent,
    CaptchaComponent,
    EmailComponent,
    MobileComponent,
    GenderComponent,
    TitleComponent,
    OtpEmailComponent,
    TooltipsDirective,
    WizardNavbarComponent,
    VerticalWizardDirective,
    WizardComponent
  ]
})
export class CustomModule {}
