import { Component, OnInit, ViewEncapsulation, Input } from '@angular/core';
import { NgbModalConfig, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'ui-modal',
  templateUrl: './modal.component.html',
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent implements OnInit {

  @Input() title: string;
  @Input() message: string;
  @Input() btnNoText: string;
  @Input() btnOkText: string;
  @Input() type: string;
  @Input() content: any;
  @Input() modal: NgbActiveModal;
  
  constructor(
    config: NgbModalConfig, 
    public activeModal: NgbActiveModal, 
    public sanitizer: DomSanitizer) {
    config.backdrop = 'static';
    config.keyboard = false;
   }

  public decline() {
    this.activeModal.close(false);
  }

  public accept() {
    this.activeModal.close(true);
  }

  public dismiss() {
    this.activeModal.dismiss();
  }

  ngOnInit() {
    if(this.modal !== undefined){
      this.activeModal = this.modal;
    }
  }
}
