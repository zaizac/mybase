import {
    Component, ElementRef, EventEmitter, HostBinding, HostListener, Input, OnChanges, Output,
    SimpleChanges, ChangeDetectorRef, ChangeDetectionStrategy, NgZone, ViewChild, AfterViewInit, OnInit
} from '@angular/core';
import { DomSanitizer, SafeUrl, SafeStyle } from '@angular/platform-browser';
import { MoveStart, Dimensions, CropperPosition, ImageCroppedEvent } from './interfaces';
import { resetExifOrientation, transformBase64BasedOnExifRotation, zoomBase64 } from './utils/exif.utils';
import { resizeCanvas } from './utils/resize.utils';
import { ImageCropperConfig } from './image-cropper.config';

export type OutputType = 'base64' | 'file' | 'both';

@Component({
    selector: 'image-cropper',
    templateUrl: './image-cropper.component.html',
    styleUrls: ['./image-cropper.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ImageCropperComponent implements OnChanges {
    private originalImage: any;
    private originalBase64: string;
    private moveStart: MoveStart;
    private maxSize: Dimensions;
    private originalSize: Dimensions;
    private setImageMaxSizeRetries = 0;
    private cropperScaledMinWidth = 20;
    private cropperScaledMinHeight = 20;
    private zoomLevel = 0;

    safeImgDataUrl: SafeUrl | string;
    marginLeft: SafeStyle | string = '0px';
    imageVisible = false;
    fileLoaded: File;
    srcImage: any;

    @ViewChild('sourceImage') sourceImage: ElementRef;
    @ViewChild('cropCanvas') cropcanvas: ElementRef;

    @Input()
    set imageFileChanged(file: File) {
        this.initCropper();
        if (file) {
            this.loadImageFile(file);
        }
    }

    @Input()
    set imageChangedEvent(event: any) {
        this.initCropper();
        if (event && event.target && event.target.files && event.target.files.length > 0) {
            this.loadImageFile(event.target.files[0]);


        }
    }

    @Input()
    set imageBase64(imageBase64: string) {
        this.initCropper();
        this.loadBase64Image(imageBase64);
    }

    DFLT_IMG_CRPPR = {
        format: 'png',
        outputType: 'both',
        maintainAspectRatio: true,
        aspectRatio: 1,
        resizeToWidth: 0,
        resizeToHeight: 0,
        cropperMinWidth: 0,
        cropperMinHeight: 0,
        roundCropper: false,
        onlyScaleDown: false,
        imageQuality: 92,
        autoCrop: true,
        cropper: {
            x1: -100,
            y1: -100,
            x2: 10000,
            y2: 10000
        },
        alignImage: 'center',
        preview: true
    } as ImageCropperConfig;

    @Input() config: ImageCropperConfig;
    @HostBinding('style.text-align')

    @Output() startCropImage = new EventEmitter<void>();
    @Output() imageCroppedBase64 = new EventEmitter<string>();
    @Output() imageCroppedFile = new EventEmitter<Blob>();

    @Output() imageCropped = new EventEmitter<ImageCroppedEvent>();
    @Output() imageLoaded = new EventEmitter<void>();
    @Output() cropperReady = new EventEmitter<void>();
    @Output() loadImageFailed = new EventEmitter<void>();

    constructor(private sanitizer: DomSanitizer,
        private cd: ChangeDetectorRef,
        private zone: NgZone) {
        // this.initCropper();
    }

    ngOnChanges(changes: SimpleChanges): void {
        this.config = Object.assign(this.DFLT_IMG_CRPPR, this.config);

        if (changes.cropper) {
            this.setMaxSize();
            this.setCropperScaledMinSize();
            this.checkCropperPosition(false);
            this.doAutoCrop();
            this.cd.markForCheck();
        }
        if (changes.aspectRatio && this.imageVisible) {
            this.resetCropperPosition();
        }
    }

    private initCropper(): void {
        this.config = Object.assign(this.DFLT_IMG_CRPPR, this.config);

        this.imageVisible = false;
        this.originalImage = null;
        this.safeImgDataUrl = 'data:image/png;base64,iVBORw0KGg'
            + 'oAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAC0lEQVQYV2NgAAIAAAU'
            + 'AAarVyFEAAAAASUVORK5CYII=';

        this.moveStart = {
            active: false,
            type: null,
            position: null,
            x1: 0,
            y1: 0,
            x2: 0,
            y2: 0,
            clientX: 0,
            clientY: 0
        };
        this.maxSize = {
            width: 0,
            height: 0
        };
        this.originalSize = {
            width: 0,
            height: 0
        };
        this.config.cropper.x1 = -100;
        this.config.cropper.y1 = -100;
        this.config.cropper.x2 = 10000;
        this.config.cropper.y2 = 10000;
    }

    private loadImageFile(file: File, successFn?: Function): void {
        this.fileLoaded = file;
        const fileReader = new FileReader();
        fileReader.onload = (event: any) => {
            const imageType = file.type;
            if (this.isValidImageType(imageType)) {
                resetExifOrientation(event.target.result)
                    .then((resultBase64: string) => this.loadBase64Image(resultBase64, successFn))
                    .catch(() => this.loadImageFailed.emit());
            } else {
                this.loadImageFailed.emit();
            }
        };
        fileReader.readAsDataURL(file);
    }

    private isValidImageType(type: string): boolean {
        return /image\/(png|jpg|jpeg|bmp|gif|tiff)/.test(type);
    }

    private loadBase64Image(imageBase64: string, callbackFn?: Function): void {
        this.originalBase64 = imageBase64;
        this.safeImgDataUrl = this.sanitizer.bypassSecurityTrustResourceUrl(imageBase64);
        this.originalImage = new Image();
        this.originalImage.onload = () => {
            this.originalSize.width = this.originalImage.width;
            this.originalSize.height = this.originalImage.height;
            this.cd.markForCheck();

            if (callbackFn) {
                callbackFn();
            }
        };
        this.originalImage.src = imageBase64;
    }

    imageLoadedInView(): void {
        if (this.originalImage != null) {
            this.imageLoaded.emit();
            this.setImageMaxSizeRetries = 0;
            setTimeout(() => this.checkImageMaxSizeRecursively());
        }
    }

    private checkImageMaxSizeRecursively(): void {
        if (this.setImageMaxSizeRetries > 40) {
            this.loadImageFailed.emit();
        } else if (this.sourceImage && this.sourceImage.nativeElement && this.sourceImage.nativeElement.offsetWidth > 0) {
            this.setMaxSize();
            this.setCropperScaledMinSize();
            this.resetCropperPosition();
            this.cropperReady.emit();
            this.cd.markForCheck();
        } else {
            this.setImageMaxSizeRetries++;
            setTimeout(() => {
                this.checkImageMaxSizeRecursively();
            }, 50);
        }
    }

    @HostListener('window:resize')
    onResize(): void {
        this.resizeCropperPosition();
        this.setMaxSize();
        this.setCropperScaledMinSize();
    }

    private resizeCropperPosition(): void {
        const sourceImageElement = this.sourceImage.nativeElement;
        if (this.maxSize.width !== sourceImageElement.offsetWidth || this.maxSize.height !== sourceImageElement.offsetHeight) {
            this.config.cropper.x1 = this.config.cropper.x1 * sourceImageElement.offsetWidth / this.maxSize.width;
            this.config.cropper.x2 = this.config.cropper.x2 * sourceImageElement.offsetWidth / this.maxSize.width;
            this.config.cropper.y1 = this.config.cropper.y1 * sourceImageElement.offsetHeight / this.maxSize.height;
            this.config.cropper.y2 = this.config.cropper.y2 * sourceImageElement.offsetHeight / this.maxSize.height;
        }
    }

    private resetCropperPosition(): void {
        const sourceImageElement = this.sourceImage.nativeElement;
        if (!this.config.maintainAspectRatio) {
            this.config.cropper.x1 = 0;
            this.config.cropper.x2 = sourceImageElement.offsetWidth;
            this.config.cropper.y1 = 0;
            this.config.cropper.y2 = sourceImageElement.offsetHeight;
        } else if (sourceImageElement.offsetWidth / this.config.aspectRatio < sourceImageElement.offsetHeight) {
            this.config.cropper.x1 = 0;
            this.config.cropper.x2 = sourceImageElement.offsetWidth;
            const cropperHeight = sourceImageElement.offsetWidth / this.config.aspectRatio;
            this.config.cropper.y1 = (sourceImageElement.offsetHeight - cropperHeight) / 2;
            this.config.cropper.y2 = this.config.cropper.y1 + cropperHeight;
        } else {
            this.config.cropper.y1 = 0;
            this.config.cropper.y2 = sourceImageElement.offsetHeight;
            const cropperWidth = sourceImageElement.offsetHeight * this.config.aspectRatio;
            this.config.cropper.x1 = (sourceImageElement.offsetWidth - cropperWidth) / 2;
            this.config.cropper.x2 = this.config.cropper.x1 + cropperWidth;
        }
        this.doAutoCrop();
        this.imageVisible = true;
    }

    startMove(event: any, moveType: string, position: string | null = null): void {
        event.preventDefault();
        this.moveStart = {
            active: true,
            type: moveType,
            position,
            clientX: this.getClientX(event),
            clientY: this.getClientY(event),
            ...this.config.cropper
        };
    }

    @HostListener('document:mousemove', ['$event'])
    @HostListener('document:touchmove', ['$event'])
    moveImg(event: any): void {
        if (this.moveStart.active) {
            event.stopPropagation();
            event.preventDefault();
            if (this.moveStart.type === 'move') {
                this.move(event);
                this.checkCropperPosition(true);
            } else if (this.moveStart.type === 'resize') {
                this.resize(event);
                this.checkCropperPosition(false);
            }
            this.cd.detectChanges();
        }
    }

    private setMaxSize(): void {
        const sourceImageElement = this.sourceImage.nativeElement;
        this.maxSize.width = sourceImageElement.offsetWidth;
        this.maxSize.height = sourceImageElement.offsetHeight;
        this.marginLeft = this.sanitizer.bypassSecurityTrustStyle('calc(50% - ' + this.maxSize.width / 2 + 'px)');
    }

    private setCropperScaledMinSize(): void {
        if (this.originalImage) {
            this.setCropperScaledMinWidth();
            this.setCropperScaledMinHeight();
        } else {
            this.cropperScaledMinWidth = 20;
            this.cropperScaledMinHeight = 20;
        }
    }

    private setCropperScaledMinWidth(): void {
        this.cropperScaledMinWidth = this.config.cropperMinWidth > 0
            ? Math.max(20, this.config.cropperMinWidth / this.originalImage.width * this.maxSize.width)
            : 20;
    }

    private setCropperScaledMinHeight(): void {
        if (this.config.maintainAspectRatio) {
            this.cropperScaledMinHeight = Math.max(20, this.cropperScaledMinWidth / this.config.aspectRatio);
        } else if (this.config.cropperMinHeight > 0) {
            this.cropperScaledMinHeight = Math.max(20, this.config.cropperMinHeight / this.originalImage.height * this.maxSize.height);
        } else {
            this.cropperScaledMinHeight = 20;
        }
    }

    private checkCropperPosition(maintainSize = false): void {
        if (this.config.cropper.x1 < 0) {
            this.config.cropper.x2 -= maintainSize ? this.config.cropper.x1 : 0;
            this.config.cropper.x1 = 0;
        }
        if (this.config.cropper.y1 < 0) {
            this.config.cropper.y2 -= maintainSize ? this.config.cropper.y1 : 0;
            this.config.cropper.y1 = 0;
        }
        if (this.config.cropper.x2 > this.maxSize.width) {
            this.config.cropper.x1 -= maintainSize ? (this.config.cropper.x2 - this.maxSize.width) : 0;
            this.config.cropper.x2 = this.maxSize.width;
        }
        if (this.config.cropper.y2 > this.maxSize.height) {
            this.config.cropper.y1 -= maintainSize ? (this.config.cropper.y2 - this.maxSize.height) : 0;
            this.config.cropper.y2 = this.maxSize.height;
        }
    }

    @HostListener('document:mouseup')
    @HostListener('document:touchend')
    moveStop(): void {
        if (this.moveStart.active) {
            this.moveStart.active = false;
            this.doAutoCrop();
        }
    }

    private move(event: any) {
        const diffX = this.getClientX(event) - this.moveStart.clientX;
        const diffY = this.getClientY(event) - this.moveStart.clientY;

        this.config.cropper.x1 = this.moveStart.x1 + diffX;
        this.config.cropper.y1 = this.moveStart.y1 + diffY;
        this.config.cropper.x2 = this.moveStart.x2 + diffX;
        this.config.cropper.y2 = this.moveStart.y2 + diffY;
    }

    private resize(event: any): void {
        const diffX = this.getClientX(event) - this.moveStart.clientX;
        const diffY = this.getClientY(event) - this.moveStart.clientY;
        switch (this.moveStart.position) {
            case 'left':
                this.config.cropper.x1 = Math.min(this.moveStart.x1 + diffX, this.config.cropper.x2 - this.cropperScaledMinWidth);
                break;
            case 'topleft':
                this.config.cropper.x1 = Math.min(this.moveStart.x1 + diffX, this.config.cropper.x2 - this.cropperScaledMinWidth);
                this.config.cropper.y1 = Math.min(this.moveStart.y1 + diffY, this.config.cropper.y2 - this.cropperScaledMinHeight);
                break;
            case 'top':
                this.config.cropper.y1 = Math.min(this.moveStart.y1 + diffY, this.config.cropper.y2 - this.cropperScaledMinHeight);
                break;
            case 'topright':
                this.config.cropper.x2 = Math.max(this.moveStart.x2 + diffX, this.config.cropper.x1 + this.cropperScaledMinWidth);
                this.config.cropper.y1 = Math.min(this.moveStart.y1 + diffY, this.config.cropper.y2 - this.cropperScaledMinHeight);
                break;
            case 'right':
                this.config.cropper.x2 = Math.max(this.moveStart.x2 + diffX, this.config.cropper.x1 + this.cropperScaledMinWidth);
                break;
            case 'bottomright':
                this.config.cropper.x2 = Math.max(this.moveStart.x2 + diffX, this.config.cropper.x1 + this.cropperScaledMinWidth);
                this.config.cropper.y2 = Math.max(this.moveStart.y2 + diffY, this.config.cropper.y1 + this.cropperScaledMinHeight);
                break;
            case 'bottom':
                this.config.cropper.y2 = Math.max(this.moveStart.y2 + diffY, this.config.cropper.y1 + this.cropperScaledMinHeight);
                break;
            case 'bottomleft':
                this.config.cropper.x1 = Math.min(this.moveStart.x1 + diffX, this.config.cropper.x2 - this.cropperScaledMinWidth);
                this.config.cropper.y2 = Math.max(this.moveStart.y2 + diffY, this.config.cropper.y1 + this.cropperScaledMinHeight);
                break;
        }

        if (this.config.maintainAspectRatio) {
            this.checkAspectRatio();
        }
    }

    private checkAspectRatio(): void {
        let overflowX = 0;
        let overflowY = 0;

        switch (this.moveStart.position) {
            case 'top':
                this.config.cropper.x2 = this.config.cropper.x1 + (this.config.cropper.y2 - this.config.cropper.y1) * this.config.aspectRatio;
                overflowX = Math.max(this.config.cropper.x2 - this.maxSize.width, 0);
                overflowY = Math.max(0 - this.config.cropper.y1, 0);
                if (overflowX > 0 || overflowY > 0) {
                    this.config.cropper.x2 -= (overflowY * this.config.aspectRatio) > overflowX ? (overflowY * this.config.aspectRatio) : overflowX;
                    this.config.cropper.y1 += (overflowY * this.config.aspectRatio) > overflowX ? overflowY : overflowX / this.config.aspectRatio;
                }
                break;
            case 'bottom':
                this.config.cropper.x2 = this.config.cropper.x1 + (this.config.cropper.y2 - this.config.cropper.y1) * this.config.aspectRatio;
                overflowX = Math.max(this.config.cropper.x2 - this.maxSize.width, 0);
                overflowY = Math.max(this.config.cropper.y2 - this.maxSize.height, 0);
                if (overflowX > 0 || overflowY > 0) {
                    this.config.cropper.x2 -= (overflowY * this.config.aspectRatio) > overflowX ? (overflowY * this.config.aspectRatio) : overflowX;
                    this.config.cropper.y2 -= (overflowY * this.config.aspectRatio) > overflowX ? overflowY : (overflowX / this.config.aspectRatio);
                }
                break;
            case 'topleft':
                this.config.cropper.y1 = this.config.cropper.y2 - (this.config.cropper.x2 - this.config.cropper.x1) / this.config.aspectRatio;
                overflowX = Math.max(0 - this.config.cropper.x1, 0);
                overflowY = Math.max(0 - this.config.cropper.y1, 0);
                if (overflowX > 0 || overflowY > 0) {
                    this.config.cropper.x1 += (overflowY * this.config.aspectRatio) > overflowX ? (overflowY * this.config.aspectRatio) : overflowX;
                    this.config.cropper.y1 += (overflowY * this.config.aspectRatio) > overflowX ? overflowY : overflowX / this.config.aspectRatio;
                }
                break;
            case 'topright':
                this.config.cropper.y1 = this.config.cropper.y2 - (this.config.cropper.x2 - this.config.cropper.x1) / this.config.aspectRatio;
                overflowX = Math.max(this.config.cropper.x2 - this.maxSize.width, 0);
                overflowY = Math.max(0 - this.config.cropper.y1, 0);
                if (overflowX > 0 || overflowY > 0) {
                    this.config.cropper.x2 -= (overflowY * this.config.aspectRatio) > overflowX ? (overflowY * this.config.aspectRatio) : overflowX;
                    this.config.cropper.y1 += (overflowY * this.config.aspectRatio) > overflowX ? overflowY : overflowX / this.config.aspectRatio;
                }
                break;
            case 'right':
            case 'bottomright':
                this.config.cropper.y2 = this.config.cropper.y1 + (this.config.cropper.x2 - this.config.cropper.x1) / this.config.aspectRatio;
                overflowX = Math.max(this.config.cropper.x2 - this.maxSize.width, 0);
                overflowY = Math.max(this.config.cropper.y2 - this.maxSize.height, 0);
                if (overflowX > 0 || overflowY > 0) {
                    this.config.cropper.x2 -= (overflowY * this.config.aspectRatio) > overflowX ? (overflowY * this.config.aspectRatio) : overflowX;
                    this.config.cropper.y2 -= (overflowY * this.config.aspectRatio) > overflowX ? overflowY : overflowX / this.config.aspectRatio;
                }
                break;
            case 'left':
            case 'bottomleft':
                this.config.cropper.y2 = this.config.cropper.y1 + (this.config.cropper.x2 - this.config.cropper.x1) / this.config.aspectRatio;
                overflowX = Math.max(0 - this.config.cropper.x1, 0);
                overflowY = Math.max(this.config.cropper.y2 - this.maxSize.height, 0);
                if (overflowX > 0 || overflowY > 0) {
                    this.config.cropper.x1 += (overflowY * this.config.aspectRatio) > overflowX ? (overflowY * this.config.aspectRatio) : overflowX;
                    this.config.cropper.y2 -= (overflowY * this.config.aspectRatio) > overflowX ? overflowY : overflowX / this.config.aspectRatio;
                }
                break;
        }
    }

    private doAutoCrop(): void {
        if (this.config.autoCrop) {
            this.crop();
        }
    }

    crop(outputType: OutputType = this.config.outputType): ImageCroppedEvent | Promise<ImageCroppedEvent> | null {
        this.startCropImage.emit();
        const imagePosition = this.getImagePosition();
        const width = imagePosition.x2 - imagePosition.x1;
        const height = imagePosition.y2 - imagePosition.y1;

        const cropCanvas = document.createElement('canvas') as HTMLCanvasElement;
        cropCanvas.width = width;
        cropCanvas.height = height;

        const ctx = cropCanvas.getContext('2d');
        if (ctx) {
            ctx.drawImage(
                this.originalImage,
                imagePosition.x1,
                imagePosition.y1,
                width,
                height,
                0,
                0,
                width,
                height
            );
            const output = { width, height, imagePosition, cropperPosition: this.config.cropper };
            const resizeRatio = this.getResizeRatio(width, height);
            if (resizeRatio !== 1) {
                output.width = Math.floor(width * resizeRatio);
                output.height = Math.floor(height * resizeRatio);
                resizeCanvas(cropCanvas, output.width, output.height);
            }
            return this.cropToOutputType(outputType, cropCanvas, output);
        }

        return null;
    }

    private getImagePosition(): CropperPosition {
        const sourceImageElement = this.sourceImage.nativeElement;
        const ratio = this.originalSize.width / sourceImageElement.offsetWidth;
        return {
            x1: Math.round(this.config.cropper.x1 * ratio),
            y1: Math.round(this.config.cropper.y1 * ratio),
            x2: Math.min(Math.round(this.config.cropper.x2 * ratio), this.originalSize.width),
            y2: Math.min(Math.round(this.config.cropper.y2 * ratio), this.originalSize.height)
        };
    }

    private cropToOutputType(outputType: OutputType, cropCanvas: HTMLCanvasElement, output: ImageCroppedEvent): ImageCroppedEvent | Promise<ImageCroppedEvent> {
        switch (outputType) {
            case 'file':
                return this.cropToFile(cropCanvas)
                    .then((result: Blob | null) => {
                        output.file = result;
                        this.imageCropped.emit(output);
                        return output;
                    });
            case 'both':
                output.base64 = this.cropToBase64(cropCanvas);
                return this.cropToFile(cropCanvas)
                    .then((result: Blob | null) => {
                        output.file = result;
                        // this.outputImg = output;
                        this.imageCropped.emit(output);
                        return output;
                    });
            default:
                output.base64 = this.cropToBase64(cropCanvas);
                this.imageCropped.emit(output);
                return output;
        }
    }

    private cropToBase64(cropCanvas: HTMLCanvasElement): string {
        const imageBase64 = cropCanvas.toDataURL('image/' + this.config.format, this.getQuality());
        this.imageCroppedBase64.emit(imageBase64);
        return imageBase64;
    }

    private cropToFile(cropCanvas: HTMLCanvasElement): Promise<Blob | null> {
        return this.getCanvasBlob(cropCanvas)
            .then((result: Blob | null) => {
                if (result) {
                    this.imageCroppedFile.emit(result);
                }
                return result;
            });
    }

    private getCanvasBlob(cropCanvas: HTMLCanvasElement): Promise<Blob | null> {
        return new Promise((resolve) => {
            cropCanvas.toBlob(
                (result: Blob | null) => this.zone.run(() => resolve(result)),
                'image/' + this.config.format,
                this.getQuality()
            );
        });
    }

    private getQuality(): number {
        return Math.min(1, Math.max(0, this.config.imageQuality / 100));
    }

    private getResizeRatio(width: number, height: number): number {
        if (this.config.resizeToWidth > 0) {
            if (!this.config.onlyScaleDown || width > this.config.resizeToWidth) {
                return this.config.resizeToWidth / width;
            }
        } else if (this.config.resizeToHeight > 0) {
            if (!this.config.onlyScaleDown || height > this.config.resizeToHeight) {
                return this.config.resizeToHeight / height;
            }
        }
        return 1;

    }

    private getClientX(event: any): number {
        return event.clientX || event.touches && event.touches[0] && event.touches[0].clientX;
    }

    private getClientY(event: any): number {
        return event.clientY || event.touches && event.touches[0] && event.touches[0].clientY;
    }

    zoomIn() {
        if (this.fileLoaded) {
            this.loadImageFile(this.fileLoaded, () => {
                this.zoomLevel++;
                zoomBase64(this.originalBase64, this.zoomLevel)
                    .then((zoomedBase64: string) => this.loadBase64Image(zoomedBase64));
            });
        }
    }

    zoomOut() {
        if (this.fileLoaded) {
            this.zoomLevel--;
            this.loadImageFile(this.fileLoaded, () => {
                zoomBase64(this.originalBase64, this.zoomLevel)
                    .then((zoomedBase64: string) => this.loadBase64Image(zoomedBase64));
            });
        }
    }

    resetImage() {
        this.imageBase64 = this.originalBase64;
    }
}
