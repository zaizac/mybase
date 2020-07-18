import { Directive, OnInit, ElementRef } from '@angular/core';
import { v4 as uuid } from 'uuid';

@Directive({
    selector: '[uniqueId]'
})
export class UuidDirective implements OnInit {

    constructor(elRef: ElementRef) {
        elRef.nativeElement.id = uuid();
    }

    ngOnInit() { }

}