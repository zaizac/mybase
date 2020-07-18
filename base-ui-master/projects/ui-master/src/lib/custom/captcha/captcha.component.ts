import { Component, Input, OnInit, OnChanges } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ControlValueAccessor, NG_VALUE_ACCESSOR, ValidationErrors } from '@angular/forms';
import { Observable } from 'rxjs';
@Component({
  selector: 'ui-captcha',
  templateUrl: './captcha.component.html',
  styleUrls: ['./captcha.component.scss'],
  providers: [
    { provide: NG_VALUE_ACCESSOR, useExisting: CaptchaComponent, multi: true },
  ]
})
export class CaptchaComponent implements OnInit, ControlValueAccessor, OnChanges { //OnChanges

  @Input() captchaService: any;
  @Input() captchaSubmit: boolean;
  @Input() submitted: any;
  @Input() required: boolean;

  captchaForm: FormGroup;
  image: any;
  captchaLen: any = 6;
  disabled = false;
  isvalid = true;
  onTouched = () => { };
  propagateChange = (_: any) => { };

  constructor(private fb: FormBuilder) { }

  ngOnInit() {
    this.captchaForm = this.fb.group({ captcha: null });
    this.captchaForm.controls.captcha.setValidators([Validators.required, Validators.minLength(this.captchaLen)]);
    this.getCaptcha();
  }

  getCaptcha() {
    const subUrl = 'register';
    this.captchaService.captcha(subUrl).subscribe(
      (data: Blob) => {
        this.createImageFromBlob(data);
      },
      (error: any) => {
      }
    );
  }
  // Converting Captcha Response data to Image
  createImageFromBlob(image: Blob) {
    const reader = new FileReader();
    reader.addEventListener('load', () => { this.image = reader.result; }, false);
    if (image) {
      reader.readAsDataURL(image);
    }
  }

  writeValue(obj: any): void {
    if (obj) {
      this.captchaForm.controls.captcha.setValue(obj);
      this.propagateChange(obj);
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

  getValue() {
    if (this.submitted) {
      this.validateCaptcha(this.captchaForm.value.captcha);
      if (!this.isvalid) {
        this.writeValue(this.captchaForm.value.captcha);
      }
    }
  }

  ngOnChanges() {
    if (this.captchaForm && this.captchaForm.value.captcha) {
      this.writeValue(this.captchaForm.value.captcha);
      this.validateCaptcha(this.captchaForm.value.captcha);
    }
  }

  validateCaptcha(captcha: string): Observable<ValidationErrors | null> {
    const subUrl = 'register';
    return this.captchaService.validateCaptcha(captcha, subUrl).subscribe(
      (data: any) => {
        if (data) {
          this.isvalid = data.isValid;
        }
        if (!this.isvalid) {
          this.getCaptcha();
          this.propagateChange(null);
          this.isvalid = false;
        }
      },
      (error: any) => {
      }
    );
  }

  onReloadCaptcha () {
    // condition check below, to avoid onReloadCaptcha triggered during submit
    if (!this.submitted) {
      this.getCaptcha();
      this.propagateChange(null);
    }
  }
}
