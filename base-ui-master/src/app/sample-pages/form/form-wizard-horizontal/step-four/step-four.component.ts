import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { WizardFormDataService, WizardCommunicationService } from 'projects/ui-master/src/public-api';

@Component({
  selector: 'app-step-four',
  templateUrl: './step-four.component.html',
  styleUrls: ['./step-four.component.scss']
})
export class StepFourComponent implements OnInit, OnDestroy {
  title = 'Thank You!';
  allData: any = {};
  isFormValid = false;
  stepFourConfirmSubs: Subscription;
  stepFourPrevSubs: Subscription;

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

    this.stepFourConfirmSubs = this.wizCommunication.nextBtnClickEmitted$.subscribe(() => {
      alert('Your detail has been submitted!');
      this.formDataService.resetFormData();
      this.router.navigate(['../stepOne'], { relativeTo: this.activeRoute, skipLocationChange: true });
    });
    this.stepFourPrevSubs = this.wizCommunication.prevBtnClickEmitted$.subscribe(() => {
      this.router.navigate(['../stepThree'], { relativeTo: this.activeRoute, skipLocationChange: true });
    });
  }

  ngOnDestroy() {
    if (this.stepFourConfirmSubs) {
      this.stepFourConfirmSubs.unsubscribe();
    }

    if (this.stepFourPrevSubs) {
      this.stepFourPrevSubs.unsubscribe();
    }
  }
}
