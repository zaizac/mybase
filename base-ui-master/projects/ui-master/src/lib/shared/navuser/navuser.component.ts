import { AfterViewInit, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';

@Component({
  selector: 'ui-navuser',
  templateUrl: './navuser.component.html',
  styleUrls: ['./navuser.component.scss']
})
export class NavuserComponent implements AfterViewInit, OnInit {

  @Input() currentUser: any;

  @Output() toggleSidebar = new EventEmitter<void>();

  public config: PerfectScrollbarConfigInterface = {};

  constructor(private modalService: NgbModal) { }

  ngAfterViewInit() { }

  ngOnInit() { }

}
