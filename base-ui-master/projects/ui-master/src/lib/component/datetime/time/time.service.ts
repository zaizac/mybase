import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TimeService {

  private hour = new Subject<any>();
  hour$ = this.hour.asObservable();
  private minute = new Subject<any>();
  minute$ = this.minute.asObservable();
  private second = new Subject<any>();
  second$ = this.second.asObservable();
  private pm = new Subject<any>();
  pm$ = this.pm.asObservable();

  constructor() { }

  publishHourValue(hours: any) {
    this.hour.next(hours);
  }

  publishMinuteValue(minutes: any){
    this.minute.next(minutes);
  }

  publishSecondValue(seconds: any):void{
    this.second.next(seconds);
  }

  publishPMValue(pms: any){
    this.pm.next(pms);
  }
}
 
