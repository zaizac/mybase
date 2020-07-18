import { NgxLoggerLevel } from 'ngx-logger';

export class UiMasterConfig {
    url: string;
    portalType: string;
    ekey: string;
    client?: string;
    skey?: string;
    serverLoggingUrl?: string;
    webLogLevel?: NgxLoggerLevel;
    serverLogLevel?: NgxLoggerLevel;
    disableConsoleLogging?: boolean;
    ERRORS: any;
}