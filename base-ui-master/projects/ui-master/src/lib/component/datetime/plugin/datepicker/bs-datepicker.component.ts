import {
  ComponentRef,
  Directive,
  ElementRef,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  Output,
  Renderer2,
  SimpleChanges,
  ViewContainerRef,
  ViewChild,
  forwardRef
} from '@angular/core';
import { ComponentLoader, ComponentLoaderFactory } from 'ngx-bootstrap/component-loader';
import { Subscription } from 'rxjs';
import { BsDatepickerConfig } from './bs-datepicker.config';
import { BsDatepickerViewMode, DatepickerDateCustomClasses } from './models';
import { BsCustomDates, ViewDateValue, ShowTimes, DateButtons, Timevalue } from './themes/bs/bs-custom-dates-view.component';
import { BsDatepickerContainerComponent } from './themes/bs/bs-datepicker-container.component';
import { DateService } from '../../date/date.service';
import { DatetimepickerComponent } from '../datetimepicker/datetimepicker.module';


@Directive({
  selector: '[bsDatepicker]',
  exportAs: 'bsDatepicker'
})
export class BsDatepickerDirective implements OnInit, OnDestroy, OnChanges {
  /**
   * Placement of a datepicker. Accepts: "top", "bottom", "left", "right"
   */
  @Input() placement: 'top' | 'bottom' | 'left' | 'right' = 'bottom';
  /**
   * Specifies events that should trigger. Supports a space separated list of
   * event names.
   */
  @Input() triggers = 'click';
  /**
   * Close datepicker on outside click
   */
  @Input() outsideClick = true;
  /**
   * A selector specifying the element the datepicker should be appended to.
   */
  // @Input() container = 'body';
  @Input() container: string;

  @Input() outsideEsc = true;

  /**
   * Returns whether or not the datepicker is currently being shown
   */
  @Input()
  get isOpen(): boolean {
    return this._datepicker.isShown;
  }

  set isOpen(value: boolean) {
    if (value) {
      this.show();
    } else {
      this.hide();
    }
  }

  /**
   * Emits an event when the datepicker is shown
   */
  /* tslint:disable-next-line: no-any*/
  @Output() onShown: EventEmitter<any>;
  /**
   * Emits an event when the datepicker is hidden
   */
  /* tslint:disable-next-line: no-any*/
  @Output() onHidden: EventEmitter<any>;

  _bsValue: Date;
  /**
   * Initial value of datepicker
   */
  @Input()
  set bsValue(value: Date) {
    if (this._bsValue === value) {
      return;
    }
    this._bsValue = value;
    this.bsValueChange.emit(value);
  }

  /**
   * Config object for datepicker
   */
  @Input() bsConfig: Partial<BsDatepickerConfig>;
  /**
   * Indicates whether datepicker's content is enabled or not
   */
  @Input() isDisabled: boolean;
  /**
   * Minimum date which is available for selection
   */
  @Input() minDate: Date;
  /**
   * Maximum date which is available for selection
   */
  @Input() maxDate: Date;

  @Input() customDates: BsCustomDates[];
  @Input() viewDate: ViewDateValue[];
  @Input() showTime: ShowTimes;
  @Input() dateButtons: DateButtons;
  @Input() timevalue: Timevalue[] = [];

  @Input() dateCustomClasses: DatepickerDateCustomClasses[];
  /**
   * Minimum view mode : day, month, or year
   */
  @Input() minMode: BsDatepickerViewMode;

  /**
   * Disable Certain days in the week
   */
  @Input() daysDisabled: number[];

  /**
   * Disable specific dates
   */
  @Input() datesDisabled: Date[];
  /**
   * Emits when datepicker value has been changed
   */
  @Output() bsValueChange: EventEmitter<Date> = new EventEmitter();

  hour: any;
  minute: any;
  second: any;
  pm: any;

  @ViewChild(forwardRef(() => DatetimepickerComponent)) timevaluecom: DatetimepickerComponent;

  protected _subs: Subscription[] = [];

  private _datepicker: ComponentLoader<BsDatepickerContainerComponent>;
  private _datepickerRef: ComponentRef<BsDatepickerContainerComponent>;

