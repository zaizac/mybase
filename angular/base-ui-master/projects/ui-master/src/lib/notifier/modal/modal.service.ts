import { Injectable, TemplateRef } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ModalComponent } from './modal.component';

enum ModalType {
  Success = 'success',
  Error = 'error',
  Warn = 'warn',
  Info = 'info',
  Danger = 'danger',
  Confirm = 'confirm'
};

enum Btn {
  Ok = 'OK',
  No = 'No',
  Yes = 'Yes'
};

@Injectable({
  providedIn: 'root'
})
export class ModalService {

  constructor(private modal: NgbModal) { }

  public confirm(message: string): Promise<boolean> {
    return this.confirmation(ModalType.Confirm.toUpperCase(), message, Btn.Ok, Btn.No);
  }

  public confirmCustom(
    title: string,
    message: string,
    btnOkText: string,
    btnNoText: string,
  ): Promise<boolean> {
    return this.confirmation(title, message, btnOkText, btnNoText);
  }

  public success(message: string): Promise<any> {
    return this.open(ModalType.Success, message);
  }

  public info(message: string): Promise<any> {
    return this.open(ModalType.Info, message);
  }

  public error(message: string): Promise<any> {
    return this.open(ModalType.Error, message);
  }

  public warn(message: string): Promise<any> {
    return this.open(ModalType.Warn, message);
  }

  public danger(message: string): Promise<any> {
    return this.open(ModalType.Danger, message);
  }

  public file(title: string, content: any, type: string): Promise<any> {
    const modalRef = this.modal.open(ModalComponent, { windowClass: ModalType.Info });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.content = content;
    modalRef.componentInstance.type = type;
    return modalRef.result;
  }

  private confirmation(
    title: string,
    message: string,
    btnOkText: string,
    btnNoText: string,
  ): Promise<boolean> {
    const modalRef = this.modal.open(ModalComponent, { windowClass: ModalType.Confirm });
    modalRef.componentInstance.title = title;
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.btnOkText = btnOkText;
    modalRef.componentInstance.btnNoText = btnNoText;
    modalRef.componentInstance.type = ModalType.Confirm;
    return modalRef.result;
  }

  private open(type: any, message: string): Promise<any> {
    const modalRef = this.modal.open(ModalComponent, { windowClass: type });
    modalRef.componentInstance.title = type.toUpperCase();
    modalRef.componentInstance.message = message;
    modalRef.componentInstance.type = type;
    return modalRef.result;
  }

  public openDefault(template: TemplateRef<any>) {
    const modalRef = this.modal.open(template, { windowClass: 'customPopup' });
    // modalRef.componentInstance.notifyParent.subscribe((result)=>{
    //   console.log(result);   
    //   modalRef.dismiss();
    // })
  }

  public confirmCstmTempRef(template: TemplateRef<any>): Promise<any> {
    const modalRef = this.modal.open(template, { windowClass: 'customPopup' });
    return modalRef.result;
  }

  public dismiss() {
    this.modal.dismissAll();
  }

}
