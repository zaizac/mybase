import { Component } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-date',
  templateUrl: './date.component.html'
})
export class DateComponent {

  date_form: FormGroup;
  minDate: Date = new Date('1921-01-01');
  maxDate: Date = new Date();

  constructor(private formBuilder: FormBuilder) {
    this.date_form = this.formBuilder.group({
      dob: null,
      year: null,
      month: null
    });
  }

}