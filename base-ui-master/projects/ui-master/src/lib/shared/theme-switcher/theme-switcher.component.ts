import { Component, EventEmitter, HostListener, Input, OnInit, Output, ViewChild } from '@angular/core';
import { PerfectScrollbarComponent, PerfectScrollbarConfigInterface } from 'ngx-perfect-scrollbar';
import { ThemeSwitcher } from './theme-switcher.config';
import { NGXLogger } from 'ngx-logger';

@Component({
  selector: 'ui-theme-switcher',
  templateUrl: './theme-switcher.component.html',
  styleUrls: ['./theme-switcher.component.scss']
})
export class ThemeSwitcherComponent implements OnInit {
  
  @Input() showSwitcher: boolean;
  @Input() themeSwitcher: ThemeSwitcher;
  private initialTheme: ThemeSwitcher;

  public innerWidth: any;

  public config: PerfectScrollbarConfigInterface = {};
  @ViewChild(PerfectScrollbarComponent) componentRef: PerfectScrollbarComponent;

  @Output() updateThemeSwitcher = new EventEmitter();


  constructor(private logger: NGXLogger) {
        this.initialTheme = this.themeSwitcher;
  }

  ngOnInit() {
    if (typeof this.themeSwitcher === 'undefined' || this.themeSwitcher === null) {
      // Apply Default
      this.themeSwitcher = new ThemeSwitcher({
        color: 'blue',
        menu: 'top',
        showSettings: false,
        showMinisidebar: false,
        showDarktheme: false,
        showRtl: false
      });
    }
    this.handleLayout();
  }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.handleLayout();
  }

  onCloseSettings() {
    this.themeSwitcher.showSettings = !this.themeSwitcher.showSettings;
    this.themeSwitcher = this.initialTheme;
    this.ngOnInit();
  }

  toggleSidebar() {
    this.themeSwitcher.showMinisidebar = !this.themeSwitcher.showMinisidebar;
  }

  handleLayout() {
    this.innerWidth = window.innerWidth;
    if (this.innerWidth < 1170) {
      this.themeSwitcher.showMinisidebar = true;
    } else {
      this.themeSwitcher.showMinisidebar = false;
    }
  }

  onApplyTheme() {
    this.updateThemeSwitcher.emit(this.themeSwitcher);
    this.initialTheme = this.themeSwitcher;
    this.logger.debug('Apply Theme color:', this.themeSwitcher.color);
  }
}
