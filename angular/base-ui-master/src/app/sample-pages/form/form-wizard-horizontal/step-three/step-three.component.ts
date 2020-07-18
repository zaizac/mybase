import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { WizardCommunicationService } from 'projects/ui-master/src/lib/custom/wizard/services/wizard-communication.service';
import { WizardFormDataService } from 'projects/ui-master/src/lib/custom/wizard/services/wizard-form-data.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-step-three',
  templateUrl: './step-three.component.html',
  styleUrls: ['./step-three.component.scss']
})
export class StepThreeComponent implements OnInit, OnDestroy {
  title = 'This is step three form title';
  stepThreeForm: FormGroup;
  stepThreeNextSubs: Subscription;
  stepThreePrevSubs: Subscription;

  constructor(
    private router: Router,
    private activeRoute: ActivatedRoute,
    private formDataService: WizardFormDataService,
    private formBuilder: FormBuilder,
    private wizCommunication: WizardCommunicationService
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

    this.stepThreeNextSubs = this.wizCommunication.nextBtnClickEmitted$.subscribe(() => {
      if (this.stepThreeForm.invalid) {
        return;
      }
      this.formDataService.setReactiveFormData('stepThree', this.stepThreeForm);
      this.router.navigate(['../stepFour'], { relativeTo: this.activeRoute, skipLocationChange: true });
    });
    this.stepThreePrevSubs = this.wizCommunication.prevBtnClickEmitted$.subscribe(() => {
      this.router.navigate(['../stepTwo'], { relativeTo: this.activeRoute, skipLocationChange: true });
    });
  }

  ngOnDestroy() {
    if (this.stepThreeNextSubs) {
      this.stepThreeNextSubs.unsubscribe();
    }

    if (this.stepThreePrevSubs) {
      this.stepThreePrevSubs.unsubscribe();
    }
  }
}
