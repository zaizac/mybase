
import { NgxLoggerLevel } from 'ngx-logger';
export const environment = {
  production: true,
  portalType: 'client',
  apiUrl: '/api',
  client: 'base-client',
  skey: 'secret',
  ekey: 'mw62SJ96!^54GKW)=@*HtQbK',
  sessionTimeout: 20, // in a minute
  alertTime: 60, // in a second
  logLevel: NgxLoggerLevel.OFF,
  serverLogLevel: NgxLoggerLevel.INFO
};
