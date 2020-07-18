import { NgxLoggerLevel } from 'ngx-logger';

export class BffApiConfig {
    url: string;
    portalType: string;
    client?: string;
    skey?: string;
    isProd?: boolean;
    serverLoggingUrl?: string;
    webLogLevel?: NgxLoggerLevel;
    serverLogLevel?: NgxLoggerLevel;
    disableConsoleLogging?: boolean;
}
