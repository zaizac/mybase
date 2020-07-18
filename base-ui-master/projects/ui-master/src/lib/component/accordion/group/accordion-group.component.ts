import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
    selector: 'ui-group',
    templateUrl: './accordion-group.component.html',
    styleUrls: ['./accordion-group.component.scss']
})
export class AccordionGroupComponent {
    @Input() opened = false;
    @Input() title: string;
    @Input() disabled: boolean;
    @Input() isToggled: boolean = true;
    @Input() isClosed: boolean = true;
    @Output() toggle: EventEmitter<any> = new EventEmitter<any>();
}