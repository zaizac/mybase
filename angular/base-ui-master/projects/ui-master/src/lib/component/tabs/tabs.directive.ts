import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
    selector: '[tabs-directive]'
  })
  export class TabsDirective {
    constructor(public viewContainer: ViewContainerRef) {}
  }