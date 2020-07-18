import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
    selector: '[formatNumber]'
})
export class FormatNumberDirective {

    private specialKeys: Array<string> = ['Backspace', 'Tab', 'End', 'Home', '-', '.', ',', 'ArrowLeft', 'ArrowRight', 'Del', 'Delete'];

    constructor(private elRef: ElementRef) {
    }

    @HostListener('keyup', ['$event']) onKeyUp(e: KeyboardEvent) {
        // prevent additional decimal position
        let newAmt: string = this.elRef.nativeElement.value;
        let current: string = newAmt.toString().replace(/,/g, "");

        let x: string[] = current.split('.');
        let a = x[0].replace(',', '');
        let z = x[1];
        if (z != undefined) {
            if (z.length >= 2) {
                z = z.substr(0, 2);
            }
            a = a + '.' + z;
        }

        this.elRef.nativeElement.value = a.toString().replace(/,/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }

    @HostListener('blur', ['$event']) onblur(e: KeyboardEvent) {
        // prevent additional decimal position
        let newAmt: string = this.elRef.nativeElement.value;
        let current: string = newAmt.toString().replace(/,/g, "");

        let x: string[] = current.split('.');
        let a = x[0].replace(',', '');
        if (x.length == 1) {
            if (a == '') {
                a = '00';
            }
            a = Number(a).toFixed(2);
        } else {
            a = Number(a + '.' + x[1]).toFixed(2);
        }

        this.elRef.nativeElement.value = a.toString().replace(/,/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }

    @HostListener('keydown', ['$event']) onKeyDown(e: KeyboardEvent) {
        // Ensure that it is a number
        if ((e.keyCode < 65 && e.keyCode > 90) ||
            (e.keyCode > 47 && e.keyCode < 58) ||
            (e.keyCode > 95 && e.keyCode < 106) ||
            this.specialKeys.indexOf(e.key) !== -1) {
            return;
        } else {
            e.preventDefault();
        }

    }



}