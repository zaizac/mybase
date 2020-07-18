import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { first } from 'rxjs/operators';
import { AuthService } from 'bff-api';
import { AlertService } from 'ui-master';

import { SessionTimeoutService } from '../session/session.timeout.service';

@Component({
  selector: 'ui-login',
  templateUrl: './login.component.html',
  providers: [SessionTimeoutService]
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  submitted = false;
  successMessage = '';
  errorMessage = '';
  successSubmit: true;
  loading = false;

  constructor(
    private logger: NGXLogger,
    private router: Router,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private alertService: AlertService,
    private sessionService: SessionTimeoutService
  ) {
    // redirect to home if already logged in
    // if (this.authService.currentUserValue) {
    //   this.router.navigate(['/home']);
    // }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(8)]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
    localStorage.removeItem('currentUser');
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    // (comment-format) stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.authService
      .login(this.f.username.value, this.f.password.value)
      .pipe(first())
      .subscribe(
        data => {
          this.sessionService.toggletimer();
          if (data.status === 'F') {
            this.router.navigate(['/firstTimeLogin']);
            this.logger.info('Is First Time');
          } else {
            this.router.navigate(['/home']);
            this.logger.info('Login Successful');
          }
        },
        error => {
          this.alertService.error(error.error.message);
          this.loginForm.reset();
          this.submitted = false;
          this.loading = false;
          this.logger.error('Login Failed: ' + error.error.message);
        }
      );
  }
}
