import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { BsDatepickerConfig } from 'projects/ui-master/src/lib/component/datetime/plugin/datepicker/bs-datepicker.config';

@Component({
  selector: 'app-demodate',
  templateUrl: './demodate.component.html',
  styleUrls: ['./demodate.component.scss']
})
export class DemodateComponent implements OnInit {

  @Input() required = false;

  date_form: FormGroup;
  bsConfig: Partial<BsDatepickerConfig>;
  colorTheme: string = 'theme-blue';

  submitted: boolean = false;
  minDate: Date = new Date('1921-01-01');
  maxDate: Date = new Date();

  constructor(private formBuilder: FormBuilder) {
    this.date_form = this.formBuilder.group({
      dob: null,
      dwt: null
    });
  }

  ngOnInit() {
    this.bsConfig = Object.assign({}, {
      containerClass: this.colorTheme, showWeekNumbers: false, dateInputFormat: 'DD/MM/YYYY',
      maxDate: new Date()
    });
    if (this.required) {
      this.date_form.controls.dt.setValidators([Validators.required]);
    }
  }

  onSubmit() {
    this.submitted = true;
  }
}
