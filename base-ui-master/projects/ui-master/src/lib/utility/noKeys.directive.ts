import { Directive, HostListener } from '@angular/core';

@Directive({
    selector: '[noKeys]'
})
export class NoKeysDirective {

    constructor() {
    }

    @HostListener('keydown', ['$event']) onKeyDown(e: KeyboardEvent) {
        e.preventDefault();
    }

}