import { Component, AfterContentInit, ContentChildren, QueryList, ViewChild, Input, ComponentFactoryResolver } from '@angular/core';
import { TabComponent } from './tab/tab.component';
import { TabsDirective } from './tabs.directive'

@Component({
  selector: 'ui-tab-group',
  templateUrl: './tab-group.component.html',
  styleUrls: ['./tab-group.component.scss']
})
export class TabGroupComponent implements AfterContentInit {

  dynamicTabs: TabComponent[] = [];
  @ContentChildren(TabComponent) tabs: QueryList<TabComponent>;
  @ViewChild(TabsDirective) dynamicTabPlaceholder: TabsDirective;
  @Input() tabPills: boolean = false;
  @Input() tabVertical: boolean = false;
  @Input() tabJustification: string;
  constructor(private componentFactoryResolver: ComponentFactoryResolver) { }

  ngAfterContentInit() {
    // get all active tabs
    const activeTabs = this.tabs.filter(tab => tab.active);

    // if there is no active tab set, activate the first
    if (activeTabs.length === 0) {
      this.selectTab(this.tabs.first);
    }

    const tabNav = document.getElementsByTagName('ul')[0];
    if (!this.tabVertical && this.tabPills) {
      tabNav.classList.add('nav-pills');
    }

    if (this.tabJustification === 'start') {
      tabNav.classList.add('justify-content-start');
    } else if (this.tabJustification === 'center') {
      tabNav.classList.add('justify-content-center');
    } else if (this.tabJustification === 'end') {
      tabNav.classList.add('justify-content-end');
    } else if (this.tabJustification === 'fill') {
      tabNav.classList.add('nav-fill');
    } else if (this.tabJustification === 'justified') {
      tabNav.classList.add('nav-justified');
    }
  }

  openTab(title: string, template, data, isCloseable = false) {
    // get a component factory for our TabComponent
    const componentFactory = this.componentFactoryResolver.resolveComponentFactory(
      TabComponent
    );

    // fetch the view container reference from our anchor directive
    const viewContainerRef = this.dynamicTabPlaceholder.viewContainer;

    // alternatively...
    // let viewContainerRef = this.dynamicTabPlaceholder;

    // create a component instance
    const componentRef = viewContainerRef.createComponent(componentFactory);

    // set the according properties on our component instance
    const instance: TabComponent = componentRef.instance as TabComponent;
    instance.title = title;
    instance.template = template;
    instance.dataContext = data;
    instance.isCloseable = isCloseable;

    // remember the dynamic component for rendering the
    // tab navigation headers
    this.dynamicTabs.push(componentRef.instance as TabComponent);

    // set it active
    this.selectTab(this.dynamicTabs[this.dynamicTabs.length - 1]);
  }


  selectTab(tab: TabComponent) {
    // deactivate all tabs
    this.tabs.toArray().forEach(tab => (tab.active = false));
    this.dynamicTabs.forEach(tab => (tab.active = false));

    // activate the tab the user has clicked on.
    tab.active = true;
  }

  closeTab(tab: TabComponent) {
    for (let i = 0; i < this.dynamicTabs.length; i++) {
      if (this.dynamicTabs[i] === tab) {
        // remove the tab from our array
        this.dynamicTabs.splice(i, 1);

        // destroy our dynamically created component again
        let viewContainerRef = this.dynamicTabPlaceholder.viewContainer;
        // let viewContainerRef = this.dynamicTabPlaceholder;
        viewContainerRef.remove(i);

        // set tab index to 1st one
        this.selectTab(this.tabs.first);
        break;
      }
    }
  }

  closeActiveTab() {
    const activeTabs = this.dynamicTabs.filter(tab => tab.active);
    if (activeTabs.length > 0) {
      // close the 1st active tab (should only be one at a time)
      this.closeTab(activeTabs[0]);
    }
  }

}