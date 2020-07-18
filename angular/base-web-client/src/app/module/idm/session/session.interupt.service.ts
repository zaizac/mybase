import { Injectable } from '@angular/core';
import { SessionInteruptService } from 'session-expiration-alert';
import { Router } from '@angular/router';
import { NGXLogger } from 'ngx-logger';

@Injectable()
export class AppSessionInteruptService extends SessionInteruptService {
  constructor(private router: Router, private logger: NGXLogger) {
    super();
  }
  continueSession() {
    this.logger.info(`Session timeout is extended.`);
  }
  stopSession() {
    this.logger.info(`Logout`);
    this.router.navigate(['logout']);
  }
}
