import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
  name: 'safeImageFile'
})
export class SafeImageFilePipe implements PipeTransform {

 constructor(private sanitizer: DomSanitizer) {}
  transform(imgPrevSrc) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(imgPrevSrc);
  }
}
