import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, FormArray, Validators } from '@angular/forms';
import { of } from 'rxjs';
import { AlertService } from 'projects/ui-master/src/public-api';

@Component({
  selector: 'app-others',
  templateUrl: './others.component.html',
  styleUrls: ['./others.component.scss']
})
export class OthersComponent implements OnInit {
  other_form: FormGroup;
  orders = [];
  fruitsOption = [];
  colors = [];

  constructor(private formBuilder: FormBuilder, private alertService: AlertService) {
    this.other_form = this.formBuilder.group({
      fruits: [[{ id: 10, name: 'Apple' }], null],
      colors: [[{ id: 'B', name: 'Blue' }], Validators.required],
      orders: new FormArray([]),
      tafullname: new FormControl()
    });

    this.getOrders().subscribe(orders => {
      this.orders = orders;
      this.addCheckboxes();
    });

    this.getFruits().subscribe(fruits => {
      this.fruitsOption = fruits;
    });



    this.getColors().subscribe(colors => {
      this.colors = colors;
    });
  }

  ngOnInit() {
    // this.other_form.setControl('checkMe', this.formBuilder.array(this.orders || []));
  }

  private addCheckboxes() {
    this.orders.map((o, i) => {
      const control = new FormControl(); // if first item set to true, else false
      (this.other_form.controls.orders as FormArray).push(control);
    });
  }

  getOrders() {
    return of([
      { id: 10, name: 'Apple' },
      { id: 20, name: 'Banana' },
      { id: 30, name: 'Grapes' },
      { id: 40, name: 'Orange' }
    ]);
  }

  getFruits() {
    return of([
      { id: 10, name: 'Apple' },
      { id: 20, name: 'Banana' },
      { id: 30, name: 'Grapes' },
      { id: 40, name: 'Orange' }
    ]);
  }

  getColors() {
    return of([
      { id: 'R', name: 'Red' },
      { id: 'G', name: 'Green' },
      { id: 'B', name: 'Blue' },
      { id: 'Y', name: 'Yellow' }
    ]);
  }

  onChangeValue(e) {
    const order = JSON.parse(e.target.value);
    if (e.target.checked) {
      this.alertService.info('Selected order is ' + order.name + ' (RM ' + order.id + ')');
    } else {
      this.alertService.info('Unselected order is ' + order.name + ' (RM ' + order.id + ')');
    }
  }

  onSubmit() {
    let total = 0;
    this.other_form.value.orders.map(v => {
      if (v) {
        v = JSON.parse(v);
        total = total + v.id;
      }
    });

    this.alertService.info('Total order is ' + total);
  }

  showTextArea() {
  }

}