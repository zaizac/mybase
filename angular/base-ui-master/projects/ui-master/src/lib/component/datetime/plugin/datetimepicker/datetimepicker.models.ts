export interface Time {
    hour?: string | number;
    minute?: string | number;
    seconds?: string | number;
    isPM?: boolean;
  }
  
  export interface DateTimepickerControls {
    canIncrementHours: boolean;
    canIncrementMinutes: boolean;
    canIncrementSeconds: boolean;
  
    canDecrementHours: boolean;
    canDecrementMinutes: boolean;
    canDecrementSeconds: boolean;
  
    canToggleMeridian: boolean;
  }
  
  export interface DateTimepickerComponentState {
    min: Date;
    max: Date;
  
    hourStep: number;
    minuteStep: number;
    secondsStep: number;
  
    readonlyInput: boolean;
    disabled: boolean;
  
    mousewheel: boolean;
    arrowkeys: boolean;
  
    showSpinners: boolean;
    showMeridian: boolean;
    showSeconds: boolean;
  
    meridians: string[];
  }
  
  export type DateTimeChangeSource = 'wheel' | 'key' | '';
  
  export interface DateTimeChangeEvent {
    step: number;
    source: DateTimeChangeSource;
  }
  