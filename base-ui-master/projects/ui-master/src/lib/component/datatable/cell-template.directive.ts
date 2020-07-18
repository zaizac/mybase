import { Directive, Input, TemplateRef, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[uiNgTemplateCellTemplate]'
})
export class CellTemplateDirective {
  private template: any = {};
  constructor(private templateRef: TemplateRef<any>, private viewContainer: ViewContainerRef) { }

  @Input() set uiNgTemplateCellTemplate(id: string) {
    this.template = {
      id,
      template: this.templateRef
    };
  }

  get cellTemplate() {
    return this.template;
  }
}
