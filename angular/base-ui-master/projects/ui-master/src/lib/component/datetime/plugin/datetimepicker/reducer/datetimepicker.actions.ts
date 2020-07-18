import { Injectable } from '@angular/core';
import { Action } from 'ngx-bootstrap/mini-ngrx';
import {
  DateTimeChangeEvent,
  DateTimepickerComponentState,
  Time
} from '../datetimepicker.models';

@Injectable()
export class DateTimepickerActions {
  static readonly WRITE_VALUE = '[datetimepicker] write value from ng model';
  static readonly CHANGE_HOURS = '[datetimepicker] change hours';
  static readonly CHANGE_MINUTES = '[datetimepicker] change minutes';
  static readonly CHANGE_SECONDS = '[datetimepicker] change seconds';
  static readonly SET_TIME_UNIT = '[datetimepicker] set time unit';
  static readonly UPDATE_CONTROLS = '[datetimepicker] update controls';

  writeValue(value: Date | string) {
    return {
      type: DateTimepickerActions.WRITE_VALUE,
      payload: value
    };
  }

  changeHours(event: DateTimeChangeEvent) {
    return {
      type: DateTimepickerActions.CHANGE_HOURS,
      payload: event
    };
  }

  changeMinutes(event: DateTimeChangeEvent) {
    return {
      type: DateTimepickerActions.CHANGE_MINUTES,
      payload: event
    };
  }

  changeSeconds(event: DateTimeChangeEvent): Action {
    return {
      type: DateTimepickerActions.CHANGE_SECONDS,
      payload: event
    };
  }

  setTime(value: Time): Action {
    return {
      type: DateTimepickerActions.SET_TIME_UNIT,
      payload: value
    };
  }

  updateControls(value: DateTimepickerComponentState): Action {
    return {
      type: DateTimepickerActions.UPDATE_CONTROLS,
      payload: value
    };
  }
}
