import {Inject, Injectable, LOCALE_ID} from '@angular/core';
import {Observable, of} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class GeolocationService {
    private localeData = {
        language: this.locale,
        country: this.locale.toUpperCase(),
        province: ''
    };

    constructor(
        @Inject(LOCALE_ID) public locale: string,
    ) {
    }

    getClientLocale(): Observable<any> {
        return of(this.localeData);
    }
}
