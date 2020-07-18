import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { WizardFormDataService, WizardCommunicationService } from 'projects/ui-master/src/public-api';

@Component({
  selector: 'app-step-five',
  templateUrl: './step-five.component.html',
  styleUrls: ['./step-five.component.scss']
})
export class StepFiveComponent implements OnInit, OnDestroy {
  title = 'Thank You!';
  allData: any = {};
  isFormValid = false;
  stepFiveConfirmSubs: Subscription;
  stepFivePrevSubs: Subscription;

  constructor(
    private formDataService: WizardFormDataService,
    private wizCommunication: WizardCommunicationService,
    private router: Router,
    private activeRoute: ActivatedRoute
  ) { }

  ngOnInit() {
    // this.formControls = this.formDataService.getFinalReactiveFormControls();
    this.allData = this.formDataService.getAllStepperData();

    this.isFormValid = this.formDataService.isFormValid();

    this.stepFiveConfirmSubs = this.wizCommunication.nextBtnClickEmitted$.subscribe(() => {
      alert('Your detail has been submitted!');
      this.formDataService.resetFormData();
      this.router.navigate(['../stepOne'], { relativeTo: this.activeRoute, skipLocationChange: true });
    });
    this.stepFivePrevSubs = this.wizCommunication.prevBtnClickEmitted$.subscribe(() => {
      this.router.navigate(['../stepThree'], { relativeTo: this.activeRoute, skipLocationChange: true });
    });
  }

  ngOnDestroy() {
    if (this.stepFiveConfirmSubs) {
      this.stepFiveConfirmSubs.unsubscribe();
    }

    if (this.stepFivePrevSubs) {
      this.stepFivePrevSubs.unsubscribe();
    }
  }
}
