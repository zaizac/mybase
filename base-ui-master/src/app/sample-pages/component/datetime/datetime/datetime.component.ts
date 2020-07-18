import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-datetime',
  templateUrl: './datetime.component.html'
})
export class DatetimeComponent {

  date_form: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.date_form = this.formBuilder.group({
      dob: null,
      year: null,
      month: null
    });
  }

}