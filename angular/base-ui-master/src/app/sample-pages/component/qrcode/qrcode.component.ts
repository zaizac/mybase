import { Component } from '@angular/core';

@Component({
  selector: 'app-qrcode',
  templateUrl: './qrcode.component.html'
})
export class QrcodeComponent {

  qrcodename: string;
  title = 'generate-qrcode';
  elementType: 'url' | 'canvas' | 'img' = 'url';
  value: string;
  display = false;

  generateQRCode() {
    if (this.qrcodename === '') {
      this.display = false;
      return;
    }
    else {
      this.value = this.qrcodename;
      this.display = true;
    }
  }
}
