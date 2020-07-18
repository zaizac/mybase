import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { AuthService } from 'bff-api';
import { ToastrComponentsService, ModalService } from 'ui-master';

@Component({
  selector: 'ui-forgotpassword',
  templateUrl: './forgotpassword.component.html'
})
export class ForgotpasswordComponent implements OnInit {

  forgotPassword: FormGroup;
  errorMessage = '';
  isError = false;
  submitted = false;
  userName = new FormControl('', [Validators.required]);

  constructor(private authService: AuthService,
    private nGXLogger: NGXLogger,
    private router: Router,
    private modalService: ModalService) {
  }


  ngOnInit() { }

  onSubmit() {
    this.submitted = true;
    if (this.userName.invalid) {
      return;
    }

    if (this.userName.value) {
      this.authService.resetPassword(this.userName.value)
        .subscribe(
          data => {
            this.isError = false;
            this.nGXLogger.info('Reset Password Susscessful');
            this.modalService.success('Please check your email to find the new password. Please login and reset password.');
            setTimeout(this.redirectToLogin.bind(this), 3000);
          },
          error => {
            this.isError = true;
            this.modalService.error(error.error.message)
            this.userName.reset();
            this.userName.setErrors(null);
          });
    } else {
      this.userName.updateValueAndValidity();
      return;
    }
  }

  redirectToLogin() {
    this.router.navigate(['login']);
  }
}
