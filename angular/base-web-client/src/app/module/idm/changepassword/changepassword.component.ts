import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';
import { AuthService, ChangePassword, User } from 'bff-api';
import { ModalService } from 'ui-master';


@Component({
  selector: 'ui-changepassword',
  templateUrl: './changepassword.component.html'
})
export class ChangepasswordComponent implements OnInit {
  changePasswordForm: FormGroup;
  currentUser: User;
  submitted = false;
  isError = false;
  errorMessage = '';
  isPassError = false;
  passErrorMessage = '';
  title = 'Change Password';

  constructor(private formBuilder: FormBuilder,
    private authService: AuthService,
    private logger: NGXLogger,
    private router: Router,
    private modalService: ModalService) {
  }

  ngOnInit() {
    this.changePasswordForm = this.formBuilder.group({
      currPword: ['', [Validators.required]],
      newPword: ['', [Validators.required, Validators.minLength(6)]],
      repNewPword: ['', [Validators.required, Validators.minLength(6)]]
    });

    this.currentUser = this.authService.currentUserValue;
    if (this.currentUser.status === 'F') {
      this.title = 'First Time Login';
    }
  }

  get cpf() { return this.changePasswordForm.controls; }

  clearError(inputName: string) {
    const self = this.cpf[inputName];
    if (self.dirty && this.submitted) {
      this.submitted = false;
      this.isPassError = false;
      this.passErrorMessage = '';
    }
  }

  onSubmit() {
    this.submitted = true;

    if (this.changePasswordForm.invalid) {
      return;
    }

    if (!!this.cpf.repNewPword.value && this.cpf.newPword.value !== this.cpf.repNewPword.value) {
      this.isPassError = true;
      this.passErrorMessage = 'Password did not match! Please try again.';
      return;
    }

    const changePassword = new ChangePassword();
    changePassword.userId = this.currentUser.userId;
    changePassword.currPword = this.cpf.currPword.value;
    changePassword.newPword = this.cpf.newPword.value;
    changePassword.repNewPword = this.cpf.repNewPword.value;

    this.authService.changePassword(changePassword)
      .subscribe(
        data => {
          this.logger.info('Change Password Successful');
          this.modalService.success('Password has been successfully changed. Please login again.');
          setTimeout(this.redirectToLogin.bind(this), 3000);
        },
        error => {
          this.logger.error(error.error.message);
          this.modalService.error(error.error.message);
          this.changePasswordForm.reset();
          this.changePasswordForm.setErrors(null);
          this.submitted = false;
        });
  }

  redirectToLogin() {
    this.router.navigate(['logout']);
  }

}