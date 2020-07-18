import { Component, Host, Input, OnInit, Optional, SkipSelf } from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR
} from '@angular/forms';

@Component({
  selector: 'ui-textarea',
  templateUrl: './textarea.component.html',
  styleUrls: ['./textarea.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: TextareaComponent, multi: true }]
})
export class TextareaComponent implements OnInit, ControlValueAccessor {
  private control: AbstractControl;
  @Input() submitted: any;
  @Input() reset: boolean;
  @Input() disabled = false;
  @Input() formControlName: string;
  @Input() label = 'Textfield';
  @Input() maxlength: number;

  fcText: FormControl;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  constructor(
    @Optional()
    @Host()
    @SkipSelf()
    private controlContainer: ControlContainer
  ) {
    this.fcText = new FormControl();
  }

  ngOnInit() {
    // Retrieve parent form control and assign the defined validations
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.fcText.setValidators(this.control.validator);
      this.fcText.setAsyncValidators(this.control.asyncValidator);
    }
  }

  ngOnChanges(): void {
    if (this.reset && this.fcText.touched) {
      this.fcText.markAsUntouched();
    }
  }

  writeValue(obj: any): void {
    if (this.reset && this.fcText.touched) {
      this.fcText.markAsUntouched();
    }
    this.fcText.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (isDisabled) {
      this.fcText.disable();
    } else {
      this.fcText.enable();
    }
  }

  valueChange() {
    this.propagateChange(this.fcText.value);
  }
}
