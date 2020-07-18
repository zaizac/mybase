import { AfterViewInit, Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';


@Component({
  selector: 'ui-navlang',
  templateUrl: './navlang.component.html',
  styleUrls: ['./navlang.component.scss']
})
export class NavlangComponent implements AfterViewInit, OnInit {
  
  @Input() currentUser: any;

  @Output() toggleSidebar = new EventEmitter<void>();

  public config: PerfectScrollbarConfigInterface = {};

  @Output() redirectUrl: string;

  constructor(private modalService: NgbModal) {}

  ngAfterViewInit() {}

  ngOnInit() {}

  public setLanguage = (language: string) => {

    if(typeof language !== 'undefined' && language) {
      const baseUrl = document.getElementsByTagName('base')[0].href;
      /** Reqular expression /\/([a-z]{2}(?:-[A-Z]{2})?)\//
       * example url: http://localhost:4000/ms/home | http://localhost:4000/en-EN/login
       * The reqular expression will look for locale /ms/ | /en-EN/
       */ 
      const currentLocale = baseUrl.match(/\/([a-z]{2}(?:-[A-Z]{2})?)\//);
      this.redirectUrl = baseUrl.replace(currentLocale[0],language);
    }
  }

}
