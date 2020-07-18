import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'ui-master-spinner-overlay',
  templateUrl: './spinner-overlay.component.html',
  styleUrls: ['./spinner-overlay.component.scss']
})
export class SpinnerOverlayComponent implements OnInit {
  @Input() public message: string;
  @Input() public isShow: boolean = false;
  constructor() { }

  ngOnInit() {
  }

}
