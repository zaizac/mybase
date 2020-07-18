import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OtpTemplateService {
  private otpEmail: any
  constructor() { }

  public setOtpEmailComponent(value: any) {
    this.otpEmail = value
  }

  public updateVerifyEmail(): void {
    this.otpEmail.emailVerified();
  }

  public updateOtpAction(val: boolean) {
    if (val) {
      this.otpEmail.otpPopupAction = true;
    } else {
      this.otpEmail.otpPopupAction = false;
    }
  }

  private _mySubj: Subject<{ [k: string]: any }>
    = new Subject<{ [k: string]: any }>();

  public data: Observable<{ [k: string]: any }> = this._mySubj.asObservable();

  private _id: any = 0;
  private _RndNr: number = 180;


  startSth(): void {
    if (!this._id) {
      this._id = setInterval(
        () => {
          this._RndNr = Math.floor(this._RndNr - 1);
          this._mySubj.next({ val: this._RndNr })
        }, 1000);

    }
  }

  stopSth(): void {
    clearInterval(this._id);
    this._id = 0;
    this._RndNr = 180;
  }
}
