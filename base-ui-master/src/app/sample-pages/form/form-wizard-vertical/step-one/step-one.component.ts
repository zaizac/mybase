import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DatatableConfig } from 'projects/ui-master/src/lib/component/datatable/datatable.config';
import { Subscription } from 'rxjs';
import { mockServerSidePaginationResponse } from 'src/app/sample-pages/component/datatable/mockData';
import { MockService } from 'src/app/mock/mock.service';
import { WizardFormDataService, WizardCommunicationService } from 'projects/ui-master/src/public-api';

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
  stepOneOtp: Subscription;

  constructor(
    private router: Router,
    private activeRoute: ActivatedRoute,
    private formDataService: WizardFormDataService,
    private formBuilder: FormBuilder,
    private wizardCommunicationService: WizardCommunicationService, public mockService: MockService
  ) { }

  ngOnInit() {
    this.headerConfig = {
      columns: [
        {
          type: 'checkbox'
        },
        { field: 'SlNo' },
        { field: 'name' },
        { field: 'gender' },
        { field: 'company' }
      ],
      data: request => {
        return mockServerSidePaginationResponse(request);
      }
    };

    const prevTable = this.formDataService.getFormData('stepOneTest');

    if (prevTable) {
      this.headerConfig.prevTblSettings = prevTable.tableConfig;
      this.headerConfig.selectedData = prevTable.selectedRowData;
    }

    this.stepOneForm =
      this.formDataService.getReactiveFormGroup('stepOneTest') ||
      this.formBuilder.group({
        firstName: ['', [Validators.required]],
        lastName: ['', [Validators.required]],
        email: ['', [Validators.required]]
      });


    this.stepOneSubs = this.wizardCommunicationService.nextBtnClickEmitted$.subscribe(resp => {
      if (this.stepOneForm.invalid || !!!this.headerConfig.selectedData) {
        return;
      }


      this.formDataService.setReactiveFormData('stepOneTest', this.stepOneForm);
      this.formDataService.setFormData('stepOneTest', {
        selectedRowData: this.headerConfig.selectedData,
        tableConfig: this.headerConfig.prevTblSettings
      });
      this.router.navigate(['../stepTwo'], { relativeTo: this.activeRoute, skipLocationChange: true });
    });

    this.stepOneOtp = this.wizardCommunicationService.otpBtnClickEmitted$.subscribe(resp => {
      console.log('otpBtnClickEmitted.............', resp)
    });

    this.wizardCommunicationService.emitButtonConfigEvent({
      text: 'Test'
    }, null, {
      otpService: this.mockService,
      text: 'Create',
      email: 'mary.jane@bestinet.com.my',
      redirectURL: 'home',
      icon: 'fa fa-plus-circle',
      formGroup: this.stepOneForm
    });


  }

  ngOnDestroy() {
    if (this.stepOneSubs) {
      this.stepOneSubs.unsubscribe();
    }

    if (this.stepOneOtp) {
      this.stepOneOtp.unsubscribe();
    }
  }

}
