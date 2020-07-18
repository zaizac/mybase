import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  TemplateRef
} from '@angular/core';
import { CalendarUtils } from 'angular-calendar';
import { CalendarEvent, MonthViewDay } from 'calendar-utils';
import { addSeconds, differenceInSeconds, getDate, getMonth, getYear, setDate, setMonth, setYear } from 'date-fns';
import { Subject, Subscription } from 'rxjs';
import { getYearView, validateEvents } from '../calendar.utils';

@Component({
  selector: 'ui-calendar-year-view',
  templateUrl: './calendar-year-view.component.html',
  styleUrls: ['./calendar-year-view.component.scss']
})
export class CalendarYearViewComponent implements OnInit, OnChanges, OnDestroy {
  /**
   * @hidden
   */
  view: any;

  @Input() public events: CalendarEvent[] = [];
  @Input() public viewDate: Date;
  /**
   * An observable that when emitted on will re-render the current view
   */
  @Input() refresh: Subject<any>;
  @Input() activeMonthIsOpen = false;
  @Input() openDayEventsTemplate: TemplateRef<any>;
  @Input() eventTitleTemplate: TemplateRef<any>;
  @Input() eventActionsTemplate: TemplateRef<any>;
  @Output() dayClicked = new EventEmitter<any>();
  @Output()
  eventClicked = new EventEmitter<{
    event: CalendarEvent;
  }>();
  @Output()
  eventTimesChanged = new EventEmitter<any>();
  @Input() yearTemplate: TemplateRef<any>;
  /**
   * @hidden
   */
  openRowIndex: number;
  /**
   * If set will be used to determine the day that should be open. If not set, the `viewDate` is used
   */
  @Input() activeMonth: number;

  /**
   * @hidden
   */
  openMonth: any;

  @Output()
  monthClicked = new EventEmitter<{
    month: any;
  }>();

  refreshSubscription: Subscription;
  @Output()
  beforeViewRender = new EventEmitter<any>();

  constructor(protected cdr: ChangeDetectorRef, protected utils: CalendarUtils) {}

  ngOnInit() {
    this.view = getYearView(this.events, this.viewDate);
    if (this.refresh) {
      this.refreshSubscription = this.refresh.subscribe(() => {
        this.refreshAll();
        this.cdr.markForCheck();
      });
    }
  }

  ngOnChanges(changes: any): void {
    const refreshBody = changes.viewDate || changes.events;

    if (changes.events) {
      validateEvents(this.events);
    }

    if (refreshBody) {
      this.refreshBody();
    }

    if (refreshBody) {
      this.emitBeforeViewRender();
    }

    if (changes.activeMonthIsOpen || changes.viewDate || changes.events || changes.activeMonth) {
      this.checkactiveMonthIsOpen();
    }
  }

  ngOnDestroy(): void {
    if (this.refreshSubscription) {
      this.refreshSubscription.unsubscribe();
    }
  }

  protected refreshBody(): void {
    this.view = getYearView(this.events, this.viewDate);
  }

  protected checkactiveMonthIsOpen(): void {
    if (this.view && this.activeMonthIsOpen === true) {
      const activeMonth = this.activeMonth || this.viewDate.getMonth();
      this.openMonth = this.view[activeMonth];
      this.openRowIndex = Math.floor(activeMonth / 4) * 4;
    } else {
      this.openRowIndex = null;
      this.openMonth = null;
    }
  }

  protected refreshAll(): void {
    this.refreshBody();
    this.emitBeforeViewRender();
    this.checkactiveMonthIsOpen();
  }

  protected emitBeforeViewRender(): void {
    this.beforeViewRender.emit({
      body: this.view.month
    });
  }

  eventDropped(droppedOn: MonthViewDay, event: CalendarEvent, draggedFrom?: MonthViewDay): void {
    if (droppedOn !== draggedFrom) {
      const year: number = getYear(droppedOn.date);
      const month: number = getMonth(droppedOn.date);
      const date: number = getDate(droppedOn.date);
      const newStart: Date = setDate(setMonth(setYear(event.start, year), month), date);
      let newEnd: Date;
      if (event.end) {
        const secondsDiff: number = differenceInSeconds(newStart, event.start);
        newEnd = addSeconds(event.end, secondsDiff);
      }
      this.eventTimesChanged.emit({
        event,
        newStart,
        newEnd,
        day: droppedOn,
        type: 'drop'
      });
    }
  }
}
