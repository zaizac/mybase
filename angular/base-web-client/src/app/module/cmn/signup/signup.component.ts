import { Component, OnInit, SimpleChanges, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { CaptchaService, OtpService, TestService, UserProfile } from 'bff-api';
import { Router } from '@angular/router';
import { ModalService } from 'ui-master';
import { NGXLogger } from 'ngx-logger';

@Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html'
})

export class SignupComponent implements OnInit {
    signUpForm: FormGroup;
    reset: boolean;
    submitted: boolean;
    isvalid = true;
    newData: any;
    public captchaSubmit: boolean = false;
    captchaErrMsg: boolean = false;
    validate: boolean;

    constructor(
        private router: Router,
        private formBuilder: FormBuilder,
        public captchaService: CaptchaService,
        public otpService: OtpService,
        public testService: TestService,
        public modalService: ModalService,
        public logger: NGXLogger
    ) { }

    myDateValue: Date;

    ngOnInit() {
        this.signUpForm = this.formBuilder.group({
            firstName: ['', Validators.required],
            lastName: ['', Validators.required],
            email: null, // ['', Validators.required],
            captcha: null,
            otpButton: null
            // nationalID: ['', Validators.required],
            // date: ['', [Validators.required]],
            // phone: ['', [Validators.required]],
            // UserType: ['', [Validators.required]],
            // Country: ['', [Validators.required]]
        });

        this.myDateValue = new Date();
    }

    onDateChange(newDate: Date) { }

    get f() { return this.signUpForm.controls; }

    onSubmit() {
        console.log('onSubmit()....')
        this.submitted = true;
        this.reset = false;

        if (this.signUpForm.invalid) {
            return;
        }

        //this.signUpForm.controls.captcha.updateValueAndValidity();
        // setTimeout(() => {
        //     if (this.signUpForm.invalid) {
        //         return;
        //     } else {
        //         this.validate = true;
        //     }
        //     if (this.signUpForm.controls.captcha.invalid) {
        //         this.captchaErrMsg = true;
        //         this.captchaSubmit = true;
        //     } else {
        //         this.router.navigateByUrl('login');
        //     }
        // }, 2000);
        this.captchaSubmit = false;
        const user = new UserProfile;
        user.firstName = this.f.firstName.value;
        user.lastName = this.f.lastName.value;
        user.email = this.f.email.value;
        this.testService.testSignUp(user)
            .subscribe(
                data => {
                    if (data) {
                        this.modalService.success('Test Registration is successful.');
                        this.onReset();
                    }
                },
                error => {
                    this.logger.error(error.error.message);
                    this.modalService.error("Failed to process the transaction. Please contact system administrator.");
                }
            );
    }

    onReset() {
        this.reset = true;
        this.submitted = false;
        this.signUpForm.reset();
    }

}