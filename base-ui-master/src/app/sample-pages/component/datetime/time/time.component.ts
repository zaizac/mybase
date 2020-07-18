import { Component } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-time',
  templateUrl: './time.component.html'
})
export class TimeComponent {

  date_form: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.date_form = this.formBuilder.group({
      dob: null
    });
  }
}
