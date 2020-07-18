import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { ImageCropperComponent } from 'projects/ui-master/src/lib/component/image-cropper/image-cropper.component';
import { ImageCroppedEvent } from 'projects/ui-master/src/lib/component/image-cropper/interfaces';
import { ModalService } from 'projects/ui-master/src/public-api';


@Component({
  selector: 'app-imagecropper',
  templateUrl: './imagecropper.component.html',
  styleUrls: ['./imagecropper.component.scss']
})
export class ImagecropperComponent implements OnInit {

  @ViewChild('cropper') cropper: TemplateRef<any>;
  // IMG_CROPPER = {

  // } as ImageCropperConfig;

  constructor(public modalSvc: ModalService) { }

  ngOnInit() {
  }

  croppedWidth: number;
  croppedHeight: number;
  imageChangedEvent: any = '';
  croppedImage: any = '';
  showCropper = false;
  event: ImageCroppedEvent;

  @ViewChild(ImageCropperComponent) imageCropper: ImageCropperComponent;

  fileChangeEvent(event: any): void {
    this.imageChangedEvent = event;
    this.modalSvc.openDefault(this.cropper);
  }

  imageCropped(event: ImageCroppedEvent) {
    this.event = event;
  }

  imageLoaded() {
    this.showCropper = true;
  }

  cropperReady() {
  }

  loadImageFailed() {
  }

  crop() {
    this.modalSvc.dismiss();
    this.croppedImage = this.event.base64;
  }

  cancel() {
    this.modalSvc.dismiss();
  }

}
