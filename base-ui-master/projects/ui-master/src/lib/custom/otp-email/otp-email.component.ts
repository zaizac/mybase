import { Component, Input, OnChanges, OnInit, TemplateRef, ViewChild } from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormBuilder,
  FormControl,
  FormGroup,
  NG_VALUE_ACCESSOR,
  ValidationErrors,
  Validator,
  Validators
} from '@angular/forms';
import { v4 as uuid } from 'uuid';
import { ModalService } from '../../ui-master.service';
import { OtpTemplateService } from '../otp-template/otp-template.service';

@Component({
  selector: 'ui-otp-email',
  templateUrl: './otp-email.component.html',
  styleUrls: ['./otp-email.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: OtpEmailComponent, multi: true }]
})
export class OtpEmailComponent implements OnInit, OnChanges, Validator, ControlValueAccessor {
  constructor(
    private formBuilder: FormBuilder,
    public modalSvc: ModalService,
    private controlContainer: ControlContainer,
    private otp: OtpTemplateService
  ) {
    this.fc = new FormControl();
    otp.setOtpEmailComponent(this);
  }

  @Input() otpService: any;
  @Input() submitted: any;
  @Input() required: any;
  @ViewChild('content') content: TemplateRef<any>;
  @Input() formControlName: string;

  otpUniqueId: any;
  otpPopupAction: boolean = false;
  emailInvalid = false;
  otpVerified = false;
  otpForm: FormGroup;
  disabled = false;
  crntEvent: any;

  private control: AbstractControl;
  fc: FormControl;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  ngOnInit() {
    this.otpForm = this.formBuilder.group({
      email: ['', Validators.required]
    });

    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.fc.setValidators(this.control.validator);
      this.fc.setAsyncValidators(this.control.asyncValidator);
    }

    this.otpUniqueId = uuid();
  }

  verifyEmail() {
    if (!this.otpForm.get('email').valid) {
      this.emailInvalid = true;
      this.submitted = true;
    } else {
      this.emailInvalid = false;
      this.modalSvc.openDefault(this.content);
    }
  }

  resetVerifiedEmail() {
    this.otpUniqueId = uuid();
    this.otpForm.get('email').enable();
    this.otpVerified = false;
    this.otpPopupAction = false;
    this.otpForm.get('email').setValue(null);
    this.fc.setValue(null);
    this.valueChange();
  }

  writeValue(obj: any): void {
    if (obj) {
      this.otpForm.controls.email.setValue(obj);
      this.fc.setValue(obj);
    }
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  ngOnChanges(): void {}

  valueChange() {
    this.propagateChange(this.otpForm.get('email').value);
  }

  validate(c: AbstractControl): ValidationErrors | null {
    return this.fc.valid ? null : { invalidForm: { valid: false, message: 'OTP is invalid.' } };
  }

  emailVerified() {
    this.fc.setValue(this.otpForm.get('email').value);
    this.valueChange();
    this.otpVerified = true;
    this.otpForm.get('email').disable();
  }
}
