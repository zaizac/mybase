import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ValidatorFn, AbstractControl } from '@angular/forms';
import { SelectConfig } from 'projects/ui-master/src/public-api';
import { of } from 'rxjs';
import { CountryCode } from 'projects/ui-master/src/lib/custom/mobile/intl-tel/data/country-code';
import { MockService } from 'src/app/mock/mock.service';

@Component({
  selector: 'app-form-basic',
  templateUrl: './form-basic.component.html',
  styleUrls: ['./form-basic.component.scss'],
  providers: [CountryCode]
})
export class FormBasicComponent implements OnInit {

  // Datepicker config
  minDate: Date = new Date('1921-01-01');
  maxDate: Date = new Date();

  userForm: FormGroup;
  submitted: boolean = false;
  reset: boolean = false;
  loading: boolean = false;

  basicSelect: SelectConfig;
  allCountries: Array<any> = [];

  constructor(private formBuilder: FormBuilder, private cntryCode: CountryCode, public mockService: MockService) {
    this.userForm = this.formBuilder.group({
      fullname: [null, [Validators.required, this.validateMax(this)]],
      email: [null, [Validators.required]],
      dob: null,
      gender: null,
      phone: [null,[Validators.required]],
      status: null,
      country: [null, [Validators.required]],
      mileage: null,
      price: [null, [Validators.required]],
      age: [null, [Validators.required]],
      ageRange: [{ from: 10, to: 20 }, [Validators.required]],
      otpEmail: [null, [Validators.required]],
      captcha: null //[null, [Validators.required]],
    });
  }

  validateMax(event: any): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      if (control.value != null) {
        return { max: { maxDays: 7, country: 'SAUDI ARABIA' } };
      }
      return null;
    };
  }

  ngOnInit() {
    this.fetchCountryData();
    this.basicSelect = {
      bindLabel: 'name',
      bindValue: '',
      searchable: true,
      searchInDropDown: false,
      closeOnSelect: false,
      hideSelected: false,
      serverSide: false,
      data: () => {
        return of(this.allCountries);
      },
    };

    // Set DOB minus 18 years from current date
    this.maxDate.setFullYear(this.maxDate.getFullYear() - 18);
  }

  get f() { return this.userForm.controls; }

  onSubmit() {
    this.submitted = true;
    this.loading = true;
    this.reset = false;
    console.log(this.userForm.value)
    if (this.userForm.invalid) {
      return;
    }

  }

  onReset() {
    this.reset = true;
    this.submitted = false;
    this.userForm.reset();
  }

  private fetchCountryData(): any {
    this.cntryCode.allCountries.forEach(c => {
      const country: any = {
        name: c[0].toString(),
        iso2: c[1].toString(),
        dialCode: c[2].toString(),
        priority: +c[3] || 0,
        areaCodes: c[4] as string[] || undefined,
        flagClass: c[1].toString().toLocaleLowerCase(),
        placeHolder: ''
      };
      this.allCountries.push(country);
    });
  }

}