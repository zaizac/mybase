import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  forwardRef,
  Host,
  Input,
  OnChanges,
  OnInit,
  Optional,
  Output,
  SkipSelf,
  ViewChild,
  ViewEncapsulation
} from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
  ValidationErrors,
  Validator
} from '@angular/forms';
import { NgSelectComponent } from '@ng-select/ng-select';
import { concat, Observable, of, Subject, throwError } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, switchMap, tap } from 'rxjs/operators';
import { SelectConfig } from './select.config';

@Component({
  selector: 'ui-select',
  templateUrl: './select.component.html',
  styleUrls: ['./select.component.scss'],
  providers: [
    { provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => SelectComponent), multi: true },
    { provide: NG_VALIDATORS, useExisting: forwardRef(() => SelectComponent), multi: true }
  ],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SelectComponent implements OnInit, OnChanges, Validator, ControlValueAccessor {
  private control: AbstractControl;

  @Input() label: string;
  @Input() submitted: boolean;
  @Input() reset: boolean;
  @Input() disabled: boolean;
  @Input() init: SelectConfig;
  @Input() formControlName: string;
  @Output() change = new EventEmitter<Object>();

  // Access ng-select
  @ViewChild(NgSelectComponent) ngSelectComponent: NgSelectComponent;

  data$: Observable<any>;
  dataInput$ = new Subject<string>();
  dataLoading: boolean = false;

  fcSelect: FormControl = new FormControl();
  onTouched: () => void = () => {};
  propagateChange = (_: any) => {};

  constructor(@Optional() @Host() @SkipSelf() private controlContainer: ControlContainer) {
    // Default form label for error message
    if (!this.label) {
      this.label = 'Select';
    }
  }

  ngOnInit() {
    // If there's no select config, throw an error
    if (!this.init) {
      throwError(new Error('Select component is not configured.'));
    }

    // Retrieve parent form control and assign the defined validations
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.fcSelect.setValidators(this.control.validator);
      this.fcSelect.setAsyncValidators(this.control.asyncValidator);
    }

    this.loadData();

    this.init.refresh = () => {
      // Call to clear
      this.ngSelectComponent.handleClearClick();
      this.loadData();
    };
  }

  ngOnChanges() {
    if (this.reset && this.fcSelect.touched) {
      this.fcSelect.markAsUntouched();
    }
  }

  validate(c: AbstractControl): ValidationErrors | null {
    return this.fcSelect.valid ? null : { invalidForm: { valid: false, message: this.label + ' is invalid' } };
  }

  writeValue(obj: any): void {
    if (this.reset && this.fcSelect.touched) {
      this.fcSelect.markAsUntouched();
    }
    this.fcSelect.setValue(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (isDisabled) {
      this.fcSelect.disable();
    } else {
      this.fcSelect.enable();
    }
  }

  customSearchFn(term: string, item: any) {
    return this.init.customSearchFn({ term: term, item: item });
  }

  valueChange(e) {
    if (this.fcSelect) {
      this.propagateChange(this.fcSelect.value);
    } else {
      this.propagateChange(null);
    }
    this.change.emit(e);
  }

  private loadData() {
    if (this.init.serverSide) {
      this.data$ = concat(
        of([]), // default items
        this.dataInput$.pipe(
          debounceTime(100),
          distinctUntilChanged(),
          tap(() => (this.dataLoading = true)),
          switchMap(term => {
            return this.init.data(term).pipe(
              catchError(() => of([])),
              tap(() => (this.dataLoading = false))
            );
          })
        )
      );
    } else {
      this.data$ = this.init.data(undefined);
    }
  }
}
