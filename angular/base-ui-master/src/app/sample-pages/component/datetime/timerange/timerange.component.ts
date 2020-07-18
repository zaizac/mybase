import { Component } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-timerange',
  templateUrl: './timerange.component.html'
})
export class TimerangeComponent {

  date_form: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.date_form = this.formBuilder.group({
      dob: null
    });
  }
}