  constructor(
    public _config: BsDatepickerConfig,
    private _elementRef: ElementRef,
    _renderer: Renderer2,
    _viewContainerRef: ViewContainerRef,
    cis: ComponentLoaderFactory,
    private _dateService: DateService
  ) {
    // todo: assign only subset of fields
    Object.assign(this, this._config);
    this._datepicker = cis.createLoader<BsDatepickerContainerComponent>(_elementRef, _viewContainerRef, _renderer);
    this.onShown = this._datepicker.onShown;
    this.onHidden = this._datepicker.onHidden;

    // from time timepicker to datepicker 
    this._dateService.hour$.subscribe(
      hours => {
        this.hour = hours;
        this.timevalue.push({ hour: this.hour });
      });
    this._dateService.minute$.subscribe(
      minutes => {
        this.minute = minutes;
        this.timevalue.push({ minute: this.minute });
      });
    this._dateService.second$.subscribe(
      seconds => {
        this.second = seconds;
        this.timevalue.push({ seconds: this.second });
      });
    this._dateService.pm$.subscribe(
      pms => {
        this.pm = pms;
        this.timevalue.push({ isPM: this.pm });
      });
  }

  ngOnInit(): void {
    this._datepicker.listen({
      outsideClick: this.outsideClick,
      outsideEsc: this.outsideEsc,
      triggers: this.triggers,
      show: () => this.show()
    });
    this.setConfig();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (!this._datepickerRef || !this._datepickerRef.instance) {
      return;
    }

    if (changes.minDate) {
      this._datepickerRef.instance.minDate = this.minDate;
    }

    if (changes.maxDate) {
      this._datepickerRef.instance.maxDate = this.maxDate;
    }

    if (changes.daysDisabled) {
      this._datepickerRef.instance.daysDisabled = this.daysDisabled;
    }

    if (changes.datesDisabled) {
      this._datepickerRef.instance.datesDisabled = this.datesDisabled;
    }

    if (changes.isDisabled) {
      this._datepickerRef.instance.isDisabled = this.isDisabled;
    }

    if (changes.dateCustomClasses) {
      this._datepickerRef.instance.dateCustomClasses = this.dateCustomClasses;
    }
  }

  /**
   * Opens an element’s datepicker. This is considered a “manual” triggering of
   * the datepicker.
   */
  show(): void {
    if (this._datepicker.isShown) {
      return;
    }

    const inputEl = this._elementRef.nativeElement as Element;

    this.setConfig();

    this._datepickerRef = this._datepicker
      .provide({ provide: BsDatepickerConfig, useValue: this._config })
      .attach(BsDatepickerContainerComponent)
      .to(this.container)
      .position({ attachment: this.placement })
      .show({ placement: this.placement });

    const bsDate = document.getElementsByTagName('bs-datepicker-container')[0].children[0];
    bsDate.setAttribute(
      'style',
      'left: -' + (inputEl.getBoundingClientRect().width / 2 - bsDate.getBoundingClientRect().width / 2) + 'px'
    );

    // if date changes from external source (model -> view)
    this._subs.push(
      this.bsValueChange.subscribe((value: Date) => {
        this._datepickerRef.instance.value = value;
      })
    );

    // if date changes from picker (view -> model)
    this._subs.push(
      this._datepickerRef.instance.valueChange.subscribe((value: Date) => {
        this.bsValue = value;
        //console.log("Value from bsDatepicker :", value);
        this.hide();
      })
    );
  }

  /**
   * Closes an element’s datepicker. This is considered a “manual” triggering of
   * the datepicker.
   */
  hide(): void {
    if (this.isOpen) {
      this._datepicker.hide();
    }
    for (const sub of this._subs) {
      sub.unsubscribe();
    }
  }

  /**
   * Toggles an element’s datepicker. This is considered a “manual” triggering
   * of the datepicker.
   */
  toggle(): void {

    if (this.isOpen) {
      return this.hide();
    }

    this.show();
  }

  /**
   * Set config for datepicker
   */
  setConfig(): void {
    this._config = Object.assign({}, this._config, this.bsConfig, {
      value: this._bsValue,
      isDisabled: this.isDisabled,
      minDate: this.minDate || (this.bsConfig && this.bsConfig.minDate),
      maxDate: this.maxDate || (this.bsConfig && this.bsConfig.maxDate),
      daysDisabled: this.daysDisabled || (this.bsConfig && this.bsConfig.daysDisabled),
      datesDisabled: this.datesDisabled || (this.bsConfig && this.bsConfig.datesDisabled),
      dateCustomClasses: this.dateCustomClasses || this.bsConfig && this.bsConfig.dateCustomClasses,
      minMode: this.minMode || this.bsConfig && this.bsConfig.minMode,
      customDates: this.customDates,
      viewDate: this.viewDate,
      showTime: this.showTime,
      dateButtons: this.dateButtons,
      timeValue: this.timevalue
    });
  }

  ngOnDestroy(): void {
    this._datepicker.dispose();
  }
}
