import { Component, OnInit } from '@angular/core';
import { EventConfig } from 'projects/ui-master/src/lib/component/calendar/calendar.config';
import { MOCKCALENDARDATA } from '../datatable/mockData';
@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent implements OnInit {
  mockCalendarData = [];
  public eventConfig: EventConfig;
  constructor() {}

  ngOnInit() {
    MOCKCALENDARDATA.subscribe(data => {
      this.mockCalendarData = data;
    });
    this.eventConfig = {
      enableAllDay: true,
      addEventTitle: 'Add new calendar event',
      minDate: new Date('2020-01-01'),
      maxDate: new Date()
    };
  }

  deleteEvent(calendarEvent) {
    this.mockCalendarData.splice(
      this.mockCalendarData.findIndex(data => data.id === calendarEvent.event.id),
      1
    );
    calendarEvent.refresh();
  }

  timeChangeEvent(calendarEvent) {
    calendarEvent.refresh();
  }

  editEvent(calendarEvent) {
    this.mockCalendarData[this.mockCalendarData.findIndex(data => data.id === calendarEvent.event.id)] =
      calendarEvent.event;
    calendarEvent.refresh();
  }

  addEvent(calendarEvent) {
    this.mockCalendarData.push(calendarEvent.event);
    calendarEvent.refresh();
  }
}
