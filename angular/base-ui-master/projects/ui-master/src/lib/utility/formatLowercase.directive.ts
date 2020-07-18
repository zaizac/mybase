import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
    selector: '[formatToLowerCase]'
})
export class FormatToLowerCaseDirective {

    lastValue: string;

    constructor(public ref: ElementRef) { }

    @HostListener('input', ['$event']) onInput($event) {
        var start = $event.target.selectionStart;
        var end = $event.target.selectionEnd;
        $event.target.value = $event.target.value.toLowerCase();
        $event.target.setSelectionRange(start, end);
        $event.preventDefault();

        // Garante que o último caractere digitado fique minúsculo
        if (!this.lastValue || (this.lastValue && $event.target.value.length > 0 && this.lastValue !== $event.target.value)) {
            this.lastValue = this.ref.nativeElement.value = $event.target.value;
            // Propagation
            const evt = document.createEvent('HTMLEvents');
            evt.initEvent('input', false, true);
            event.target.dispatchEvent(evt);
        }
    }

}