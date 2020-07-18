import { CalendarEvent } from 'angular-calendar';

export class CalendarEventHandler {
  event?: CalendarEvent<any>;
  refresh?: () => void;
  constructor() {}
}

export const CalendarColour: any = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3'
  },
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF'
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA'
  }
};

export class EventConfig {
  /**
   * Enable add day event checkbox when add/edit event
   */
  enableAllDay?: boolean;
  /**
   * Enable draggable event checkbox when add/edit event
   */
  enableDraggable?: boolean;
  /**
   * Enable sizeble event checkbox when add/edit event
   */
  enableSizable?: boolean;
  /**
   * Title for add event modal. Default is 'Add'
   */
  addEventTitle?: string;
  /**
   * Title for edit event modal Default is 'Edit'
   */
  editEventTitle?: string;
  /**
   * Date type
   */
  dateType?: string;
  /**
   * Minimum Date to select
   */
  minDate?: Date;
  /**
   * Maximum Date to select
   */
  maxDate?: Date;

  constructor(
    enableAllDay?: boolean,
    enableDraggable?: boolean,
    enableSizable?: boolean,
    addEventTitle?: string,
    editEventTitle?: string,
    dateType?: string,
    minDate?: Date,
    maxDate?: Date
  ) {
    this.enableAllDay = enableAllDay || false;
    this.enableDraggable = enableDraggable || false;
    this.enableSizable = enableSizable || false;
    this.addEventTitle = addEventTitle || 'Add';
    this.editEventTitle = editEventTitle || 'Edit';
    this.dateType = dateType || 'range';
    this.minDate = minDate;
    this.maxDate = maxDate;
  }

}
