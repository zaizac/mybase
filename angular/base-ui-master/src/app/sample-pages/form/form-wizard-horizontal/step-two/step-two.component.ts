import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { WizardCommunicationService } from 'projects/ui-master/src/lib/custom/wizard/services/wizard-communication.service';
import { WizardFormDataService } from 'projects/ui-master/src/lib/custom/wizard/services/wizard-form-data.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-step-two',
  templateUrl: './step-two.component.html',
  styleUrls: ['./step-two.component.scss']
})
export class StepTwoComponent implements OnInit, OnDestroy {
  title = 'This is step two form title';

  stepTwoForm: FormGroup;
  stepTwoNextSubs: Subscription;
  stepTwoPrevSubs: Subscription;

  constructor(
    private router: Router,
    private activeRoute: ActivatedRoute,
    private formDataService: WizardFormDataService,
    private formBuilder: FormBuilder,
    private wizCommunication: WizardCommunicationService
  ) {}

  ngOnInit() {
    const steppers = [...this.wizCommunication.stepperConfig];
    this.stepTwoForm =
      this.formDataService.getReactiveFormGroup('stepTwo') ||
      this.formBuilder.group({
        work: ['', [Validators.required]]
      });

    const nextButton = {
      text: 'Next',
      icon: 'fa fa-chevron-right'
    };

    const prevButton = {
      text: 'Previous',
      icon: 'fa fa-chevron-left'
    };
    this.wizCommunication.emitButtonConfigEvent(nextButton, prevButton);
    this.stepTwoNextSubs = this.wizCommunication.nextBtnClickEmitted$.subscribe(() => {
      if (this.stepTwoForm.invalid) {
        return;
      }
      this.formDataService.setReactiveFormData('stepTwo', this.stepTwoForm);
      steppers[steppers.findIndex(step => step.stepFormName === 'stepThree')].hide = false;
      this.wizCommunication.updateStepperEvent(steppers);
      this.router.navigate(['../stepThree'], { relativeTo: this.activeRoute, skipLocationChange: true });
      // this.router.navigate(['../stepFour'], { relativeTo: this.activeRoute, skipLocationChange: true });
    });
    this.stepTwoPrevSubs = this.wizCommunication.prevBtnClickEmitted$.subscribe(() => {
      this.router.navigate(['../stepOne'], { relativeTo: this.activeRoute, skipLocationChange: true });
    });
  }

  ngOnDestroy() {
    if (this.stepTwoNextSubs) {
      this.stepTwoNextSubs.unsubscribe();
    }

    if (this.stepTwoPrevSubs) {
      this.stepTwoPrevSubs.unsubscribe();
    }
  }
}
