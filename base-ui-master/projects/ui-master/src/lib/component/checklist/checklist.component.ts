import { Component, EventEmitter, forwardRef, Host, Input, OnInit, Optional, Output, SkipSelf } from '@angular/core';
import {
  AbstractControl,
  ControlContainer,
  ControlValueAccessor,
  FormControl,
  NG_VALUE_ACCESSOR
} from '@angular/forms';

// Increasing integer for generating unique ids for checklist components.
let nextUniqueId = 0;
let nextGrpUniqueId = 0;

export class CheckOptions {
  id: string;
  name: string;
  value: any;
  checked: boolean;

  constructor(args: { id: string; name: string; value: any; checked: boolean }) {
    this.id = args.id;
    this.name = args.name;
    this.value = args.value;
    this.checked = args.checked;
  }
}

@Component({
  selector: 'ui-checklist',
  templateUrl: './checklist.component.html',
  styleUrls: ['./checklist.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ChecklistComponent),
      multi: true
    }
  ]
})
export class ChecklistComponent implements OnInit, ControlValueAccessor {
  private control: AbstractControl;

  _uniqueId: string = `ui-checklist-${nextGrpUniqueId++}`;

  @Input() items: any[];

  @Input() bindLabel: string;

  @Input() bindValue: string;

  @Input() tabIndex: number;

  @Input() label: string;

  @Input() submitted: boolean;

  @Input() reset: boolean;

  @Input() formControlName: string;

  @Output() change = new EventEmitter<object>();

  selected = new Set<any>();

  fcCheck: FormControl = new FormControl();

  options = new Set<CheckOptions>();

  onTouched: () => void = () => {};

  propagateChange = (_: any) => {};

  constructor(@Optional() @Host() @SkipSelf() private controlContainer: ControlContainer) {
    // Default form label for error message
    if (!this.label) {
      this.label = 'Checkbox';
    }
  }

  ngOnInit() {
    // Retrieve parent form control and assign the defined validations
    if (this.controlContainer && this.formControlName) {
      this.control = this.controlContainer.control.get(this.formControlName);
      this.fcCheck.setValidators(this.control.validator);
      this.fcCheck.setAsyncValidators(this.control.asyncValidator);
    }

    // Construct Options
    for (const item of this.items) {
      this.options.add(
        new CheckOptions({
          id: this._uniqueId + `_${nextUniqueId++}`,
          value: this.resolveNested(item, this.bindValue),
          name: this.resolveNested(item, this.bindLabel),
          checked: false
        })
      );
    }
  }

  onBlur() {
    this.fcCheck.markAsTouched();
  }

  onChangeValue({ id, checked }) {
    this.options.forEach(item => {
      if (item.id === id) {
        if (checked) {
          this.selected.add(item);
        } else {
          this.selected.delete(item);
        }
      }
    });

    if (this.propagateChange) {
      // do not calculate if not ready
      const selected = [];
      this.selected.forEach(item => {
        selected.push(item.value);
      });
      // this.handleChange(selected);
      this.fcCheck.setValue(selected);
      this.propagateChange(this.fcCheck.value);
    } else {
      this.propagateChange(null);
    }
  }

  writeValue(value: any | any[]) {
    if (this.reset && this.fcCheck.touched) {
      this.fcCheck.markAsUntouched();
    }

    this.options.forEach(opt => {
      for (const item of value) {
        const newItem = this.resolveNested(item, this.bindValue);
        if (JSON.stringify(opt.value) === JSON.stringify(newItem)) {
          opt.checked = true;
          this.selected.add(opt);
        }
      }
    });
    this.fcCheck.setValue(value);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    if (isDisabled) {
      this.fcCheck.disable();
    } else {
      this.fcCheck.enable();
    }
  }

  resolveNested(option: any, key: string): any {
    if (!isObject(option)) {
      return option;
    }

    if (!key) {
      return option;
    }

    if (key.indexOf('.') === -1) {
      return option[key];
    } else {
      const keys: string[] = key.split('.');
      let value = option;
      for (let i = 0, len = keys.length; i < len; ++i) {
        if (value == null) {
          return null;
        }
        value = value[keys[i]];
      }
      return value;
    }
  }
}

export function isDefined(value: any) {
  return value !== undefined && value !== null;
}

export function isObject(value: any) {
  return typeof value === 'object' && isDefined(value);
}
