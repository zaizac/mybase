import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'ui-tab',
  templateUrl: './tab.component.html',
  styleUrls: ['./tab.component.scss']
})
export class TabComponent implements OnInit {
  @Input('tabTitle') title: string;
  @Input() active = false;
  @Input() isCloseable = false;
  @Input() template;
  @Input() dataContext;
  @Input() disabled = false;
  @Input() imagePath: string;

  constructor() { }

  ngOnInit() { }

}
