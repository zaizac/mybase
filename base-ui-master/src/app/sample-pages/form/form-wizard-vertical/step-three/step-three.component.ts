import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { WizardFormDataService } from 'projects/ui-master/src/public-api';

@Component({
  selector: 'app-step-three',
  templateUrl: './step-three.component.html',
  styleUrls: ['./step-three.component.scss']
})
export class StepThreeComponent implements OnInit {
  title = 'This is step three form title';
  stepThreeForm: FormGroup;

  constructor(
    private router: Router,
    private activeRoute: ActivatedRoute,
    private formDataService: WizardFormDataService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.stepThreeForm =
      this.formDataService.getReactiveFormGroup('stepThree') ||
      this.formBuilder.group({
        street: ['', [Validators.required]],
        city: ['', [Validators.required]],
        state: ['', [Validators.required]],
        zip: ['', [Validators.required]]
      });
  }

  save() {
    if (this.stepThreeForm.invalid) {
      return;
    }
    this.formDataService.setReactiveFormData('stepThree', this.stepThreeForm);
    this.router.navigate(['../stepFour'], { relativeTo: this.activeRoute, skipLocationChange: true });
  }

  // Cancel button event Starts
  cancel() {
    this.router.navigate(['../stepTwo'], { relativeTo: this.activeRoute, skipLocationChange: true });
  }
}
