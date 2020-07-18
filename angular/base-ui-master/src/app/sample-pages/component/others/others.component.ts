import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, FormArray, Validators } from '@angular/forms';
import { of } from 'rxjs';
import { AlertService } from 'projects/ui-master/src/public-api';
import { CheckboxItem } from 'projects/ui-master/src/lib/component/checkbox-group/checkboxitem';

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


  /** Declare Checkbox Group - start --------------- */
  userRolesOptions = new Array<any>();

  private userRoles = [
    { id: 1, name: 'Admin' },
    { id: 2, name: 'Director' },
    { id: 3, name: 'Professor' },
    { id: 4, name: 'Student' }
  ];

  // Lets imagine we fetched our user from the database with 3 roles:
  public userModel = {
    id: 1,
    name: 'Vlad',
    roles: [1,2,3] // <--- This is the array we are going to use in [selectedIds]
  };
  /** Declare Checkbox Group - end ------------------ */

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
    // load checkbox group. Mapping example array to checkbox [options]
    this.userRolesOptions = this.userRoles.map( 
      // Note: new CheckboxItem (1. id , 2. label, 3. value (optional), 4. checked status (optional))
      x => new CheckboxItem(x.id, x.name)
    );
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

  /**
   *  CheckboxGroup Component - binding selected values and options objects
   *  Example: 
   *  1. this.userRolesOptions <--- Objects bind in [option] as CheckboxItem class format
   *  2. this.userModel.roles <--- Objects bind in [selectedIds] as array of checkbox ids
   *  Note: 
   */  
  onRolesChange(value:any) {
    // processing changes of object array
    if ( Array.isArray( value ) ) {
      this.userModel.roles = value;
    }
  }

}