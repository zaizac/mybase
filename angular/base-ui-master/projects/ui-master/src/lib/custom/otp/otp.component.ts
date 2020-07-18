import { Component, OnInit, Input, ViewChild, TemplateRef, Output, EventEmitter } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { ModalService } from '../../ui-master.service';
import { v4 as uuid } from 'uuid';
import { OtpTemplateService } from '../otp-template/otp-template.service';

@Component({
  selector: 'ui-otp',
  templateUrl: './otp.component.html',
  styleUrls: ['./otp.component.scss']
})

export class OtpComponent implements OnInit {

  @Input() icon: string;
  @Input() buttonText: string;
  @Input() type: string;
  @Input() buttonType: string;
  @Input() value: any;
  @Input() redirectURL: string;
  @Input() formGroup: FormGroup
  @ViewChild('content') content: TemplateRef<any>;
  @Output() validate = new EventEmitter<Object>();
  @Input() disabled: boolean;

  @Input() otpService: any;
  otpForm: FormGroup;

  otpPopupAction: boolean = false;
  otpUniqueId: any;
  otpErrorMsg: string;
  otpSuccessMsg: string;

  constructor(public modalSvc: ModalService, private formBuilder: FormBuilder, private otp: OtpTemplateService) {
    otp.setOtpEmailComponent(this);
    /** 
     * otpUniqueId - generate once during initialised 
     * especially if two forms in same page using otp may generate id twice during onInit.
     * The later will replace the id and verification might failed in the other.
     * So avoid declare 'otpUniqueId' in ngOnInit
     */
    this.otpUniqueId = uuid();
  }

  ngOnInit() {
    this.otpForm = this.formBuilder.group({
      otp: ['', Validators.required]
    });
  }

  onClick() {
    if (this.value && this.formGroup.valid) {
      this.modalSvc.openDefault(this.content);
    }
  }

  emailVerified() {
    this.validate.emit();
  }

}
