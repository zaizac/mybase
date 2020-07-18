import { Component, OnInit, forwardRef, Input, SimpleChanges, ViewChild, ElementRef, OnChanges } from '@angular/core';
import { NG_VALIDATORS, NG_VALUE_ACCESSOR } from '@angular/forms';
import { CountryCode } from './data/country-code';
import { phoneNumberValidator } from './intl-tel.validator';
import { Country } from './model/country.model';
import * as lpn from 'google-libphonenumber';
import { SearchCountryField } from './enums/search-country-field.enum';
import { TooltipLabel } from './enums/tooltip-label.enum';
import { CountryISO } from './enums/country-iso.enum';

@Component({
  selector: 'ui-intl-tel',
  templateUrl: './intl-tel.component.html',
  styleUrls: ['./intl-tel.component.scss'],
  providers: [
    CountryCode,
    {
      provide: NG_VALUE_ACCESSOR,
      // tslint:disable-next-line:no-forward-ref
      useExisting: forwardRef(() => IntlTelComponent),
      multi: true
    },
    {
      provide: NG_VALIDATORS,
      useValue: phoneNumberValidator,
      multi: true,
    }
  ]
})
export class IntlTelComponent implements OnInit, OnChanges {

  @Input() value = '';
  @Input() preferredCountries: Array<string> = [];
  @Input() enablePlaceholder = true;
  @Input() cssClass = 'form-control';
  @Input() onlyCountries: Array<string> = [];
  @Input() enableAutoCountrySelect = true;
  @Input() searchCountryFlag = false;
  @Input() searchCountryField: SearchCountryField[] = [SearchCountryField.All];
  @Input() searchCountryPlaceholder = '';
  @Input() maxLength = '';
  @Input() tooltipField: TooltipLabel;
  @Input() selectFirstCountry = true;
  @Input() selectedCountryISO: CountryISO;
  @Input() phoneValidation = true;
  selectedCountry: Country = {
    areaCodes: undefined,
    dialCode: '',
    flagClass: '',
    iso2: '',
    name: '',
    placeHolder: '',
    priority: 0
  };

  // display the country dial code next to the selected flag
  @Input() separateDialCode = false;
  separateDialCodeClass: string;

  phoneNumber = '';
  allCountries: Array<Country> = [];
  preferredCountriesInDropDown: Array<Country> = [];
  // Has to be 'any' to prevent a need to install @types/google-libphonenumber by the package user...
  phoneUtil: any = lpn.PhoneNumberUtil.getInstance();
  disabled = false;
  errors: Array<any> = ['Phone number is required.'];
  countrySearchText = '';

  @ViewChild('countryList') countryList: ElementRef;

  onTouched = () => { };
  propagateChange = (_: any) => { };

  constructor(
    private countryCodeData: CountryCode
  ) { }

  ngOnInit() {
    this.fetchCountryData();
    if (this.preferredCountries.length) {
      this.getPreferredCountries();
    }
    if (this.onlyCountries.length) {
      this.allCountries = this.allCountries.filter(c => this.onlyCountries.includes(c.iso2));
    }
    if (this.selectFirstCountry) {
      if (this.preferredCountriesInDropDown.length) {
        this.selectedCountry = this.preferredCountriesInDropDown[0];
      } else {
        this.selectedCountry = this.allCountries[0];
      }
    }
    this.getSelectedCountry();
    this.checkSeparateDialCodeStyle();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (this.allCountries && changes['selectedCountryISO']
      && changes['selectedCountryISO'].currentValue !== changes['selectedCountryISO'].previousValue) {
      this.getSelectedCountry();
    }
    if (changes.preferredCountries) {
      this.getPreferredCountries();
    }
    this.checkSeparateDialCodeStyle();
  }

  getPreferredCountries() {
    if (this.preferredCountries.length) {
      this.preferredCountriesInDropDown = [];
      this.preferredCountries.forEach(iso2 => {
        const preferredCountry = this.allCountries.filter((c) => {
          return c.iso2 === iso2;
        });

        this.preferredCountriesInDropDown.push(preferredCountry[0]);
      });
    }
  }

