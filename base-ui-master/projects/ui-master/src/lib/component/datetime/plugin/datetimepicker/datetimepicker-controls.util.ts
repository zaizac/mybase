import { changeTime } from './datetimepicker.utils';
import {
  DateTimeChangeEvent,
  DateTimepickerComponentState,
  DateTimepickerControls
} from './datetimepicker.models';

export function canChangeValue(
  state: DateTimepickerComponentState,
  event?: DateTimeChangeEvent
): boolean {
  if (state.readonlyInput || state.disabled) {
    return false;
  }

  if (event) {
    if (event.source === 'wheel' && !state.mousewheel) {
      return false;
    }

    if (event.source === 'key' && !state.arrowkeys) {
      return false;
    }
  }

  return true;
}

export function canChangeHours(
  event: DateTimeChangeEvent,
  controls: DateTimepickerControls
): boolean {
  if (!event.step) {
    return false;
  }

  if (event.step > 0 && !controls.canIncrementHours) {
    return false;
  }

  if (event.step < 0 && !controls.canDecrementHours) {
    return false;
  }

  return true;
}

export function canChangeMinutes(
  event: DateTimeChangeEvent,
  controls: DateTimepickerControls
): boolean {
  if (!event.step) {
    return false;
  }
  if (event.step > 0 && !controls.canIncrementMinutes) {
    return false;
  }
  if (event.step < 0 && !controls.canDecrementMinutes) {
    return false;
  }

  return true;
}

export function canChangeSeconds(
  event: DateTimeChangeEvent,
  controls: DateTimepickerControls
): boolean {
  if (!event.step) {
    return false;
  }
  if (event.step > 0 && !controls.canIncrementSeconds) {
    return false;
  }
  if (event.step < 0 && !controls.canDecrementSeconds) {
    return false;
  }

  return true;
}

export function getControlsValue(
  state: DateTimepickerComponentState
): DateTimepickerComponentState {
  const {
    hourStep,
    minuteStep,
    secondsStep,
    readonlyInput,
    disabled,
    mousewheel,
    arrowkeys,
    showSpinners,
    showMeridian,
    showSeconds,
    meridians,
    min,
    max
  } = state;

  return {
    hourStep,
    minuteStep,
    secondsStep,
    readonlyInput,
    disabled,
    mousewheel,
    arrowkeys,
    showSpinners,
    showMeridian,
    showSeconds,
    meridians,
    min,
    max
  };
}

export function datetimepickerControls(
  value: Date,
  state: DateTimepickerComponentState
): DateTimepickerControls {
  const hoursPerDayHalf = 12;
  const { min, max, hourStep, minuteStep, secondsStep, showSeconds } = state;
  const res: DateTimepickerControls = {
    canIncrementHours: true,
    canIncrementMinutes: true,
    canIncrementSeconds: true,

    canDecrementHours: true,
    canDecrementMinutes: true,
    canDecrementSeconds: true,

    canToggleMeridian: true
  };

  if (!value) {
    return res;
  }

  // compare dates
  if (max) {
    const _newHour = changeTime(value, { hour: hourStep });
    res.canIncrementHours = max > _newHour;

    if (!res.canIncrementHours) {
      const _newMinutes = changeTime(value, { minute: minuteStep });
      res.canIncrementMinutes = showSeconds
        ? max > _newMinutes
        : max >= _newMinutes;
    }

    if (!res.canIncrementMinutes) {
      const _newSeconds = changeTime(value, { seconds: secondsStep });
      res.canIncrementSeconds = max >= _newSeconds;
    }

    if (value.getHours() < hoursPerDayHalf) {
      res.canToggleMeridian = changeTime(value, { hour: hoursPerDayHalf }) < max;
    }
  }

  if (min) {
    const _newHour = changeTime(value, { hour: -hourStep });
    res.canDecrementHours = min < _newHour;

    if (!res.canDecrementHours) {
      const _newMinutes = changeTime(value, { minute: -minuteStep });
      res.canDecrementMinutes = showSeconds
        ? min < _newMinutes
        : min <= _newMinutes;
    }

    if (!res.canDecrementMinutes) {
      const _newSeconds = changeTime(value, { seconds: -secondsStep });
      res.canDecrementSeconds = min <= _newSeconds;
    }

    if (value.getHours() >= hoursPerDayHalf) {
      res.canToggleMeridian = changeTime(value, { hour: -hoursPerDayHalf }) > min;
    }
  }

  return res;
}
