import { Component, Host, Input, OnInit, Optional, SimpleChanges, SkipSelf } from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
  ValidationErrors,
  Validator
} from '@angular/forms';
import { CountryISO } from './intl-tel/enums/country-iso.enum';
import { SearchCountryField } from './intl-tel/enums/search-country-field.enum';
import { TooltipLabel } from './intl-tel/enums/tooltip-label.enum';

@Component({
  selector: 'ui-mobile',
  templateUrl: './mobile.component.html',
  styleUrls: ['./mobile.component.scss'],
  providers: [
    { provide: NG_VALUE_ACCESSOR, useExisting: MobileComponent, multi: true },
    { provide: NG_VALIDATORS, useExisting: MobileComponent, multi: true }
  ]
})
export class MobileComponent implements OnInit, Validator, ControlValueAccessor {
  constructor(@Optional() @Host() @SkipSelf() private controlContainer: ControlContainer) {
    // Default Preferred Countries
    if (this.preferredCountries && this.preferredCountries.length == 0) {
      this.preferredCountries = [CountryISO.Bangladesh, CountryISO.Indonesia, CountryISO.Malaysia];
    }

    if (this.selectedCountryISO) {
      this.selectedCountryISO = CountryISO.Malaysia;
    }

    this.fcMobile = new FormControl();
  }

  @Input() submitted: boolean;
  @Input() reset: boolean;
  @Input() disabled: boolean;
  @Input() label = 'Mobile Number';
  @Input() preferredCountries: Array<string> = [];
  @Input() onlyCountries: Array<string> = [];
  @Input() selectedCountryISO: string;
  @Input() formControlName: string;

  cssClass = 'form-control';

  SearchCountryField = SearchCountryField;
  TooltipLabel = TooltipLabel;
  CountryISO = CountryISO;

  fcMobile: FormControl;

  private control: AbstractControl;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  ngOnInit() {
    // Retrieve parent form control and assign the defined validations
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.fcMobile.setValidators(this.control.validator);
      this.fcMobile.setAsyncValidators(this.control.asyncValidator);
    }
  }

  onKeyDown() {
    this.updateCssValidation();
  }

  ngOnChanges(changes: SimpleChanges): void {
    // Resetting the forms and css
    if (changes.submitted && !changes.submitted.currentValue) {
      this.cssClass = 'form-control';
    }

    if (this.reset && this.fcMobile.touched) {
      this.fcMobile.markAsUntouched();
    }

    this.updateCssValidation();
  }

  writeValue(obj: any): void {
    if (this.reset && this.fcMobile.touched) {
      this.fcMobile.markAsUntouched();
    }

    this.fcMobile.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (isDisabled) {
      this.fcMobile.disable();
    } else {
      this.fcMobile.enable();
    }
  }

  validate(c: AbstractControl): ValidationErrors | null {
    if (this.fcMobile.value) {
      return this.fcMobile.valid ? null : { invalidForm: { valid: false, message: this.label + ' is invalid' } };
    }
    return null;
  }

  changeValue() {
    if (this.fcMobile.value) {
      this.propagateChange(this.fcMobile.value.internationalNumber);
    } else {
      this.propagateChange(null);
    }
    this.updateCssValidation();
  }

  private updateCssValidation() {
    if (this.submitted || this.reset || this.fcMobile.touched) {
      if (this.fcMobile.valid) {
        this.cssClass = 'form-control';
      } else {
        this.cssClass = 'form-control is-invalid';
      }
    } else {
      this.cssClass = 'form-control';
    }
  }
}
