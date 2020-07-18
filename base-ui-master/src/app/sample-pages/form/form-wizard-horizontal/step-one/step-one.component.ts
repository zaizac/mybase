import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DatatableConfig } from 'projects/ui-master/src/lib/component/datatable/datatable.config';
import { WizardCommunicationService } from 'projects/ui-master/src/lib/custom/wizard/services/wizard-communication.service';
import { WizardFormDataService } from 'projects/ui-master/src/lib/custom/wizard/services/wizard-form-data.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-step-one',
  templateUrl: './step-one.component.html',
  styleUrls: ['./step-one.component.scss']
})
export class StepOneComponent implements OnInit, OnDestroy {
  headerConfig: DatatableConfig;

  title = 'This is step one form title';

  stepOneForm: FormGroup;
  stepOneSubs: Subscription;

  constructor(
    private router: Router,
    private activeRoute: ActivatedRoute,
    private formDataService: WizardFormDataService,
    private formBuilder: FormBuilder,
    private wizardCommunicationService: WizardCommunicationService
  ) {}

  ngOnInit() {
    const steppers = [...this.wizardCommunicationService.stepperConfig];
    steppers[steppers.findIndex(step => step.stepFormName === 'stepFour')].hide = false;
    this.wizardCommunicationService.updateStepperEvent(steppers);

    this.stepOneSubs = this.wizardCommunicationService.nextBtnClickEmitted$.subscribe(resp => {
      this.router.navigate(['../stepTwo'], { relativeTo: this.activeRoute, skipLocationChange: true });
    });
  }

  ngOnDestroy() {
    if (this.stepOneSubs) {
      this.stepOneSubs.unsubscribe();
    }
  }
}
