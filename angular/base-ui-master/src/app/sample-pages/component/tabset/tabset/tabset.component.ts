import { Component, OnInit } from '@angular/core';
import { SelectConfig } from 'projects/ui-master/src/public-api';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { materialize, delay, dematerialize } from 'rxjs/operators';
import { of } from 'rxjs';
import { CountryCode } from 'projects/ui-master/src/lib/custom/mobile/intl-tel/data/country-code';

@Component({
  selector: 'app-tabset',
  templateUrl: './tabset.component.html',
  styleUrls: ['./tabset.component.scss'],
  providers: [CountryCode]
})
export class TabsetComponent implements OnInit {
  emptySelect: SelectConfig;
  basicSelect: SelectConfig;
  serverSideSelect: SelectConfig;
  userForm: FormGroup;
  justified: string = 'start';
  orientation: string = 'true';

  allCountries: Array<any> = [];
  constructor(private formBuilder: FormBuilder, private cntryCode: CountryCode) {
    this.userForm = this.formBuilder.group({
      country: [null, [Validators.required]],
      email: [null, [Validators.required]]
    });
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

    this.serverSideSelect = {
      bindLabel: 'name',
      bindValue: 'dialcode',
      searchable: true,
      searchInDropDown: false,
      closeOnSelect: false,
      hideSelected: false,
      serverSide: true,
      data: value => {
        return of(this.allCountries).pipe(materialize())
          .pipe(delay(500))
          .pipe(dematerialize());
      }
    };
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

  onChangeSearchable(e, type) {
    if (type == 'server-side') {
      this.serverSideSelect.searchable = e.target.checked;
    } else {
      this.basicSelect.searchable = e.target.checked;
    }
  }

  onChangeSearchDropdown(e, type) {
    if (type == 'server-side') {
      this.serverSideSelect.searchInDropDown = e.target.checked;
    } else {
      this.basicSelect.searchInDropDown = e.target.checked;
    }
  }

  onChangeCloseOnSelect(e, type) {
    if (type == 'server-side') {
      this.serverSideSelect.closeOnSelect = e.target.checked;
    } else {
      this.basicSelect.closeOnSelect = e.target.checked;
    }
  }

  onChangeHideSelected(e, type) {
    if (type == 'server-side') {
      this.serverSideSelect.hideSelected = e.target.checked;
    } else {
      this.basicSelect.hideSelected = e.target.checked;
    }
  }

  onChangeDropdown(e) {

  }

  onSubmit() {
    if (this.userForm.invalid) {
      return;
    }
  }

  onReset() {
    this.userForm.reset();
  }
}
