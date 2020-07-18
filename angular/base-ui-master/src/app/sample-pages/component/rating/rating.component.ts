import { Component } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-rating',
  templateUrl: './rating.component.html',
  styleUrls: ['./rating.component.scss']
})
export class RatingComponent {

  rating_form: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.rating_form = this.formBuilder.group({
      rate: null,
      decimalrate: null,
      percentrate: null,
    });
  }

}
