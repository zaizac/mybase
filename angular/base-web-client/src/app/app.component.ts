import { Component, OnInit, AfterViewChecked, PLATFORM_ID, Inject } from '@angular/core';
import { environment } from '@environments/environment';
import { AuthService, User, ReferenceService, IdmConfigDto, IdmConfigMapper } from 'bff-api';
import { ThemeSwitcher } from 'ui-master';
import { NGXLogger } from 'ngx-logger';
import { isPlatformBrowser } from '@angular/common';
import { map } from 'rxjs/operators';
import { InboxConfig } from '@appmodule/mailbox/inbox.config';
import { InboxCommunicationService } from '@appmodule/mailbox/inbox/inbox-communication.service';
import { Router } from '@angular/router';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit, AfterViewChecked {

  currentUser: User;
  showSwitcher = false;
  alertAt = environment.alertTime;
  startTimer = false;
  themeSwitcher: ThemeSwitcher;
  idmConfigDtos: IdmConfigDto[];
  idmConfigMapper: IdmConfigMapper;
  isBrowser: boolean;  

  constructor(
    private router: Router,
    private authService: AuthService,
    private referenceService: ReferenceService,
    private logger: NGXLogger,
    @Inject(PLATFORM_ID) platformId: Object,
    private inboxCommunicationService: InboxCommunicationService
  ) {
    this.authService.currentUser.subscribe(x => this.currentUser = x);
    this.isBrowser = isPlatformBrowser(platformId);
  }

  ngOnInit() {
    if (this.authService.currentUserValue) {
      // ONLY SYSTEM USER CAN CHANGE THE THEME COLOR
      this.showSwitcher = this.authService.hasAnyRole('SYSTEM');
      this.toggletimer();
    } else {
      this.showSwitcher = false;
    }

    if (this.idmConfigMapper === null || this.idmConfigMapper === undefined) {
      // subscribe to themeconfig and assign value
      this.referenceService.idmConfigByPortalType(environment.portalType)
        .pipe(map((data: IdmConfigDto[]) => {
          return this.settingThemeSwitcher(data);
        }))
        .subscribe(
          data => { this.myTheme = data; });
    }

    // Setting mailbox config filter
    this.mailboxConfig();
  }

  settingThemeSwitcher(data: IdmConfigDto[]) {
    this.idmConfigMapper = new IdmConfigMapper(environment.portalType, data);
    const themeConfig = (new IdmConfigMapper(environment.portalType, data)).getThemeSwitcherConfig();
    this.logger.debug('theme color is : ' + themeConfig.color);
    return new ThemeSwitcher({
      color: themeConfig.color,
      menu: themeConfig.showMenu,
      showSettings: false,
      showMinisidebar: false,
      showDarktheme: themeConfig.showDarktheme,
      showRtl: themeConfig.showRtl
    });
  }

  // this method stay for now to refresh themeswitcher
  ngAfterViewChecked(): void { }

  get myTheme() {
    return this.themeSwitcher;
  }

  set myTheme(theme: ThemeSwitcher) {
    this.themeSwitcher = theme;
  }

  increase() {
    this.alertAt++;
  }

  toggletimer() {
    this.startTimer = !this.startTimer;
  }

  updateThemeSwitcher(ev: ThemeSwitcher) {
    if (this.idmConfigMapper) {
      const idmConfig
        = this.idmConfigMapper
          .getIdmConfig(ev.color, ev.showSettings, ev.showMinisidebar,
            ev.showDarktheme, ev.showRtl, ev.menu);
      this.referenceService.updateIdmConfig(environment.portalType, idmConfig).subscribe(resp => { });
    }
  }

  // This function is to configure the filtering of data in Inbox
  mailboxConfig() {
    if(this.authService.currentUserValue) {      
      var inboxConfig = new InboxConfig;
  
      // Condition to display inbox only
      if (this.authService.currentUserValue.userType.userTypeCode === 'EMP'
        || this.authService.currentUserValue.userType.userTypeCode === 'RA') {
          inboxConfig.isInboxOnly = true;
      }
  
      // Filter with additional info   
      inboxConfig.additionalInfo = {};
  
      if (this.authService.currentUserValue.userRoleGroup.userGroupCode === 'RA_ADMIN') {
        inboxConfig.additionalInfo.raId = this.authService.currentUserValue.profId;
      }
  
      if (this.authService.currentUserValue.userRoleGroup.userGroupCode === 'EMP_ADMIN') {
        inboxConfig.additionalInfo.empProfId = this.authService.currentUserValue.profId;
      }
  
      if (this.authService.currentUserValue.userRoleGroup.userGroupCode === 'EMP_IND_ADMIN') {
        inboxConfig.additionalInfo = this.authService.currentUserValue.profId;
      }
  
      this.inboxCommunicationService.setConfig(inboxConfig);
    }
  }

}
