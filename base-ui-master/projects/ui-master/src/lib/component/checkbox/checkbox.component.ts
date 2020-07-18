import { Component, OnInit, Input, Output, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

// Increasing integer for generating unique ids for checkbox components.
let nextUniqueId = 0;


@Component({
  selector: 'ui-checkbox',
  templateUrl: './checkbox.component.html',
  styleUrls: ['./checkbox.component.scss'],
  providers: [
    { provide: NG_VALUE_ACCESSOR, useExisting: CheckboxComponent, multi: true }
  ]
})
export class CheckboxComponent implements OnInit, ControlValueAccessor {

  _uniqueId: string = `ui-checkbox-${nextUniqueId++}`;

  @Input('aria-label') ariaLabel: string = '';

  @Input('aria-labelledby') ariaLabelledby: string | null = null;

  /** A unique id for the checkbox input. If none is supplied, it will be auto-generated. */
  @Input() id: string = this._uniqueId;

  /** Returns the unique id for the visual hidden input. */
  get inputId(): string { return `${this.id || this._uniqueId}-input`; }

  /** Whether the checkbox is required. */
  @Input() required: boolean;

  /** Event emitted when the checkbox's `checked` value changes. */
  @Output() change: EventEmitter<Event> = new EventEmitter<Event>();

  /** Event emitted when the checkbox's `indeterminate` value changes. */
  @Output() readonly indeterminateChange: EventEmitter<boolean> = new EventEmitter<boolean>();

  /** The value attribute of the native input element */
  @Input() value: any;

  /** The native `<input type="checkbox">` element */
  @ViewChild('input', { read: false }) _inputElement: ElementRef<HTMLInputElement>;

  @Input() checked: boolean = false;

  @Input() tabIndex: number;

  _indeterminate: boolean = false;

  @Input() disabled = false;
  onTouched = () => { };
  propagateChange = (_: any) => { };

  constructor() { }

  ngOnInit() { }

  writeValue(obj: any): void {
    this.checked = !!obj;
    this.propagateChange(obj);
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  _getAriaChecked(): 'true' | 'false' | 'mixed' {
    return this.checked ? 'true' : (this._indeterminate ? 'mixed' : 'false');
  }

  _onInteractionEvent(e) {
    // We always have to stop propagation on the change event.
    // Otherwise the change event, from the input element, will bubble up and
    // emit its event object to the `change` output.
    e.stopPropagation();

    this._inputElement.nativeElement.checked = e.target.checked;
  }

  /**
  * Event handler for checkbox input element.
  * Toggles checked state if element is not disabled.
  * Do not toggle on (change) event since IE doesn't fire change event when
  * indeterminate checkbox is clicked.
  * @param event
  */
  _onInputClick(event) {
    // We have to stop propagation for click events on the visual hidden input element.
    // By default, when a user clicks on a label element, a generated click event will be
    // dispatched on the associated input element. Since we are using a label element as our
    // root container, the click event on the `checkbox` will be executed twice.
    // The real click event will bubble up, and the generated click event also tries to bubble up.
    // This will lead to multiple click events.
    // Preventing bubbling for the second event will solve that issue.
    event.stopPropagation();

    if (!this.disabled) {
      if (event.target.checked) {
        this.propagateChange(this._inputElement.nativeElement.value);
        this.change.emit(event);
      } else {
        this.propagateChange(null);
        this.change.emit(event);
      }
    }
  }

}
