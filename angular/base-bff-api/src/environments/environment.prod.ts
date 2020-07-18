import { NgxLoggerLevel } from 'ngx-logger';
export const environment = {
  production: true,
  portalType: 'api',
  apiUrl: 'http://localhost:8080/base-bff',
  logLevel: NgxLoggerLevel.OFF,
  serverLogLevel: NgxLoggerLevel.OFF
};
