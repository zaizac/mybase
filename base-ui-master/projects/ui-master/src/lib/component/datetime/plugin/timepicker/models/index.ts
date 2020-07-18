import { InjectionToken, Type } from '@angular/core';
import { ControlValueAccessor } from '@angular/forms';

export interface ControlValueAccessorModel {
  provide: InjectionToken<ControlValueAccessor>;
  // tslint:disable-next-line:no-any
  useExisting: Type<any>;
  multi: boolean;
}

// export interface TimeFrom{
//   hour : number;
//   minute : number;
//   second : number;
//   pm : string;
// }

export class TimeFrom {
    hour: any;
    minute: any;
    second: any; 
    pm: any; 
}

// export interface TimeTo{
//   hour : number;
//   minute : number;
//   second : number;
//   pm : string;
// }

export class TimeTo {
  hour: number;
  minute: number;
  second: number; 
  pm: string;
}