  getSelectedCountry() {
    if (this.selectedCountryISO) {
      this.selectedCountry = this.allCountries.find(c => { return (c.iso2.toLowerCase() === this.selectedCountryISO.toLowerCase()); });
      if (this.selectedCountry) {
        if (this.phoneNumber) {
          this.onPhoneNumberChange();
        } else {
          this.propagateChange(null);
        }
      }
    }
  }


	/**
	 * Search country based on country name, iso2, dialCode or all of them.
	 */
  searchCountry() {
    if (!this.countrySearchText) {
      this.countryList.nativeElement.querySelector('li').scrollIntoView({ behavior: 'smooth' });
      return;
    }
    const countrySearchTextLower = this.countrySearchText.toLowerCase();
    const country = this.allCountries.filter(c => {
      if (this.searchCountryField.indexOf(SearchCountryField.All) > -1) {
        // Search in all fields
        if (c.iso2.toLowerCase().startsWith(countrySearchTextLower)) {
          return c;
        }
        if (c.name.toLowerCase().startsWith(countrySearchTextLower)) {
          return c;
        }
        if (c.dialCode.startsWith(this.countrySearchText)) {
          return c;
        }
      } else {
        // Or search by specific SearchCountryField(s)
        if (this.searchCountryField.indexOf(SearchCountryField.Iso2) > -1) {
          if (c.iso2.toLowerCase().startsWith(countrySearchTextLower)) {
            return c;
          }
        }
        if (this.searchCountryField.indexOf(SearchCountryField.Name) > -1) {
          if (c.name.toLowerCase().startsWith(countrySearchTextLower)) {
            return c;
          }
        }
        if (this.searchCountryField.indexOf(SearchCountryField.DialCode) > -1) {
          if (c.dialCode.startsWith(this.countrySearchText)) {
            return c;
          }
        }
      }
    });

    if (country.length > 0) {
      const el = this.countryList.nativeElement.querySelector('#' + country[0].iso2);
      if (el) {
        el.scrollIntoView({ behavior: 'smooth' });
      }
    }

    this.checkSeparateDialCodeStyle();
  }

  public onPhoneNumberChange(): void {
    this.value = this.phoneNumber;

    let number: lpn.PhoneNumber;
    try {
      number = this.phoneUtil.parse(this.phoneNumber, this.selectedCountry.iso2.toUpperCase());
    } catch (e) {
    }

    let countryCode = this.selectedCountry.iso2;
    // auto select country based on the extension (and areaCode if needed) (e.g select Canada if number starts with +1 416)
    if (this.enableAutoCountrySelect) {
      countryCode = number && number.getCountryCode()
        ? this.getCountryIsoCode(number.getCountryCode(), number)
        : this.selectedCountry.iso2;
      if (countryCode && countryCode !== this.selectedCountry.iso2) {
        const newCountry = this.allCountries.find(c => c.iso2 === countryCode);
        if (newCountry) {
          this.selectedCountry = newCountry;
        }
      }
    }
    countryCode = countryCode ? countryCode : this.selectedCountry.iso2;

    this.checkSeparateDialCodeStyle();

    if (!this.value) {
      // tslint:disable-next-line:no-null-keyword
      this.propagateChange(null);
    } else {
      var intlNo = number ? this.phoneUtil.format(number, lpn.PhoneNumberFormat.INTERNATIONAL) : '';

      // parse phoneNumber if separate dial code is needed
      if (this.separateDialCode && intlNo) {
        this.phoneNumber = this.removeDialCode(intlNo);
      }

      this.propagateChange({
        number: this.value,
        internationalNumber: intlNo,
        nationalNumber: number ? this.phoneUtil.format(number, lpn.PhoneNumberFormat.NATIONAL) : '',
        countryCode: countryCode.toUpperCase(),
        dialCode: '+' + this.selectedCountry.dialCode
      });
    }
  }

