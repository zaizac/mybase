import { Component, ChangeDetectionStrategy, Input } from '@angular/core';

export interface BsCustomDates {
  label: string;
  value: Date | Date[];
}

export interface ViewDateValue{
  value: string | Date | Date[];
}

export interface Timevalue {
  hour?: string | number | any;
  minute?: string | number | any;
  seconds?: string | number | any;
  isPM?: boolean | any;
}

export interface ShowTimes{
  showTime: boolean;
}

export interface RangeShowTimes{
  rangeShowTime: boolean;
}

export interface DateButtons{
  dateButtons: boolean;
}

@Component({
  selector: 'bs-custom-date-view',
  template: `
    <div class="bs-datepicker-predefined-btns">
      <button *ngFor="let range of ranges">{{ range.label }}</button>
      <button *ngIf="isCustomRangeShown">Custom Range</button>
    </div>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BsCustomDatesViewComponent {
  @Input() isCustomRangeShown: true;
  @Input() ranges: BsCustomDates[];
}
