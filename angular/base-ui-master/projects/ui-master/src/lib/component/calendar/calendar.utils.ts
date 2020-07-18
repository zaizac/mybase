// Adapt from Angular-calendar to cater for Calendar Year View since this class not expose by Angular-calendar

import {
  CalendarEvent,
  DayViewEvent,
  DayViewHour,
  DayViewHourSegment,
  getEventsInPeriod,
  validateEvents as validateEventsWithoutLog,
  ViewPeriod,
  WeekDay,
  WeekViewEvent
} from 'calendar-utils';
import {
  addMilliseconds,
  addMonths,
  differenceInMilliseconds,
  endOfMonth,
  endOfYear,
  format,
  isAfter,
  isBefore,
  isSameDay,
  setYear,
  startOfMonth,
  startOfYear
} from 'date-fns';

export const validateEvents = (events: CalendarEvent[]) => {
  const warn = (...args) => console.warn('angular-calendar', ...args);
  return validateEventsWithoutLog(events, warn);
};

export function isInside(outer: ClientRect, inner: ClientRect): boolean {
  return (
    Math.floor(outer.left) <= Math.ceil(inner.left) &&
    Math.floor(inner.left) <= Math.ceil(outer.right) &&
    Math.floor(outer.left) <= Math.ceil(inner.right) &&
    Math.floor(inner.right) <= Math.ceil(outer.right) &&
    Math.floor(outer.top) <= Math.ceil(inner.top) &&
    Math.floor(inner.top) <= Math.ceil(outer.bottom) &&
    Math.floor(outer.top) <= Math.ceil(inner.bottom) &&
    Math.floor(inner.bottom) <= Math.ceil(outer.bottom)
  );
}

export function roundToNearest(amount: number, precision: number) {
  return Math.round(amount / precision) * precision;
}

export const trackByEventId = (index: number, event: CalendarEvent) => (event.id ? event.id : event);

export const trackByWeekDayHeaderDate = (index: number, day: WeekDay) => day.date.toISOString();

export const trackByHourSegment = (index: number, segment: DayViewHourSegment) => segment.date.toISOString();

export const trackByHour = (index: number, hour: DayViewHour) => hour.segments[0].date.toISOString();

export const trackByDayOrWeekEvent = (index: number, weekEvent: WeekViewEvent | DayViewEvent) =>
  weekEvent.event.id ? weekEvent.event.id : weekEvent.event;

const MINUTES_IN_HOUR = 60;

export function getMinutesMoved(
  movedY: number,
  hourSegments: number,
  hourSegmentHeight: number,
  eventSnapSize: number
): number {
  const draggedInPixelsSnapSize = roundToNearest(movedY, eventSnapSize || hourSegmentHeight);
  const pixelAmountInMinutes = MINUTES_IN_HOUR / (hourSegments * hourSegmentHeight);
  return draggedInPixelsSnapSize * pixelAmountInMinutes;
}

export function getMinimumEventHeightInMinutes(hourSegments: number, hourSegmentHeight: number) {
  return (MINUTES_IN_HOUR / (hourSegments * hourSegmentHeight)) * hourSegmentHeight;
}

export function isDraggedWithinPeriod(newStart: Date, newEnd: Date, period: ViewPeriod): boolean {
  const end = newEnd || newStart;
  return (period.start <= newStart && newStart <= period.end) || (period.start <= end && end <= period.end);
}

export function shouldFireDroppedEvent(
  dropEvent: { dropData?: { event?: CalendarEvent; calendarId?: symbol } },
  date: Date,
  allDay: boolean,
  calendarId: symbol
) {
  return (
    dropEvent.dropData &&
    dropEvent.dropData.event &&
    (dropEvent.dropData.calendarId !== calendarId ||
      (dropEvent.dropData.event.allDay && !allDay) ||
      (!dropEvent.dropData.event.allDay && allDay))
  );
}

export function isWithinThreshold({ x, y }: { x: number; y: number }) {
  const DRAG_THRESHOLD = 1;
  return Math.abs(x) > DRAG_THRESHOLD || Math.abs(y) > DRAG_THRESHOLD;
}

export function getYearView(events: CalendarEvent[], viewDate: Date, cellModifier?) {
  const view = [];
  const eventsInPeriod = getEventsInYearPeriod(viewDate, events);
  let month = startOfYear(viewDate);
  let count = 0;
  while (count < 12) {
    const periodStart = new Date(month.getTime());
    const periodEnd = endOfMonth(new Date(periodStart.getTime()));
    const periodEvents = getEventsInPeriod({ events: eventsInPeriod, periodStart, periodEnd });
    const cell = {
      label: format(periodStart, 'MMMM'),
      isThisMonth: isSameDay(periodStart, startOfMonth(new Date())),
      events: periodEvents,
      date: periodStart,
      badgeTotal: periodEvents.length
    };

    view.push(cell);
    month = addMonths(month, 1);
    count++;
  }

  return view;
}

export function filterEventsInYearPeriod(events: CalendarEvent[], startPeriod: Date, endPeriod: Date) {
  return events.filter(event => eventIsInPeriod(event, startPeriod, endPeriod));
}

export function getEventsInYearPeriod(calendarDate: Date, events: CalendarEvent[]) {
  const startPeriod = startOfYear(calendarDate);
  const endPeriod = endOfYear(calendarDate);
  return filterEventsInYearPeriod(events, startPeriod, endPeriod);
}

export function eventIsInPeriod(event: CalendarEvent, periodStart: Date, periodEnd: Date) {
  const eventPeriod = getRecurringEventYearPeriod({ start: event.start, end: event.end || event.start }, periodStart);
  const eventStart = eventPeriod.start;
  const eventEnd = eventPeriod.end;

  return (
    (isAfter(eventStart, periodStart) && isBefore(eventStart, periodEnd)) ||
    (isAfter(eventEnd, periodStart) && isBefore(eventEnd, periodEnd)) ||
    (isBefore(eventStart, periodStart) && isAfter(eventEnd, periodEnd)) ||
    isSameDay(eventStart, periodStart) ||
    isSameDay(eventEnd, periodEnd)
  );
}

export function getRecurringEventYearPeriod(eventPeriod: { start: Date; end: Date }, periodStart: Date) {
  let eventStart = eventPeriod.start;
  let eventEnd = eventPeriod.end;

  eventStart = setYear(eventStart, periodStart.getFullYear());
  eventEnd = adjustEndDateFromStartDiff(eventPeriod.start, eventStart, eventEnd);

  return { start: eventStart, end: eventEnd };
}

export function adjustEndDateFromStartDiff(oldStart: Date, newStart: Date, oldEnd: Date) {
  if (!oldEnd) {
    return oldEnd;
  }
  const diffInSeconds = differenceInMilliseconds(newStart, oldStart);
  return addMilliseconds(oldEnd, diffInSeconds);
}