  public onCountrySelect(country: Country, el): void {
    this.selectedCountry = country;

    this.checkSeparateDialCodeStyle();

    if (this.phoneNumber != null && this.phoneNumber.length > 0) {
      this.value = this.phoneNumber;

      let number: lpn.PhoneNumber;
      try {
        number = this.phoneUtil.parse(this.phoneNumber, this.selectedCountry.iso2.toUpperCase());
      } catch (e) {
      }

      var intlNo = number ? this.phoneUtil.format(number, lpn.PhoneNumberFormat.INTERNATIONAL) : '';

      // parse phoneNumber if separate dial code is needed
      if (this.separateDialCode && intlNo) {
        this.phoneNumber = this.removeDialCode(intlNo);
      }

      this.propagateChange({
        number: this.value,
        internationalNumber: intlNo,
        nationalNumber: number ? this.phoneUtil.format(number, lpn.PhoneNumberFormat.NATIONAL) : '',
        countryCode: this.selectedCountry.iso2.toUpperCase(),
        dialCode: '+' + this.selectedCountry.dialCode
      });
    } else {
      this.propagateChange(null);
    }

    el.focus();
  }

  public onInputKeyPress(event: KeyboardEvent): void {
    const allowedChars = /[0-9\+\-\ ]/;
    const allowedCtrlChars = /[axcv]/; // Allows copy-pasting
    const allowedOtherKeys = [
      'ArrowLeft', 'ArrowUp', 'ArrowRight', 'ArrowDown',
      'Home', 'End', 'Insert', 'Delete', 'Backspace'
    ];

    if (!allowedChars.test(event.key)
      && !(event.ctrlKey && allowedCtrlChars.test(event.key))
      && !(allowedOtherKeys.includes(event.key))) {
      event.preventDefault();
    }
  }

  protected fetchCountryData(): void {
    this.countryCodeData.allCountries.forEach(c => {
      const country: Country = {
        name: c[0].toString(),
        iso2: c[1].toString(),
        dialCode: c[2].toString(),
        priority: +c[3] || 0,
        areaCodes: c[4] as string[] || undefined,
        flagClass: c[1].toString().toLocaleLowerCase(),
        placeHolder: ''
      };

      if (this.enablePlaceholder) {
        country.placeHolder = this.getPhoneNumberPlaceHolder(country.iso2.toUpperCase());
      }

      this.allCountries.push(country);
    });
  }

  protected getPhoneNumberPlaceHolder(countryCode: string): string {
    try {
      return this.phoneUtil.format(this.phoneUtil.getExampleNumber(countryCode), lpn.PhoneNumberFormat.INTERNATIONAL);
    } catch (e) {
      return e;
    }
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any) {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  writeValue(obj: any): void {
    if (obj == null) {
			this.ngOnInit();
		}
    this.phoneNumber = obj;
    setTimeout(() => {
      this.onPhoneNumberChange();
    }, 1);
  }

  private getCountryIsoCode(countryCode: number, number: lpn.PhoneNumber): string | undefined {
    // Will use this to match area code from the first numbers
    const rawNumber = number['values_']['2'].toString();
    // List of all countries with countryCode (can be more than one. e.x. US, CA, DO, PR all have +1 countryCode)
    const countries = this.allCountries.filter(c => c.dialCode === countryCode.toString());
    // Main country is the country, which has no areaCodes specified in country-code.ts file.
    const mainCountry = countries.find(c => c.areaCodes === undefined);
    // Secondary countries are all countries, which have areaCodes specified in country-code.ts file.
    const secondaryCountries = countries.filter(c => c.areaCodes !== undefined);
    let matchedCountry = mainCountry ? mainCountry.iso2 : undefined;

		/*
			Interate over each secondary country and check if nationalNumber starts with any of areaCodes available.
			If no matches found, fallback to the main country.
		*/
    secondaryCountries.forEach(country => {
      country.areaCodes.forEach(areaCode => {
        if (rawNumber.startsWith(areaCode)) {
          matchedCountry = country.iso2;
        }
      });
    });

    return matchedCountry;
  }

  separateDialCodePlaceHolder(placeholder: string): string {
    return this.removeDialCode(placeholder);
  }

  private removeDialCode(phoneNumber: string): string {
    if (this.separateDialCode && phoneNumber) {
      phoneNumber = phoneNumber.substr(phoneNumber.indexOf(' ') + 1);
    }
    return phoneNumber;
  }

  // adjust input alignment
  private checkSeparateDialCodeStyle() {
    if (this.separateDialCode && this.selectedCountry) {
      var cntryCd = this.selectedCountry.dialCode;
      this.separateDialCodeClass = 'separate-dial-code iti-sdc-' + (cntryCd.length + 1);
		} else {
      this.separateDialCodeClass = '';
    }
  }

}
