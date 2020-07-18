import { Component, OnInit } from '@angular/core';
import { User, AuthService } from 'bff-api';
import { Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';

@Component({
  selector: 'ui-logout',
  templateUrl: 'logout.component.html'
})
export class LogoutComponent implements OnInit {

  currentUser: User;

  constructor(
    private router: Router,
    private authService: AuthService,
    private logger: NGXLogger
  ) {
    this.currentUser = this.authService.currentUserValue;
    this.authService.currentUser.subscribe(x => this.currentUser = x);
  }

  ngOnInit() {
    this.currentUser = this.authService.currentUserValue;

    // If user object does not exists, redirect to Login page
    if(this.currentUser === undefined) {
      this.router.navigate(['login']);
    }

    // Initiate logout
    this.authService.logout(this.currentUser).subscribe(
      data => {
        if (data) {
          this.router.navigate(['login']);
        } else {
          this.logger.error("Unable to logout");
          this.router.navigate(['']);
        }
      },
      error => {
        this.logger.error("Unable to logout");
        this.router.navigate(['']);
      });

  }

}
