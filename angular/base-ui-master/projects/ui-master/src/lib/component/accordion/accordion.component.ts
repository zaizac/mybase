import { Component, AfterContentInit, ContentChildren, QueryList, Input } from '@angular/core';
import { AccordionGroupComponent } from './group/accordion-group.component';

@Component({
  selector: 'ui-accordion',
  templateUrl: './accordion.component.html',
  styleUrls: ['./accordion.component.scss']
})
export class AccordionComponent implements AfterContentInit {
  @Input() closed: boolean;
  @ContentChildren(AccordionGroupComponent)
  groups: QueryList<AccordionGroupComponent>;
  constructor() { }

  ngAfterContentInit(): void {
    // Loop through all Groups
    this.groups.toArray().forEach((t) => {
      // when title bar is clicked
      if (t.isToggled) {
        t.toggle.subscribe(() => {
          // Open the group
          if (this.closed) {
            this.openAndCloseGroup(t);
          } else {
            this.openGroup(t);
          }
        });
      }

      if(!t.isClosed){
        t.toggle.subscribe(() => {
          t.opened = true;
        });
      }
    });
  }

  openGroup(group: AccordionGroupComponent) {
    if (group.opened) {
      group.opened = false;
    } else {
      group.opened = true;
    }
  }

  openAndCloseGroup(group: AccordionGroupComponent) {
    this.groups.toArray().forEach((t) => t.opened = false);
    group.opened = true;
  }

}
