import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ThemeSwitcher } from 'projects/ui-master/src/public-api';
import { MENU_FAKE_DATA } from './mock/menu.fake';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  userMenu: any = MENU_FAKE_DATA;
  result = '';
  num: any;
  userlist: FormGroup;
  submitted = false;
  number: any;
  showSwitcher = false;
  themeSwitcher: ThemeSwitcher;

  constructor(
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.showSwitcher = true;
    this.themeSwitcher = {
      menu: 'top',
      color: 'green',
      showDarktheme: false,
      showMinisidebar: false,
      showRtl: false,
      showSettings: false
    }
    this.userlist = this.formBuilder.group({
      number: ['', [Validators.required]]
    });
  }

  // openToastr() {
  //   this.toastrSvc.success('success toastr!!!');
  // }

  get f() {
    return this.userlist.controls;
  }

  onSubmit() {
    this.number = this.f.number.value;
    this.num = this.number;
    this.submitted = true;
  }

  updateThemeSwitcher(ev) {
    // ok now I can use updated value to pass to bff = 
  }
}
