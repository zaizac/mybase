import { Action } from 'ngx-bootstrap/mini-ngrx';
import {
  canChangeHours,
  canChangeMinutes,
  canChangeSeconds,
  canChangeValue,
  datetimepickerControls
} from '../datetimepicker-controls.util';
import { DatetimepickerConfig } from '../datetimepicker.config';
import {
  DateTimepickerComponentState,
  DateTimepickerControls
} from '../datetimepicker.models';
import { changeTime, setTime, isValidLimit } from '../datetimepicker.utils';
import { DateTimepickerActions } from './datetimepicker.actions';

export class DateTimepickerState {
  value: Date;
  config: DateTimepickerComponentState;
  controls: DateTimepickerControls;
}

export const initialState: DateTimepickerState = {
  value: null,
  config: new DatetimepickerConfig(),
  controls: {
    canIncrementHours: true,
    canIncrementMinutes: true,
    canIncrementSeconds: true,

    canDecrementHours: true,
    canDecrementMinutes: true,
    canDecrementSeconds: true,

    canToggleMeridian: true
  }
};

// tslint:disable-next-line:cyclomatic-complexity
export function datetimepickerReducer(state = initialState, action: Action) {
  switch (action.type) {
    case DateTimepickerActions.WRITE_VALUE: {
      return Object.assign({}, state, { value: action.payload });
    }

    case DateTimepickerActions.CHANGE_HOURS: {
      if (
        !canChangeValue(state.config, action.payload) ||
        !canChangeHours(action.payload, state.controls)
      ) {
        return state;
      }

      const _newTime = changeTime(state.value, { hour: action.payload.step });

      if ((state.config.max || state.config.min) && !isValidLimit(state.config, _newTime)) {
          return state;
      }

      return Object.assign({}, state, { value: _newTime });
    }

    case DateTimepickerActions.CHANGE_MINUTES: {
      if (
        !canChangeValue(state.config, action.payload) ||
        !canChangeMinutes(action.payload, state.controls)
      ) {
        return state;
      }

      const _newTime = changeTime(state.value, { minute: action.payload.step });

      if ((state.config.max || state.config.min) && !isValidLimit(state.config, _newTime)) {
        return state;
      }

      return Object.assign({}, state, { value: _newTime });
    }

    case DateTimepickerActions.CHANGE_SECONDS: {
      if (
        !canChangeValue(state.config, action.payload) ||
        !canChangeSeconds(action.payload, state.controls)
      ) {
        return state;
      }

      const _newTime = changeTime(state.value, {
        seconds: action.payload.step
      });

      if ((state.config.max || state.config.min) && !isValidLimit(state.config, _newTime)) {
        return state;
      }

      return Object.assign({}, state, { value: _newTime });
    }

    case DateTimepickerActions.SET_TIME_UNIT: {
      if (!canChangeValue(state.config)) {
        return state;
      }

      const _newTime = setTime(state.value, action.payload);

      return Object.assign({}, state, { value: _newTime });
    }

    case DateTimepickerActions.UPDATE_CONTROLS: {
      const _newControlsState = datetimepickerControls(state.value, action.payload);
      const _newState: DateTimepickerState = {
        value: state.value,
        config: action.payload,
        controls: _newControlsState
      };

      if (state.config.showMeridian !== _newState.config.showMeridian) {
        if (state.value) {
          _newState.value = new Date(state.value);
        }
      }

      return Object.assign({}, state, _newState);
    }

    default:
      return state;
  }
}
