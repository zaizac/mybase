import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { WizardFormDataService } from 'projects/ui-master/src/public-api';

@Component({
  selector: 'app-step-two',
  templateUrl: './step-two.component.html',
  styleUrls: ['./step-two.component.scss']
})
export class StepTwoComponent implements OnInit {
  title = 'This is step two form title';

  stepTwoForm: FormGroup;

  constructor(
    private router: Router,
    private activeRoute: ActivatedRoute,
    private formDataService: WizardFormDataService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit() {
    this.stepTwoForm =
      this.formDataService.getReactiveFormGroup('stepTwo') ||
      this.formBuilder.group({
        work: ['', [Validators.required]]
      });
  }

  // Save button event Starts
  save() {
    if (this.stepTwoForm.invalid) {
      return;
    }
    this.formDataService.setReactiveFormData('stepTwo', this.stepTwoForm);
    this.router.navigate(['../stepThree'], { relativeTo: this.activeRoute, skipLocationChange: true });
  }
  // Cancel button event Starts
  cancel() {
    this.router.navigate(['../stepOne'], { relativeTo: this.activeRoute, skipLocationChange: true });
  }
}
