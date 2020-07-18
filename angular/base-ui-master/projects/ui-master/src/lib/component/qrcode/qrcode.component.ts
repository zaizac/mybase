import { ChangeDetectionStrategy, Component, ElementRef, Input, OnChanges, Renderer2, ViewChild } from '@angular/core';
import QRCode from 'qrcode';
import { QrcodeElementTypes } from './qrcode.types';


@Component({
  selector: 'ui-qrcode',
  template: `<div #qrcodeElement></div>`,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class QrcodeComponent implements OnChanges {

  @Input() elementType : string;
  @Input() alt: string;
  @Input() colorDark : '#000';
  @Input() colorLight : '#FFF';
  @Input() value : string;
  @Input() version : string;
  @Input() errorCorrectionLevel : string;
  @Input() margin : 5;
  @Input() scale : 5;
  @Input() width : 10;

  @ViewChild('qrcodeElement') qrcodeElement: ElementRef;

  constructor(private renderer: Renderer2) {
  }

  ngOnChanges() {
    this.createQRCode();
  }

  createQRCode() {
    if (!this.value) {
      return;
    }

    let element: Element;

    switch (this.elementType) {

      case QrcodeElementTypes.CANVAS:
        element = this.renderer.createElement('canvas');
        this.toCanvas(element).then(() => {
          this.renderElement(element);
        }).catch(e => {
          this.removeElementChild();
        });
        break;
      default:
        element = this.renderer.createElement('img');
        this.toDataURL().then((src: string) => {
          element.setAttribute('src', src);
          if (this.alt) {
            element.setAttribute('alt', this.alt);
          }
          this.renderElement(element);
        }).catch(e => {
          this.removeElementChild();
        });
    }
  }

  private toDataURL(): Promise<string> {
    return QRCode.toDataURL(this.value,
      {
        version: this.version,
        errorCorrectionLevel: this.errorCorrectionLevel,
        margin: this.margin,
        scale: this.scale,
        width: this.width,
        color: {
          dark: this.colorDark,
          light: this.colorLight
        }
      });
  }

  private toCanvas(canvas): Promise<any> {
    return QRCode.toCanvas(canvas, this.value, {
      version: this.version,
      errorCorrectionLevel: this.errorCorrectionLevel,
      margin: this.margin,
      scale: this.scale,
      width: this.width,
      color: {
        dark: this.colorDark,
        light: this.colorLight
      }
    });
  }

  private renderElement(element): void {
    this.removeElementChild();
    this.renderer.appendChild(this.qrcodeElement.nativeElement, element);
  }

  private removeElementChild(): void {
    for (const node of this.qrcodeElement.nativeElement.childNodes) {
      this.renderer.removeChild(this.qrcodeElement.nativeElement, node);
    }
  }
}

