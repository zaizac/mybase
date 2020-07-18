import { Component, EventEmitter, Host, Input, OnChanges, OnInit, Optional, Output, SkipSelf } from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR
} from '@angular/forms';

@Component({
  selector: 'ui-text',
  templateUrl: './text.component.html',
  styleUrls: ['./text.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: TextComponent, multi: true }]
})
export class TextComponent implements OnInit, OnChanges, ControlValueAccessor {
  @Input() submitted: boolean;
  @Input() reset: boolean;
  @Input() disabled: boolean = false;
  @Input() formControlName: string;
  @Input() label: string = 'Textfield';
  @Input() placeholder: string;
  @Input() maxlength: number;
  @Output() change = new EventEmitter<Object>();

  fcText: FormControl;
  onTouched = () => {};
  propagateChange = (_: any) => {};

  private control: AbstractControl;

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

  ngOnChanges() {
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

  valueChange(e: any) {
    this.propagateChange(this.fcText.value);
    this.change.emit(e);
  }
}
