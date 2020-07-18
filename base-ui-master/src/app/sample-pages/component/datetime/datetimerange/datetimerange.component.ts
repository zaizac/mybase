import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-datetimerange',
  templateUrl: './datetimerange.component.html'
})
export class DatetimerangeComponent {

  date_form: FormGroup;
  minDate: Date = new Date('1921-01-01');
  maxDate: Date = new Date();

  constructor(private formBuilder: FormBuilder) {
    this.date_form = this.formBuilder.group({
      dob: null
    });
  }
}
