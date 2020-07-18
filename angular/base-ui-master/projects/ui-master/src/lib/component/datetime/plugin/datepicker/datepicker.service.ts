import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DatepickerService {

  private valueDate = new Subject<any>();
  valueDate$ = this.valueDate.asObservable();

  constructor() { }

  publishDateValue(valueDate: any) {
    this.valueDate.next(valueDate);
  }
}
