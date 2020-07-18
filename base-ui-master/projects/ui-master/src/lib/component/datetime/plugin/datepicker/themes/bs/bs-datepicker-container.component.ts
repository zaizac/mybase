import { Component, EventEmitter, OnDestroy, OnInit, ViewChild, forwardRef, ContentChild, ViewChildren } from '@angular/core';

import { BsDatepickerAbstractComponent } from '../../base/bs-datepicker-container';
import { BsDatepickerConfig } from '../../bs-datepicker.config';
import { DayViewModel } from '../../models';
import { BsDatepickerActions } from '../../reducer/bs-datepicker.actions';
import { BsDatepickerEffects } from '../../reducer/bs-datepicker.effects';
import { BsDatepickerStore } from '../../reducer/bs-datepicker.store';
import { PositioningService } from 'ngx-bootstrap/positioning';

import { Subscription } from 'rxjs';
import { BsCustomDates, ViewDateValue, ShowTimes, Timevalue } from './bs-custom-dates-view.component';


@Component({
  selector: 'bs-datepicker-container',
  providers: [BsDatepickerStore, BsDatepickerEffects],
  templateUrl: './bs-datepicker-view.html',
  host: {
    '(click)': '_stopPropagation($event)',
    style: 'position: absolute; display: block;',
    role: 'dialog',
    'aria-label': 'calendar'
  }
})
export class BsDatepickerContainerComponent extends BsDatepickerAbstractComponent
  implements OnInit, OnDestroy {

    time123 : Timevalue;
  set value(value: Date) {    
    this._effects.setValue(value);
  }
  valueChange: EventEmitter<Date> = new EventEmitter<Date>();

  _subs: Subscription[] = [];
  constructor(
    private _config: BsDatepickerConfig,
    private _store: BsDatepickerStore,
    private _actions: BsDatepickerActions,
    _effects: BsDatepickerEffects,
    private _positionService: PositioningService
  ) {
    super();
    this._effects = _effects;
  }

  ngOnInit(): void {
    this._positionService.setOptions({
      modifiers: {
        flip: {
          enabled: this._config.adaptivePosition
        }
      }
    });

    this.isOtherMonthsActive = this._config.selectFromOtherMonth;
    this.containerClass = this._config.containerClass;
    this._effects
      .init(this._store)
      // intial state options
      .setOptions(this._config)
      // data binding view --> model
      .setBindings(this)
      // set event handlers
      .setEventHandlers(this)
      .registerDatepickerSideEffects();

    // todo: move it somewhere else
    // on selected date change
    this._subs.push(
      this._store
        /* tslint:disable-next-line: no-any */
        .select((state: any) => state.selectedDate)
        /* tslint:disable-next-line: no-any */
        .subscribe((date: any) => this.valueChange.emit(date))
    );
    this.customDates = this._config.customDates;
    this.viewDate = this._config.viewDate;
    this.showTime = this._config.showTime;
    this.dateButtons = this._config.dateButtons;
    this.time = this._config.time;
    
  }

  daySelectHandler(day: DayViewModel): void {
    const isDisabled = this.isOtherMonthsActive ? day.isDisabled : (day.isOtherMonth || day.isDisabled);

    if (isDisabled) {
      return;
    }

    this._store.dispatch(this._actions.select(day.date));
  }

  onCustomDateSelect(bsCustomDate: BsCustomDates) {
    this._store.dispatch(this._actions.select(<any>bsCustomDate.value));
  }

  onViewDateValue(viewDatevalue:ViewDateValue){
    this._store.dispatch(this._actions.maxDate(<any>viewDatevalue.value));
  }

  onViewTime(timeValue: Timevalue){
    this._store.dispatch(this._actions.select(<any> timeValue.hour && timeValue.minute && timeValue.seconds && timeValue.isPM))
  }

  // clear(){
  //   this.valueChange = null;
  // }

  ngOnDestroy(): void {
    for (const sub of this._subs) {
      sub.unsubscribe();
    }
    this._effects.destroy();
  }
}
