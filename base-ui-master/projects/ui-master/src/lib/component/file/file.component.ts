import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Input, OnChanges, OnInit, TemplateRef, ViewChild, PipeTransform, Pipe } from '@angular/core';
import { ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { forkJoin, Observable, of, Subscriber } from 'rxjs';
import { first, map, switchMap } from 'rxjs/operators';
import { v4 as uuid } from 'uuid';
import { ModalService } from '../../notifier/modal/modal.service';
import { UiMasterConfig } from '../../ui-master.config';
import { FileUploadConfig } from './fileUpload.config';
import { File } from './model/file';
import { FileConfig } from './model/file.config';
import { SafeImageFilePipe } from '../../utility/safe-image-file.pipe';

@Component({
  selector: 'ui-file',
  templateUrl: './file.component.html',
  styleUrls: ['./file.component.scss'],
  providers: [{ provide: NG_VALUE_ACCESSOR, useExisting: FileComponent, multi: true }]
})
export class FileComponent implements OnInit, OnChanges, ControlValueAccessor {
  @Input() refDoc: FileConfig;
  @Input() init: FileUploadConfig;
  @Input() docMgtId: string | string[];
  @Input() dmBucket: string
  @Input() submitted: boolean;
  @Input() reset: boolean;
  @ViewChild('modalContent') modalContent: TemplateRef<any>;
  multiple = false;
  canUpload = true;

  safeImageFile : SafeImageFilePipe;

  DFLT_CONFIG = {
    type: 'file',
    viewOnly: false,
    textAlign: 'center',
    width: '100%',
    height: '100%',
    fileClass: 'fileupload',
    // inputClass: 'form-control fileinput-ellipsis',
    noImgSrc: 'assets/default/no_image.jpg',
    radius: '4px',
    autoUpload: false,
    preview: true
  } as FileUploadConfig;

  hasImg = false;
  imgPrevSrc: string;
  imgPrevSrcList: any[] = [];
  origImgPrevSrc: string;
  origDoc: File;
  origDocList: File[] = [];
  upldFile: File;
  upldFiles: File[] = [];
  fuLabel: string;
  filename: string;
  // type: string
  errorMessage: string;
  imgURL: any;
  downloadUrl = `/api/references/documents/download/content/`;
  disabled = false;
  fcFile: FormControl;
  onTouched = () => { };
  propagateChange = (_: any) => { };

  constructor(
    public modalService: ModalService,
    private config: UiMasterConfig,
    private http: HttpClient,
    public sanitizer: DomSanitizer
  ) {
    this.downloadUrl = this.config.url + this.downloadUrl;
    this.fcFile = new FormControl();
  }

  ngOnInit() {
    this.configInit();
    this.imgPreviewSrc();
    this.updateFuLabel();
    if (this.refDoc && this.refDoc.maxDocCnt && this.refDoc.maxDocCnt > 1) {
      this.multiple = true;
    }
  }

  ngOnChanges() {
    if (this.submitted && this.errorMessage == null) {
      this.validate(null);
    } else if (this.reset) {
      this.fcFile.markAsUntouched();
    }
  }

  private updateFuLabel() {
    this.fuLabel = this.hasImg ? 'Change' : 'Select File';
  }

  writeValue(obj: any): void {
    if (obj) {
      if (Array.isArray(obj)) {
        this.upldFiles = [...obj];
        this.origDocList = [...obj];
        obj.forEach(fu => {
          this.imgPreviewSrc(fu);
        });
      } else {
        this.origDoc = obj;
        this.origDocList.push(obj);
        this.imgPreviewSrc();
      }

       this.origImgPrevSrc = this.imgPrevSrc;
      this.imgPrevSrc = this.origImgPrevSrc;
      if (this.origDoc && this.origDoc.docMgtId) {
        this.hasImg = true;
        this.updateFuLabel();
      } else {
        this.hasImg = false;
        this.updateFuLabel();
      }
    }
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  onFileChange(event) {
    const files = Object.values(event.target.files);

    if (!files.length) {
      return;
    }

    if (
      this.multiple &&
      this.refDoc &&
      (files.length > this.refDoc.maxDocCnt || this.imgPrevSrcList.length + files.length > this.refDoc.maxDocCnt)
    ) {
      this.setError(`You have reached maximum (${this.refDoc.maxDocCnt}) allowed uploaded document`);
      return;
    }

    if (this.refDoc.maxDocCnt && this.refDoc.maxDocCnt > 1 && this.upldFiles.length) {
      let fileNameError = false;
      files.forEach((file: any) => {
        if (this.upldFiles.find(fl => fl.filename === file.name)) {
          this.setError('Unable to upload file with same name');
          fileNameError = true;
          return;
        }
      });
      if (fileNameError) {
        return;
      }
    }

    const observables: Observable<any>[] = [];
    files.forEach((file: any) => {
      this.validate(file);
      if (this.fcFile.invalid) {
        this.imgPreviewSrc();
        this.updateFuLabel();
        return;
      }

      if (this.init.type !== 'thumbnail') {
        this.filename = file.name;
      } else {
        this.upldFiles.length = 0;
      }

      this.readFileAsUrl(file, _event => {
        this.imgPrevSrc = _event.target.result;
        this.imgPrevSrcList.push({
          name: file.name,
          data: _event.target.result,
          isReady: !this.init.autoUpload
        });
        if (!this.multiple && this.imgPrevSrcList.length > 1) {
          this.imgPrevSrcList.splice(0, 1);
        }
        this.hasImg = true;
        // this.type = file.type;
        this.updateFuLabel();
      });
      const upldFile = new File({
        filename: file.name,
        size: file.size,
        contentType: file.type,
        docId: this.refDoc.docId,
        compulsary: this.refDoc.compulsary,
        dmBucket: this.refDoc.dmBucket
      });

      this.upldFile = upldFile;
      observables.push(
        this.readFileAsBinary(file).pipe(
          switchMap(bytes => {
            upldFile.content = btoa(bytes.toString());
            if (this.init.autoUpload) {
              this.canUpload = false;
              return this.upload(upldFile);
            } else {
              return of(bytes);
            }
          }),
          first(),
          map(data => {
            if (this.init.autoUpload) {
              upldFile.docMgtId = data.id;
            } else {
              upldFile.docMgtId = this.origDoc != null ? this.origDoc.docMgtId : null;
            }
            return upldFile;
          })
        )
      );
    });

    forkJoin(observables).subscribe(
      data => {
        data.forEach(fu => {
          this.upldFile = fu;
          this.upldFiles.push(fu);
          if (!this.multiple && this.upldFiles.length > 1) {
            this.upldFiles.splice(0, 1);
          }
        });
        this.propagateChange(this.upldFiles);
        this.imgPrevSrcList.forEach(lst => {
          lst.isReady = true;
        });
        this.canUpload = true;
      },
      error => {
        files.forEach((file: any) => {
          this.imgPrevSrcList.splice(
            this.imgPrevSrcList.findIndex(img => img.name === file.name),
            1
          );
        });
        this.canUpload = true;
        this.setError('Upload failed. Please try again');
      }
    );
    this.init.isImg = this.upldFile && this.upldFile.contentType.includes('image');
  }

  validate(file) {
    this.errorMessage = null;

    // Check compulsary, if file is required
    if (file === null || file === undefined || (file !== null && file.size === 0)) {
      if (this.refDoc && this.refDoc.compulsary) {
        // this.errorMessage = "Please attach file.";
        this.setError('Please attach file.');
      }
    } else {
      const docType = this.refDoc.type;
      // Check file type
      if (file.type === '' || (docType && !docType.toLowerCase().includes(file.type.toLowerCase()))) {
        this.setError('Attached file type is not supported.');
        // this.errorMessage = "Attached file type is not supported.";
      }
      // Check file size
      else if (file.size > this.refDoc.size) {
        this.setError('Invalid file size. Please upload file with maximum size of ' + this.refDoc.size + '.');
        // this.errorMessage = "Invalid file size. Please upload file with maximum size of " + this.refDoc.size + ".";
      }

      // Check dimension, if image, compulsory and a thumbnail
      else if (this.refDoc.dimensionCompulsary) {
        this.checkDimension(file, function (w, h) {
          if (
            (w > 0 && h > 0 && (w > this.refDoc.maxWidth || w < this.refDoc.minWidth)) ||
            h > this.refDoc.maxHeight ||
            h < this.refDoc.minHeight
          ) {
            this.setError(
              'Invalid dimension, please upload file which height is between {0}px and {1}px, width is between {2}px and {3}px'
            );
            // this.errorMessage = "Invalid dimension, please upload file which height is between {0}px and {1}px, width is between {2}px and {3}px";
          }
        });
      }
    }
  }

  setError(errorMessage: string) {
    if (errorMessage != null) {
      this.errorMessage = errorMessage;
      this.fcFile.setErrors({
        invalidFile: true
      });
      this.propagateChange(this.fcFile);
    }
  }

  preview(flname?: number) {
    if (flname !== null && this.imgPrevSrcList.length) {
       this.imgPrevSrc = this.imgPrevSrcList[this.imgPrevSrcList.findIndex(fl => fl.name === flname)].data;
    }

    if (this.imgPrevSrc.includes('no_image') || this.errorMessage || !this.init.preview) {
      return;
    }

    this.modalService.openDefault(this.modalContent);
  }

  removeImage(flname?: string, index?: number) {
    if (this.imgPrevSrcList.length === 1 && this.refDoc.compulsary && this.origDocList.length) {
      this.fcFile.markAsDirty();
      this.setError('Unable to remove attachment. Field is compulsary');
      return;
    }

    this.hasImg = false;

    if (this.init.type === 'thumbnail') {
      this.imgPrevSrc = this.refDoc.compulsary
        ? this.origImgPrevSrc
          ? this.origImgPrevSrc
          : this.init.noImgSrc
        : this.init.noImgSrc;
      if (this.imgPrevSrcList.length) {
        this.imgPrevSrcList.length = 0;
        this.imgPrevSrcList.push(this.imgPrevSrc);
        this.upldFiles.length = 0;
        this.upldFiles.push(this.origDoc);
      }
      this.hasImg = !this.imgPrevSrc.match(this.init.noImgSrc);
    } else {
      if (this.imgPrevSrcList.length) {
        this.imgPrevSrcList.splice(
          this.imgPrevSrcList.findIndex(img => img.name === flname),
          1
        );
      }
      if (this.upldFiles.length) {
        if (this.origDocList && this.origDocList.length) {
          this.upldFiles[this.upldFiles.findIndex(file => file.filename === flname)].remove = true;
        } else {
          this.upldFiles.splice(
            this.upldFiles.findIndex(file => file.filename === flname),
            1
          );
        }
      }
      this.filename = null;
    }

    this.errorMessage = '';
    this.updateFuLabel();
    // Clears the file input
    this.propagateChange(this.upldFiles);
  }

  private imgPreviewSrc(fu?: any) {
    if (fu) {
      this.origDoc = Object.assign({}, fu);
    }
    if (this.origDoc && this.origDoc.docMgtId) {
      const oriImg =
        this.config.url +
        `/api/references/documents/download/content/` +
        this.refDoc.dmBucket +
        `/` +
        this.origDoc.docMgtId;
      if (oriImg !== undefined) {
        this.imgPrevSrc = oriImg;
        this.init.isImg = this.refDoc.type.includes('image');
        if (fu) {
          this.imgPrevSrcList.push({
            name: this.origDoc.filename,
            data: oriImg,
            isReady: true
          });
        }
      }
    } else if (this.init.viewOnly && this.docMgtId) {
      const observables: Observable<any>[] = [];
      const docMgtIdList: string[] = Array.isArray(this.docMgtId) ? this.docMgtId : [this.docMgtId];

      docMgtIdList.forEach(id => {
        observables.push(
          this.download(id).pipe(
            map((fl: any) => {
              this.imgPrevSrcList.push({
                name: fl.filename,
                data: 'data:' + fl.contentType + ';base64,' + fl.content
              });
              return fl;
            })
          )
        );
      });
      forkJoin(observables).subscribe(
        (data: any) => {
          if (data.length) {
            const len = data.length;
            this.imgPrevSrc = 'data:' + data[len - 1].contentType + ';base64,' + data[len - 1].content;
            this.filename = data[len - 1].filename;
            this.origDoc = data[len - 1];
            this.init.isImg = data[len - 1].contentType.includes('image');
          }
        },
        error => {
          this.setDefaultImg();
        }
      );
    } else {
      this.setDefaultImg();
    }
  }

  setDefaultImg() {
    this.imgPrevSrc = this.init.noImgSrc !== undefined ? this.init.noImgSrc : this.DFLT_CONFIG.noImgSrc;
  }

  private readFileAsBinary(file): Observable<string> {
    return Observable.create((sub: Subscriber<string>): void => {
      const reader = new FileReader();
      // if success
      reader.onload = (ev: ProgressEvent): void => {
        sub.next((ev.target as any).result);
      };
      // if failed
      reader.onerror = (ev): void => {
        sub.error(ev);
      };
      reader.readAsBinaryString(file);
    });
  }

  private readFileAsUrl(file, callback) {
    const reader = new FileReader();
    reader.onload = callback;
    reader.readAsDataURL(file);
  }

  private checkDimension(file, callback) {
    const _URL = window.URL;
    const img = new Image();
    img.onload = function () {
      if (callback) {
        callback(img.width, img.height);
      }
    };
    img.src = _URL.createObjectURL(file);
  }

  configInit() {
    this.init = Object.assign(this.DFLT_CONFIG, this.init);
    if (this.init.textAlign !== undefined) {
      this.init.fileClass = this.init.fileClass + ' text-' + this.init.textAlign;
    }
    if (this.init.viewOnly) {
      this.init.inputClass = this.init.inputClass + '  view';
    }
  }

  upload(file) {
    return this.http.post<any>(
      this.config.url + `/api/references/documents/upload/custommultipart/${this.refDoc.dmBucket}`,
      JSON.stringify(file),
      this.geHeaders()
    );
  }

  download(id?: string) {
    const docMgtId = id ? id : this.docMgtId.length ? this.docMgtId[0] : this.docMgtId;

    var bucketName = this.dmBucket;

    if(this.refDoc && this.refDoc.dmBucket) {
      bucketName = this.refDoc.dmBucket;
    }

    return this.http
      .get<any>(this.config.url + `/api/references/documents/download/${bucketName}/` + docMgtId, this.geHeaders())
      .pipe(
        map(res => {
          return new File({
            filename: res.filename,
            size: res.length,
            contentType: res.contentType,
            content: res.content,
            docId: res.docid,
            docMgtId: res.id,
            dmBucket: res.dmBucket
          });
        })
      );
  }

  downloadContent() {
    return this.http.get<any>(
      this.config.url + `/api/references/documents/download/content/` + this.origDoc.docMgtId,
      this.geHeaders()
    );
  }

  geHeaders() {
    const httpheaders = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json; charset=utf-8',
        'Access-Control-Allow-Origin': '*',
        Authorization: 'Basic ' + btoa(this.config.client + ':' + this.config.skey),
        'X-Message-Id': uuid().toString()
      })
    };
    return httpheaders;
  }

}
