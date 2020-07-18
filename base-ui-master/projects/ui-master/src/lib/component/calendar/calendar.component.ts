import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  DoCheck,
  EventEmitter,
  Input,
  IterableDiffers,
  OnInit,
  Output,
  TemplateRef,
  ViewChild
} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CalendarEvent, CalendarEventAction, CalendarEventTimesChangedEvent } from 'angular-calendar';
import { addYears, differenceInCalendarDays, isSameDay, isSameMonth, subYears } from 'date-fns';
import { Subject } from 'rxjs';
import { ModalService } from '../../ui-master.service';
import { CalendarColour, CalendarEventHandler, EventConfig } from './calendar.config';

@Component({
  selector: 'ui-calendar',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent implements OnInit, DoCheck {
  @ViewChild('eventContent') eventContent: TemplateRef<any>;
  @Input() public activeDayIsOpen = false;
  @Input() public events: CalendarEvent[] = [];
  @Input() public customYearTemplate: TemplateRef<any>;
  @Input() public customCellTemplate: TemplateRef<any>;
  @Input() public customOpenDayTemplate: TemplateRef<any>;
  @Input() public allowPastEvent = false;
  @Input() public eventColour: {
    past: { primary: string; secondary: string };
    today: { primary: string; secondary: string };
    future: { primary: string; secondary: string };
  } = {
    past: CalendarColour.red,
    today: CalendarColour.yellow,
    future: CalendarColour.blue
  };

  @Input() public eventConfig: EventConfig = new EventConfig();

  @Output() deleteEvent = new EventEmitter<CalendarEventHandler>();
  @Output() timeChangeEvent = new EventEmitter<CalendarEventHandler>();
  @Output() editEvent = new EventEmitter<CalendarEventHandler>();
  @Output() addEvent = new EventEmitter<CalendarEventHandler>();
  submitted = false;
  isSingleClick = true;
  timer: any;
  iterableDiffer: any;

  eventDetailForm: FormGroup;
  refresh: Subject<any> = new Subject();
  view = 'month';
  viewDate: Date = new Date();
  today: Date = new Date();
  modalData: {
    action: string;
    event: CalendarEvent;
  };

  actions: CalendarEventAction[] = [
    {
      label: '<i class="fa fa-fw fa-pencil text-white"></i>',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.handleEvent('Edited', event);
      }
    },
    {
      label: '<i class="fa fa-fw fa-times  text-white"></i>',
      onClick: ({ event }: { event: CalendarEvent }): void => {
        this.handleEvent('Deleted', event);
      }
    }
  ];

  constructor(
    private modal: NgbModal,
    public modalSvc: ModalService,
    private formBuilder: FormBuilder,
    private changeDetector: ChangeDetectorRef,
    private iterableDiffers: IterableDiffers
  ) {
    this.iterableDiffer = this.iterableDiffers.find([]).create(null);
    this.eventDetailForm = this.formBuilder.group({
      id: null,
      title: null,
      eventDate: null,
      allDay: null,
      draggable: null,
      resizable: this.formBuilder.group({
        beforeStart: null,
        afterEnd: null
      })
    });
  }

  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    this.isSingleClick = true;
    this.timer = setTimeout(() => {
      if (this.isSingleClick) {
        if (isSameMonth(date, this.viewDate)) {
          if ((isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) || events.length === 0) {
            this.activeDayIsOpen = false;
          } else {
            this.activeDayIsOpen = true;
            this.viewDate = date;
          }
        }
      }
      this.changeDetector.markForCheck();
    }, 200);
  }

  monthClicked(month: any): void {
    if ((isSameDay(this.viewDate, month.date) && this.activeDayIsOpen === true) || month.events.length === 0) {
      this.activeDayIsOpen = false;
    } else {
      this.activeDayIsOpen = true;
      this.viewDate = month.date;
    }
  }

  eventTimesChanged({ event, newStart, newEnd }: CalendarEventTimesChangedEvent): void {
    if (!this.canHaveAction(newStart) && !this.allowPastEvent) {
      this.modalSvc.error('Please set future date for event');
      return;
    }

    this.modalSvc.confirm('Are you sure you want to change event time?').then(confirm => {
      if (confirm) {
        event.start = newStart;
        event.end = newEnd;
        this.timeChangeEvent.emit({
          event,
          refresh: () => {
            this.refresh.next();
          }
        });
        this.setEventActionsandColour([event]);
      }
    });
  }

  handleEvent(action: string, event: CalendarEvent): void {
    this.modalData = { event, action };
    if (action === 'Deleted') {
      this.modalSvc.confirm('Are you sure you want to delete this event?').then(confirm => {
        if (confirm) {
          this.deleteEvent.emit({
            event,
            refresh: () => {
              this.refresh.next();
            }
          });
        }
      });
    } else if (action === 'Edited') {
      this.eventDetailForm.reset();
      const eventDates = [];
      if (event.start) {
        eventDates.push(event.start);
      }
      if (event.end) {
        eventDates.push(event.end);
      }
      this.eventDetailForm.patchValue({
        id: event.hasOwnProperty('id') ? event.id : null,
        title: event.hasOwnProperty('title') ? event.title : null,
        eventDate: eventDates,
        end: event.hasOwnProperty('end') ? event.end : null,
        allDay: event.hasOwnProperty('allDay') ? event.allDay : null,
        draggable: event.hasOwnProperty('draggable') ? event.draggable : null
      });
      if (event.resizable) {
        if (!event.resizable.hasOwnProperty('beforeStart')) {
          this.eventDetailForm.patchValue({ resizable: { beforeStart: null, afterEnd: event.resizable.afterEnd } });
        } else if (!event.resizable.hasOwnProperty('afterEnd')) {
          this.eventDetailForm.patchValue({ resizable: { beforeStart: event.resizable.beforeStart, afterEnd: null } });
        } else {
          this.eventDetailForm.patchValue({ resizable: event.resizable });
        }
      } else {
        this.eventDetailForm.patchValue({ resizable: { beforeStart: null, afterEnd: null } });
      }
      this.modalSvc.openDefault(this.eventContent);
    }
  }

  get evDetailFormCtrl() {
    return this.eventDetailForm.controls;
  }

  onSubmit() {
    const event = {
      id: Number(this.evDetailFormCtrl.id.value),
      title: this.evDetailFormCtrl.title.value,
      start: this.evDetailFormCtrl.eventDate.value.from
        ? this.evDetailFormCtrl.eventDate.value.from.value
        : this.evDetailFormCtrl.eventDate.value[0],
      end: this.evDetailFormCtrl.eventDate.value.to
        ? this.evDetailFormCtrl.eventDate.value.to.value
        : this.evDetailFormCtrl.eventDate.value.length > 1
        ? this.evDetailFormCtrl.eventDate.value[1]
        : null,
      allDay: this.evDetailFormCtrl.allDay.value,
      draggable: this.evDetailFormCtrl.draggable.value,
      resizable: this.evDetailFormCtrl.resizable.value
    };
    if (this.modalData.action === 'Edited') {
      this.editEvent.emit({
        event,
        refresh: () => {
          this.refresh.next();
        }
      });
    } else {
      this.addEvent.emit({
        event,
        refresh: () => {
          this.refresh.next();
        }
      });
    }
    this.modalSvc.dismiss();
  }

  get checkPrevDisable(): boolean {    
    if(this.eventConfig.minDate) {
      if(this.view === 'year') {
        return this.viewDate.getFullYear() === this.eventConfig.minDate.getFullYear();
      } else if(this.view === 'month') {
        return ((this.viewDate.getMonth() + 1 === this.eventConfig.minDate.getMonth() + 1) 
          && this.viewDate.getFullYear() === this.eventConfig.minDate.getFullYear());
      } else if(this.view === 'week') {
        return ((this.viewDate.getMonth() + 1 === this.eventConfig.minDate.getMonth() + 1) 
          && this.viewDate.getFullYear() === this.eventConfig.minDate.getFullYear());
      } else { // day
        return ((this.viewDate.getMonth() + 1 === this.eventConfig.minDate.getMonth() + 1) 
        && this.viewDate.getFullYear() === this.eventConfig.minDate.getFullYear()
        && this.viewDate.getDate() === this.eventConfig.minDate.getDate());
      }
      
    }

    return false;
  }

  get checkNextDisable(): boolean {
    if(this.eventConfig.maxDate) {
      if(this.view === 'year') {
        return this.viewDate.getFullYear() === this.eventConfig.maxDate.getFullYear();
      }
      return ((this.viewDate.getMonth() + 1 === this.eventConfig.maxDate.getMonth() + 1) 
      && this.viewDate.getFullYear() === this.eventConfig.maxDate.getFullYear());
    }

    return false;
  }

  ngOnInit() {
    if (!this.allowPastEvent) {
      this.today = null;
    }
    const currentDate = new Date();
    currentDate.setHours(0, 0, 0, 0);
    this.setEventActionsandColour(this.events);
    if (!this.eventConfig.addEventTitle) {
      this.eventConfig.addEventTitle = 'Add';
    }

    if (!this.eventConfig.editEventTitle) {
      this.eventConfig.editEventTitle = 'Edit';
    }

    if (!this.eventConfig.dateType) {
      this.eventConfig.dateType = 'range';
    }
  }

  setEventActionsandColour(events) {
    if (events.length) {
      events.forEach(el => {
        if (
          (el.end && this.canHaveAction(el.end)) ||
          (el.start && this.canHaveAction(el.start)) ||
          this.allowPastEvent
        ) {
          el.actions = this.actions;
        }

        if ( el.start && !(el.start instanceof Date)) {
          el.start = this.formatDateStringToDate(el.start);
        }

        if (el.end && !(el.end instanceof Date)) {
          el.end = this.formatDateStringToDate(el.end);
        }

        if (differenceInCalendarDays(el.start, new Date()) > 0) {
          el.color = this.eventColour.future;
        } else if (
          isSameDay(el.start, new Date()) ||
          (differenceInCalendarDays(el.start, new Date()) < 0 && differenceInCalendarDays(el.end, new Date()) >= 0)
        ) {
          el.color = this.eventColour.today;
        } else {
          el.color = this.eventColour.past;
        }
      });
    }
  }

  canHaveAction(date: Date): boolean {
    return isSameDay(date, new Date()) || differenceInCalendarDays(date, new Date()) >= 0;
  }

  addCalEvent($event: any, date?: Date) {
    if (!date) {
      date = new Date();
    }
    $event.stopPropagation();
    this.eventDetailForm.reset();
    this.modalData = {
      event: {
        title: null,
        start: date
      },
      action: 'Added'
    };
    this.eventDetailForm.reset();
    this.eventDetailForm.patchValue({
      eventDate: [date]
    });
    this.modalSvc.openDefault(this.eventContent);
    this.changeDetector.markForCheck();
  }

  addYear() {
    this.viewDate = addYears(this.viewDate, 1);
  }

  subYear() {
    this.viewDate = subYears(this.viewDate, 1);
  }

  dblClkEvent($event: any, date?: Date) {
    this.isSingleClick = false;
    clearTimeout(this.timer);
    this.addCalEvent($event, date);
  }

  ngDoCheck() {
    const changes = this.iterableDiffer.diff(this.events);
    if (changes) {
      this.setEventActionsandColour(this.events);
      this.refresh.next();
    }
  }

  formatDateStringToDate(dateString: string) {
    const dateTimeArray = dateString.split(' ');
    const dateArray = dateTimeArray[0].split('/');
    const timeArray = dateTimeArray[1].split(':');

    // Add 12 hours when hours is between 1-11 PM
    if (timeArray[0] !== '12' && dateTimeArray[2] === 'PM') {
      timeArray[0] = String(Number(timeArray[0]) + 12);
    }

    // Change hour to 00 when 12AM
    if (timeArray[0] === '12' && dateTimeArray[2] === 'AM') {
      timeArray[0] = '00';
    }

    // Append 0 to single digit AM hour
    if (timeArray[0].length === 1 && dateTimeArray[2] === 'AM') {
      timeArray[0] = `0${timeArray[0]}`;
    }

    let timeString = '';
    timeArray.forEach((time, index) => {
      if (index === 0) {
        timeString = timeString.concat(time);
      } else {
        timeString = timeString.concat(`:${time}`);
      }
    });

    return new Date(`${dateArray[2]}-${dateArray[1]}-${dateArray[0]}T${timeString}`);
  }
}
