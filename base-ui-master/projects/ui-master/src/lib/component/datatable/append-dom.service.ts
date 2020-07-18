import {
  ApplicationRef,
  ComponentFactoryResolver,
  ComponentRef,
  EmbeddedViewRef,
  Injectable,
  Injector,
  ViewContainerRef
} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppendDomService {
  componentRef: ComponentRef<unknown>;

  constructor(
    private componentFactoryResolver: ComponentFactoryResolver,
    private appRef: ApplicationRef,
    private injector: Injector,
    private viewContainerRef: ViewContainerRef
  ) {}

  appendComponentToElement(element: Element, component: any, inputs: any) {
    // Inputs need to be in the following format to be resolved properly
    const inputProviders = Object.keys(inputs).map(inputKey => {
      return {
        provide: inputKey,
        useValue: inputs[inputKey]
      };
    });
    const options = {
      providers: inputProviders,
      parent: this.injector,
      deps: []
    };
    // We create an injector out of the data we want to pass down and this components injector
    const injector = Injector.create(options);

    // Create a component reference from the component
    this.componentRef = this.componentFactoryResolver.resolveComponentFactory(component).create(injector);
    Object.assign(this.componentRef.instance as any, inputs);

    // Attach component to the appRef so that it's inside the ng component tree
    this.appRef.attachView(this.componentRef.hostView);

    // Get DOM element from component
    // const domElem = (this.componentRef.hostView as EmbeddedViewRef<any>).rootNodes[0] as HTMLElement;
    const domNode = (this.componentRef.hostView as EmbeddedViewRef<any>).rootNodes[0];
    element.appendChild(domNode);
  }

  destroyComponent() {
    this.componentRef.destroy();
  }
}
