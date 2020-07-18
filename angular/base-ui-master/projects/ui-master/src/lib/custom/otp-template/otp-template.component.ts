import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { ModalService } from '../../ui-master.service';
import { first } from 'rxjs/operators';
import { Router } from '@angular/router';
import { OtpTemplateService } from './otp-template.service';

@Component({
  selector: 'ui-otp-template',
  templateUrl: './otp-template.component.html',
  styleUrls: ['./otp-template.component.scss']
})
export class OtpTemplateComponent implements OnInit {

  @Input() otpType: any;
  @Input() otpService: any;
  @Input() navigateURL: string;
  @Input() emailIdToSend: string;
  @Input() otpAction: boolean;
  @Input() otpUniqueId: any;

  errorMsg: string;
  successMsg: string;
  popupForm: FormGroup;

  lastVal: { [k: string]: any };
  startingRndNr = 180;
  RndNr: number = this.startingRndNr;

  constructor(private formBuilder: FormBuilder, public modalSvc: ModalService,
    private otpSvc: OtpTemplateService, private router: Router) {
  }

  ngOnInit() {
    this.popupForm = this.formBuilder.group({
      otp: ['', Validators.required]
    });

    if (!this.otpAction) {
      this.popupForm.get('otp').disable();
    }

    this.otpSvc.data.subscribe((x) => {
      if (this.RndNr > 0) {
        x.timeStamp = Date.now();
        this.lastVal = x;
        this.RndNr = x.val;
      } else {
        this.otpSvc.stopSth();
        //this.modalSvc.dismiss();
        this.RndNr = this.startingRndNr; //Reset back the timer
        this.otpAction = false;
        this.otpSvc.updateOtpAction(this.otpAction)
        this.popupForm.get('otp').enable();
      }
    });
  }

  // Fetching OTP using Email and Unique ID
  getOtp() {
    const subUrl = 'emailAddress=' + this.emailIdToSend + '&otpUniqueId=' + this.otpUniqueId;
    this.otpService.generateOtp(subUrl)
      .pipe(first())
      .subscribe(
        (data: any) => {
          // Calling Timer on successful Response Data
          this.errorMsg = null;
          this.successMsg = 'OTP Generation successful.';
          this.otpSvc.startSth();
        });
  }

  // Validating OTP and returning response and Parent TextBox
  validateOtp() {
    this.errorMsg = null;
    this.successMsg = null;
    if (this.popupForm.value.otp !== '' && this.popupForm.value.otp != null) {
      const subUrl = 'otpUniqueId=' + this.otpUniqueId;
      this.otpService.validateOtp(subUrl, this.popupForm.value.otp)
        .pipe(first())
        .subscribe(
          (data: { [s: string]: unknown; } | ArrayLike<unknown>) => {
            const newData = Object.values(data)
            if (newData[0] === true && newData[1] === false) {

              if (!this.navigateURL) {
                this.otpSvc.updateVerifyEmail();
              }
              this.otpAction = false;
              this.otpSvc.stopSth();
              this.modalSvc.dismiss();
              this.otpSvc.updateOtpAction(this.otpAction)

              if (this.navigateURL) {
                this.router.navigateByUrl(this.navigateURL);
              }
            }
            else if (newData[0] === true && newData[1] === true) {
              this.errorMsg = 'OTP Verification Fail. [OTP Expired].';
              this.successMsg = null;
            }
            else {
              this.errorMsg = 'OTP Verification Fail. [Invalid OTP].';
              this.successMsg = null;
            }
          },
          (error: any) => {
          });
    } else {
      this.errorMsg = 'OTP is required';
      this.successMsg = null;
    }
  }

  requestOtp() {
    this.getOtp();
    this.otpAction = true;
    this.otpSvc.updateOtpAction(this.otpAction)
    this.popupForm.get('otp').enable();
  }


}
