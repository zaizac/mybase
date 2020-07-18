import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-accordions',
  templateUrl: './accordions.component.html',
  styleUrls: ['./accordions.component.scss']
})
export class AccordionsComponent implements OnInit {
  first: boolean;
  second: boolean;

  constructor() { }
  cities = [
    { id: 1, title: 'New York', desc: 'This is New York' },
    { id: 2, title: 'Roma', desc: 'This is Roma' },
    { id: 3, title: 'Copenaghen', desc: 'This is Copenaghen'},
    { id: 4, title: 'Gibuti', desc: 'This is Gibuti'}
  ];

  ngOnInit() {
  }

}
