import { Injectable, Inject } from '@angular/core';
import { UiMasterConfig } from '../ui-master.config';
import { KeyValuePipe } from '@angular/common';
import { LOCALE_ID } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class SimpleMessagesProviderService {

    params: any[] = [];

    constructor(public config: UiMasterConfig, private keyValuePipe: KeyValuePipe, @Inject(LOCALE_ID) public locale: string) {}

    public instant(key: string, args?: any) {
        const defaultLocale = 'en';
        let currentLocale = ( this.locale != null ) ? this.locale : defaultLocale;
        // convert locale like 'en-US' to 'en_US' as presented errors-mapping.ts
        currentLocale = currentLocale.replace('-','_');

        if (currentLocale in this.config.ERRORS) {
            if (key in this.config.ERRORS[currentLocale]) {
                let msg = this.config.ERRORS[currentLocale][key];

                if (args && typeof args == 'object') {
                    this.params = this.keyValuePipe.transform(args);

                    this.params.forEach(e => {
                        msg = msg.replace('{' + e.key + '}', e.value)
                    });
                }
                return msg;
            } else {
                return key;
            }
        } else {
            return 'LOCALE ['+currentLocale+'] error definition not found.'
        }
    }

}