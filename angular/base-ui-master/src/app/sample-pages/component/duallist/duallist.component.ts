import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { DuallistboxComponent } from '../../../../../projects/ui-master/src/lib/component/duallistbox/duallistbox.component';
import { sampleData } from './mockData';

@Component({
  selector: 'app-duallist',
  templateUrl: './duallist.component.html'
})

export class DuallistComponent implements OnInit {
  // tslint:disable-next-line: variable-name
  dualList_form: FormGroup;

  tab = 1;
  keepSorted = true;
  key: string;
  display: any;
  source: Array<any>;
  confirmed: Array<any>;
  disabled = false;
  keys: any;

  sourceLeft = true;
  format: any = DuallistboxComponent.DEFAULT_FORMAT;
  private jsonValue: Array<any> = sampleData

  private sourceValue: Array<any>;
  private confirmedValue: Array<any>;

  arrayType = [
    { key: 'key', detail: '(object array)', value: 'value' }
  ];

  type = this.arrayType[0].key;

  constructor(private formBuilder: FormBuilder) {
    this.dualList_form = this.formBuilder.group({
      dualList: null
    });
  }

  ngOnInit() {
    this.doReset();
  }

  doReset() {
    this.sourceValue = JSON.parse(JSON.stringify(this.jsonValue));
    this.confirmedValue = new Array<any>();
    this.confirmedValue.push(this.jsonValue);

    switch (this.type) {
      case this.arrayType[0].key:
        this.useValue();
        break;
    }
  }

  private displayLabel(item: any) {
    return item.employee_name;
  }

  private useValue() {
    this.key = 'id';
    this.display = this.displayLabel;
    this.keepSorted = true;
    this.source = this.sourceValue;
    this.confirmed = this.confirmedValue;
  }

  swapSource() {
    switch (this.type) {
      case this.arrayType[0].value:
        this.useValue();
        break;
    }
  }

  doDisable() {
    this.disabled = !this.disabled;
  }

}

