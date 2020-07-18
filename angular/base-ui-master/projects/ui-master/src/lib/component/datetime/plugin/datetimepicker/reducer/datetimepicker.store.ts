import { Injectable } from '@angular/core';
import {
    datetimepickerReducer,
  DateTimepickerState,
  initialState
} from './datetimepicker.reducer';
import { BehaviorSubject } from 'rxjs';

import { Action, MiniStore, MiniState } from 'ngx-bootstrap/mini-ngrx';

@Injectable()
export class DatetimepickerStore extends MiniStore<DateTimepickerState> {
  constructor() {
    const _dispatcher = new BehaviorSubject<Action>({
      type: '[mini-ngrx] dispatcher init'
    });
    const state = new MiniState<DateTimepickerState>(
      initialState,
      _dispatcher,
      datetimepickerReducer
    );
    super(_dispatcher, datetimepickerReducer, state);
  }
}
