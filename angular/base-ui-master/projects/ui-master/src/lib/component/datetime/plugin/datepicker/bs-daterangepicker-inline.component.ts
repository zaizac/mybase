import {
    ComponentRef, Directive, ElementRef, EventEmitter, Input, OnChanges,
    OnDestroy, OnInit, Output, Renderer2, SimpleChanges, ViewContainerRef
} from '@angular/core';
import { ComponentLoader, ComponentLoaderFactory } from 'ngx-bootstrap/component-loader';
import { Subscription } from 'rxjs';
import { BsDatepickerConfig } from './bs-datepicker.config';
import { DatepickerDateCustomClasses } from './models';
import { BsDaterangepickerInlineConfig } from './bs-daterangepicker-inline.config';
import { BsDaterangepickerInlineContainerComponent } from './themes/bs/bs-daterangepicker-inline-container.component';
import { filter } from 'rxjs/operators';
import { DatepickerService } from './datepicker.service';

@Directive({
    selector: 'bs-daterangepicker-inline',
    exportAs: 'bsDaterangepickerInline'
})
export class BsDaterangepickerInlineDirective
    implements OnInit, OnDestroy, OnChanges {
    _bsValue: Date[];
    /**
     * Initial value of datepicker
     */
    @Input()
    set bsValue(value: Date[]) {
        if (this._bsValue === value) {
            return;
        }
        this._bsValue = value;
        this.bsValueChange.emit(value);
    }

    /**
     * Config object for datepicker
     */
    @Input() bsConfig: Partial<BsDaterangepickerInlineConfig>;
    /**
     * Indicates whether datepicker is enabled or not
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
    /**
     * Date custom classes
     */
    @Input() dateCustomClasses: DatepickerDateCustomClasses[];
    /**
     * Disable specific dates
     */
    @Input() datesDisabled: Date[];
    /**
     * Emits when daterangepicker value has been changed
     */
    @Output() bsValueChange: EventEmitter<Date[]> = new EventEmitter();

    protected _subs: Subscription[] = [];

    private _datepicker: ComponentLoader<BsDaterangepickerInlineContainerComponent>;
    private _datepickerRef: ComponentRef<BsDaterangepickerInlineContainerComponent>;

    constructor(public _config: BsDaterangepickerInlineConfig,
                private _elementRef: ElementRef,
                _renderer: Renderer2,
                _viewContainerRef: ViewContainerRef,
                cis: ComponentLoaderFactory,
                private _datepickerService : DatepickerService) {
        // todo: assign only subset of fields
        Object.assign(this, this._config);
        this._datepicker = cis.createLoader<BsDaterangepickerInlineContainerComponent>(
            _elementRef,
            _viewContainerRef,
            _renderer
        );
    }

    ngOnInit(): void {
        this.setConfig();

        this._datepickerRef = this._datepicker
            .provide({ provide: BsDatepickerConfig, useValue: this._config })
            .attach(BsDaterangepickerInlineContainerComponent)
            .to(this._elementRef)
            .show();

        // if date changes from external source (model -> view)
        this._subs.push(
            this.bsValueChange.subscribe((value: Date[]) => {
                this._datepickerRef.instance.value = value;
            })
        );

        // if date changes from picker (view -> model)
        this._subs.push(
            this._datepickerRef.instance.valueChange
                .pipe(
                    filter((range: Date[]) => range && range[0] && !!range[1])
                )
                .subscribe((value: Date[]) => {
                    this.bsValue = value;
                    this._datepickerService.publishDateValue(this.bsValue = value);
                })
        );
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
     * Set config for datepicker
     */
    setConfig(): void {
        this._config = Object.assign({}, this._config, this.bsConfig, {
            value: this._bsValue,
            isDisabled: this.isDisabled,
            minDate: this.minDate || this.bsConfig && this.bsConfig.minDate,
            maxDate: this.maxDate || this.bsConfig && this.bsConfig.maxDate,
            //dateCustomClasses: this.dateCustomClasses || this.bsConfig && this.bsConfig.dateCustomClasses,
            datesDisabled: this.datesDisabled || this.bsConfig && this.bsConfig.datesDisabled
        });
    }

    ngOnDestroy(): any {
        this._datepicker.dispose();
    }
}