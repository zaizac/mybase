import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { WizardCommunicationService, WizardFormDataService } from 'ui-master';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { WfwReferenceService, TestService, AuthService } from 'bff-api';

@Component({
  selector: 'ui-reg-confirm',
  templateUrl: './reg-confirm.component.html',
  styleUrls: ['./reg-confirm.component.scss']
})
export class RegConfirmComponent implements OnInit, OnDestroy {


  confirmForm: FormGroup;
  nextSubs: Subscription;
  prevSubs: Subscription;
  submitted: boolean = false;
  reset: boolean = false;
  statusList: any;
  taskDetails: any;

  constructor(
    private router: Router,
    private activeRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private formDataService: WizardFormDataService,
    private wizardCommunicationService: WizardCommunicationService,
    private wfwSvc: WfwReferenceService,
    private testSvc: TestService,
    private authService: AuthService
  ) { }

  ngOnInit() {

    this.taskDetails = this.formDataService.getFormData('regDetails').details;
    this.wfwSvc.getRefStatusList({ display: 1, levelId: this.taskDetails.levelId })
      .subscribe(data => { this.statusList = data }, error => { });

    this.confirmForm =
      this.formBuilder.group({
        status: ['', [Validators.required]],
        remarks: null
      });


    this.nextSubs = this.wizardCommunicationService.nextBtnClickEmitted$.subscribe(resp => {
      if (this.confirmForm.invalid) {
        return;
      }
      this.testSvc.testUpdateSignUp({
        refNo: this.taskDetails.appRefNo,
        taskMasterId: this.taskDetails.taskMasterId,
        statusCd: this.confirmForm.controls.status.value,
        remark: this.confirmForm.controls.remarks.value,
        taskAuthorId: this.authService.currentUserValue.userId
      }).subscribe(() => {
        this.confirmForm.reset();
        this.router.navigate(['/application'], { relativeTo: this.activeRoute, skipLocationChange: true });
      });
    });

    this.prevSubs = this.wizardCommunicationService.prevBtnClickEmitted$.subscribe(() => {
      this.router.navigate(['../details'], { relativeTo: this.activeRoute, skipLocationChange: true });
    });
  }

  ngOnDestroy() {
    if (this.nextSubs) {
      this.nextSubs.unsubscribe();
    }

    if (this.prevSubs) {
      this.prevSubs.unsubscribe();
    }
  }

}
