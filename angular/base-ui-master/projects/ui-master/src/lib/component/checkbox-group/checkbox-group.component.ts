import { Component, OnInit, Input, Output, EventEmitter, OnChanges } from '@angular/core';
import { CheckboxItem } from './checkboxitem';

@Component({
  selector: 'ui-checkbox-group',
  templateUrl: './checkbox-group.component.html',
  styleUrls: ['./checkbox-group.component.scss']
})
export class CheckboxGroupComponent implements OnInit, OnChanges {
  @Input() options = Array<CheckboxItem>();
  @Input() selectedIds: string[];

  /** Event emitted when the checkbox's `checked` value changes. */
  @Output() change = new EventEmitter<any[]>();

  /** Event emitted when the checkbox's `indeterminate` value changes. */
  @Output() readonly indeterminateChange: EventEmitter<boolean> = new EventEmitter<boolean>();

  @Input('aria-label') ariaLabel = '';
  @Input('aria-labelledby') ariaLabelledby: string | null = null;
  /** Whether the checkbox is required. */
  @Input() required: boolean;
  /** Whether the checkbox is disabled. */
  @Input() disabled = false;

  indeterminate = false;
  
  constructor() { }

  ngOnInit() { }


  onChecked() {
    if (!this.disabled) {
      const checkedOptions = this.options.filter(x => x.checked);
      this.selectedIds = checkedOptions.map(x => x.id);
      this.change.emit(checkedOptions.map(x => x.id));
    }
  }

  getAriaChecked(): 'true' | 'false' | 'mixed' {
    return this.change ? 'true' : (this.indeterminate ? 'mixed' : 'false');
  }

  /** 
   * We use ngOnChanges() not ngOnInit() for setting up selected items. 
   * This is because at init time we may still doesn't have a values 
   * retrieved from the backend. 
   */
  ngOnChanges() {
    this.selectedIds.forEach(selectedId => {
      const element = this.options.find(x => x.id === selectedId);
      if (element) {
        element.checked = true;
      }
    });
  }  
}
