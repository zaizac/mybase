import { Component, EventEmitter, OnDestroy, OnInit } from '@angular/core';

import { BsDatepickerAbstractComponent } from '../../base/bs-datepicker-container';
import { BsDatepickerConfig } from '../../bs-datepicker.config';
import { DayViewModel } from '../../models';
import { BsDatepickerActions } from '../../reducer/bs-datepicker.actions';
import { BsDatepickerEffects } from '../../reducer/bs-datepicker.effects';
import { BsDatepickerStore } from '../../reducer/bs-datepicker.store';
import { PositioningService } from 'ngx-bootstrap/positioning';
import { Subscription } from 'rxjs';
import { BsCustomDates, RangeShowTimes } from './bs-custom-dates-view.component';


@Component({
  selector: 'bs-daterangepicker-container',
  providers: [BsDatepickerStore, BsDatepickerEffects],
  templateUrl: './bs-datepicker-view.html',
  host: {
    '(click)': '_stopPropagation($event)',
    style: 'position: absolute; display: block;',
    role: 'dialog',
    'aria-label': 'calendar'
  }
})
export class BsDaterangepickerContainerComponent extends BsDatepickerAbstractComponent
  implements OnInit, OnDestroy {
  set value(value: Date[]) {
    this._effects.setRangeValue(value);
  }

  valueChange = new EventEmitter<Date[]>();

  _rangeStack: Date[] = [];
  _subs: Subscription[] = [];
  constructor(
    _effects: BsDatepickerEffects,
    private _actions: BsDatepickerActions,
    private _config: BsDatepickerConfig,
    private _store: BsDatepickerStore,
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

    this.containerClass = this._config.containerClass;
    this.isOtherMonthsActive = this._config.selectFromOtherMonth;
    this._effects
      .init(this._store)
      // intial state options
      // todo: fix this, split configs
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
        .select(state => state.selectedRange)
        .subscribe(date => this.valueChange.emit(date))
    );
    this.customDates = this._config.customDates;
    this.rangeShowTime = this._config.rangeShowTime;
    this.dateButtons = this._config.dateButtons;
  }

  daySelectHandler(day: DayViewModel): void {
    const isDisabled = this.isOtherMonthsActive ? day.isDisabled : (day.isOtherMonth || day.isDisabled);

    if (isDisabled) {
      return;
    }

    // if only one date is already selected
    // and user clicks on previous date
    // start selection from new date
    // but if new date is after initial one
    // than finish selection
    if (this._rangeStack.length === 1) {
      this._rangeStack =
        day.date >= this._rangeStack[0]
          ? [this._rangeStack[0], day.date]
          : [day.date];
    }

    if (this._rangeStack.length === 0) {
      this._rangeStack = [day.date];
    }

    this._store.dispatch(this._actions.selectRange(this._rangeStack));

    if (this._rangeStack.length === 2) {
      this._rangeStack = [];
    }
  }

  onCustomDateSelect(bsCustomDate: BsCustomDates) {
    this.daySelectHandler({ isDisabled: false, isOtherMonth: false, date: <any>bsCustomDate.value, label: bsCustomDate.label });
  }

  ngOnDestroy(): void {
    for (const sub of this._subs) {
      sub.unsubscribe();
    }
    this._effects.destroy();
  }
}
