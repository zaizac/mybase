import { ChangeDetectorRef, Directive, ElementRef, Renderer2 } from '@angular/core';

@Directive({
  selector: '[uiHorizontalWizard]'
})
export class HorizontalWizardDirective {
  // tslint:disable-next-line: variable-name
  constructor(private _renderer: Renderer2, private _elRef: ElementRef, private changeDetection: ChangeDetectorRef) {}

  updateStepper() {
    const steppers = (this._elRef.nativeElement as HTMLElement).getElementsByClassName('horizontal-step');
    switch (steppers.length) {
      case 1:
      case 2:
        this._renderer.setAttribute(this._elRef.nativeElement, 'style', `--wiz-length:50%;`);
        break;
      case 3:
        this._renderer.setAttribute(this._elRef.nativeElement, 'style', `--wiz-length:70%;`);
        break;
      case 4:
        this._renderer.setAttribute(this._elRef.nativeElement, 'style', `--wiz-length:75%;`);
        break;
      case 5:
      case 6:
        this._renderer.setAttribute(this._elRef.nativeElement, 'style', `--wiz-length:80%;`);
        break;
      case 7:
        this._renderer.setAttribute(this._elRef.nativeElement, 'style', `--wiz-length:85%;`);
        break;
      default:
        this._renderer.setAttribute(this._elRef.nativeElement, 'style', `--wiz-length:90%;`);
        break;
    }

    const stepLabel = (this._elRef.nativeElement as HTMLElement).getElementsByClassName('step-label');
    let labelheight = 50;
    Array.from(stepLabel).forEach((label: HTMLElement) => {
      labelheight = label.getClientRects()[0].height > labelheight ? label.getClientRects()[0].height : labelheight;
    });

    this._renderer.setStyle(this._elRef.nativeElement, 'margin-bottom', `${labelheight + 10}px`);
  }
}
