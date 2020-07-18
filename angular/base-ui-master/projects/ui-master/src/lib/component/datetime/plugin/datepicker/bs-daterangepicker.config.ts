import { Injectable } from '@angular/core';
import { BsDatepickerConfig } from './bs-datepicker.config';
import { BsDatepickerViewMode } from './models';

@Injectable()
export class BsDaterangepickerConfig extends BsDatepickerConfig {
  // DatepickerRenderOptions
  displayMonths = 2;
}
