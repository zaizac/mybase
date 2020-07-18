import { NgxLoggerLevel } from 'ngx-logger';
export const environment = {
  production: false,
  portalType: 'emp',
  apiUrl: 'http://localhost:8080/base-bff',
  client: 'urp-bgd-web-emp',
  skey: 'secret',
  ekey: 'mw62SJ96!^54GKW)=@*HtQbK',
  sessionTimeout: 20, // in a minute
  alertTime: 60, // in a second
  logLevel: NgxLoggerLevel.DEBUG,
  serverLogLevel: NgxLoggerLevel.ERROR
};