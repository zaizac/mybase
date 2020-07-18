import { CropperPosition } from './interfaces';

export class ImageCropperConfig{

// imageChangedEvent: FileEvent;
    imageFile?: Blob;
    imageBase64?: string;
    format?: string;
    outputType?: any;
    aspectRatio?: number;
    maintainAspectRatio?: boolean;
    containWithinAspectRatio?: boolean;
    resizeToWidth?: number;
    resizeToHeight?: number;
    cropperMinWidth?: number;
    cropperMinHeight?: number;
    onlyScaleDown?: boolean;
    cropper?: CropperPosition;
    roundCropper?: boolean;
    imageQuality?: number;
    autoCrop?: boolean;
    alignImage?: string;
    backgroundColor?: string;
    preview: boolean;
}