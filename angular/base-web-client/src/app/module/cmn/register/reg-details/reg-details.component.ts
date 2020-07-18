import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router, NavigationStart } from '@angular/router';
import { RouterDataService, WizardFormDataService, WizardCommunicationService } from 'ui-master';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TestService } from 'bff-api';
import { FormGroup, FormBuilder } from '@angular/forms';
import { NGXLogger } from 'ngx-logger';

@Component({
  selector: 'ui-reg-details',
  templateUrl: './reg-details.component.html',
  styleUrls: ['./reg-details.component.scss']
})
export class RegDetailsComponent implements OnInit, OnDestroy {

  data: any = {};
  stepOneSubs: Subscription;
  state: Subscription;
  signUpForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private activeRoute: ActivatedRoute,
    private formDataService: WizardFormDataService,
    private wizardCommunicationService: WizardCommunicationService,
    private testService: TestService,
    private logger: NGXLogger) {
    this.signUpForm = this.formBuilder.group({
      fullName: [{ value: '', disable: true }, null],
      email: [{ value: '', disable: true }, null],
      statusCd: [{ value: '', disable: true }, null]
    });
  }

  ngOnInit() {
    this.state = this.activeRoute.paramMap
      .pipe(map(() => window.history.state)).subscribe(data => {

        if (data && Object.keys(data).length > 1) {
          this.data = data;
          sessionStorage.setItem('reg-details-state', JSON.stringify(data));
        } else {
          this.data = JSON.parse(sessionStorage.getItem('reg-details-state'));
        }

        this.testService.testFindByRefNo(this.data.appRefNo).subscribe(
          data => {
            this.signUpForm.controls.fullName.setValue(data.fullName);
            this.signUpForm.controls.email.setValue(data.email);
            this.signUpForm.controls.statusCd.setValue(data.statusCd);
            this.signUpForm.disable();
          },
          error => {
            this.logger.error(error);
          });
      });

    this.stepOneSubs = this.wizardCommunicationService.nextBtnClickEmitted$.subscribe(resp => {
      this.router.navigate(['../confirm'], { relativeTo: this.activeRoute, skipLocationChange: true });
    });
    this.formDataService.setFormData('regDetails', {
      details: this.data
    });

  }

  ngOnDestroy(): void {
    if (this.stepOneSubs) {
      this.stepOneSubs.unsubscribe();
    }

    if (this.state) {
      this.state.unsubscribe();
    }
  }

}
