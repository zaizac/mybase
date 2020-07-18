import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule, KeyValuePipe } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';
import { UuidDirective } from './uuid.directive';
import { FormatNumberDirective } from './formatNumber.directive';
import { FormatToUpperCaseDirective } from './formatUppercase.directive';
import { NoKeysDirective } from './noKeys.directive';
import { FormatToLowerCaseDirective } from './formatLowercase.directive';
import { ErrorPipe } from './error.pipe';
import { SimpleErrorPipe } from './simple-error-pipe.pipe';
import { SafeImageFilePipe } from './safe-image-file.pipe';

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        NgbModule,
        RouterModule
    ],
    declarations: [
        UuidDirective,
        FormatNumberDirective,
        FormatToUpperCaseDirective,
        FormatToLowerCaseDirective,
        NoKeysDirective,
        ErrorPipe,
        SimpleErrorPipe,
        SafeImageFilePipe
    ],
    providers: [KeyValuePipe],
    schemas: [
        CUSTOM_ELEMENTS_SCHEMA
    ],
    exports: [
        UuidDirective,
        FormatNumberDirective,
        FormatToUpperCaseDirective,
        FormatToLowerCaseDirective,
        NoKeysDirective,
        ErrorPipe,
        SimpleErrorPipe,
        SafeImageFilePipe
    ],
})
export class UtilityModule { }