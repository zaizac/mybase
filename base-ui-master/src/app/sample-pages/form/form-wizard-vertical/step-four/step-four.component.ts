import { Component, OnInit } from '@angular/core';
import { WizardFormDataService, WizardCommunicationService } from 'projects/ui-master/src/public-api';
import { MockService } from 'src/app/mock/mock.service';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-step-four',
  templateUrl: './step-four.component.html',
  styleUrls: ['./step-four.component.scss']
})
export class StepFourComponent implements OnInit {
  title = 'Thank You!';
  // formControls: FormControl[];
  // formGroups: FormGroups[];
  isFormValid = false;

  stepFourForm: FormGroup;

  constructor(private formDataService: WizardFormDataService,
    private wizardCommunicationService: WizardCommunicationService
    , public mockService: MockService) { }

  ngOnInit() {
    this.isFormValid = this.formDataService.isFormValid();

    this.wizardCommunicationService.emitButtonConfigEvent(null, null, {
      otpService: this.mockService,
      text: 'Submit',
      email: 'mary.jane@bestinet.com.my',
      redirectURL: 'home',
      icon: 'fa fa-plus-circle',
      formGroup: this.stepFourForm
    });
  }

  submit() {
    alert('Excellent Job!');
    this.formDataService.resetFormData();
    this.isFormValid = false;
  }
}
