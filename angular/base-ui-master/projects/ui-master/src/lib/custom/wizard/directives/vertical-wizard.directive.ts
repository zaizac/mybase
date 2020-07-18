import { ChangeDetectorRef, Directive, ElementRef, Renderer2 } from '@angular/core';

@Directive({
  selector: '[uiVerticalWizard]'
})
export class VerticalWizardDirective {
  // tslint:disable-next-line: variable-name
  constructor(private _renderer: Renderer2, private _elRef: ElementRef, private changeDetection: ChangeDetectorRef) { }

  updateStepper() {
    const steppers = (this._elRef.nativeElement as HTMLElement).getElementsByClassName('vertical-step');
    let stepheight = 0;
    if (steppers.length) {
      stepheight = steppers[0].getClientRects()[0].height;
    }
    if (steppers.length === 1) {
      steppers[0].classList.remove('flex-col');
    }

    if (stepheight > 1 && steppers.length) {
      const stepperPadding =
        steppers.length === 2
          ? steppers[0].getClientRects()[0].height * (steppers.length - 1) + steppers[0].getClientRects()[0].height / 2
          : steppers[0].getClientRects()[0].height * (steppers.length - 1) * 1.5;

      this._renderer.setAttribute(this._elRef.nativeElement, 'style', `--ver-step-pad:${stepperPadding}px;`);
    }
  }
}
