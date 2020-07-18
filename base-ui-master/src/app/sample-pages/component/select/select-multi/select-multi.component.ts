import { Component, OnInit } from '@angular/core';
import { SelectConfig } from 'projects/ui-master/src/public-api';
import { of } from 'rxjs';
import { CountryCode } from 'projects/ui-master/src/lib/custom/mobile/intl-tel/data/country-code';

@Component({
  selector: 'app-select-multi',
  templateUrl: './select-multi.component.html',
  styleUrls: ['./select-multi.component.scss'],
  providers: [CountryCode]
})
export class SelectMultiComponent implements OnInit {

  basicSelect: SelectConfig;
  serverSideSelect: SelectConfig;

  constructor(private cntryCode: CountryCode) { }

  ngOnInit() {
    this.basicSelect = {
      bindLabel: 'name',
      bindValue: 'dialcode',
      searchable: true,
      searchInDropDown: false,
      closeOnSelect: false,
      hideSelected: false,
      serverSide: false,
      multiple: true,
      data: () => {
        return of(this.fetchCountryData());
      }
    };

    this.serverSideSelect = {
      bindLabel: 'name',
      bindValue: 'dialcode',
      searchable: true,
      searchInDropDown: false,
      closeOnSelect: false,
      hideSelected: false,
      serverSide: true,
      multiple: true,
      data: () => {
        return of(this.fetchCountryData());
      }
    };
  }

  private fetchCountryData(): any {
    const allCountries: Array<any> = [];
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
      allCountries.push(country);
    });
    return allCountries;
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

}
