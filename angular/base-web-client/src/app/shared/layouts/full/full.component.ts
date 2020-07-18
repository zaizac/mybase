import { Component, OnInit } from '@angular/core';
import { AuthService, User, UserMenu } from 'bff-api';

@Component({
  selector: 'ui-full-layout',
  templateUrl: './full.component.html'
})
export class FullComponent implements OnInit {

  currentUser: User;
  userMenu: UserMenu[];

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.currentUser = this.authService.currentUserValue;
    if (this.currentUser) {
      this.userMenu = this.currentUser.menus;
    }
  }

}