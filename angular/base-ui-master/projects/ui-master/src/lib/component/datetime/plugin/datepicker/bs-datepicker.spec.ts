import { Component, ViewChild, Renderer2 } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BsDatepickerContainerComponent } from './themes/bs/bs-datepicker-container.component';
import { CalendarCellViewModel, WeekViewModel } from './models';
// TODO: import { dispatchKeyboardEvent } from '@netbasal/spectator';
import { registerEscClick } from 'ngx-bootstrap/utils/triggers';
import { BsDatepickerDirective } from './bs-datepicker.component';
import { BsDatepickerConfig } from './bs-datepicker.config';
import { BsDatepickerModule } from './bs-datepicker.module';


@Component({
  selector: 'test-cmp',
  template: `<input type="text" bsDatepicker [bsConfig]="bsConfig">`
})
class TestComponent {
  @ViewChild(BsDatepickerDirective) datepicker: BsDatepickerDirective;
  bsConfig: Partial<BsDatepickerConfig> = {
    displayMonths: 2,
    selectWeek: true
  };
}

type TestFixture = ComponentFixture<TestComponent>;

function getDatepickerDirective(fixture: TestFixture): BsDatepickerDirective {
  const datepicker: BsDatepickerDirective = fixture.componentInstance.datepicker;

  return datepicker;
}

function showDatepicker(fixture: TestFixture): BsDatepickerDirective {
  const datepicker = getDatepickerDirective(fixture);
  datepicker.show();
  fixture.detectChanges();

  return datepicker;
}

function hideDatepicker(fixture: TestFixture): BsDatepickerDirective {
  const datepicker = getDatepickerDirective(fixture);
  datepicker.hide();
  fixture.detectChanges();

  return datepicker;
}

function getDatepickerContainer(datepicker: BsDatepickerDirective): BsDatepickerContainerComponent | null {
  return datepicker[`_datepickerRef`] ? datepicker[`_datepickerRef`].instance : null;
}

describe('datepicker:', () => {
  let fixture: TestFixture;
  beforeEach(
    async(() => TestBed.configureTestingModule({
      declarations: [TestComponent],
      imports: [BsDatepickerModule.forRoot()]
    }).compileComponents()
    ));
  beforeEach(() => {
    fixture = TestBed.createComponent(TestComponent);
    fixture.detectChanges();
  });

  it('should display datepicker on show', () => {
    const datepicker = showDatepicker(fixture);
    expect(getDatepickerContainer(datepicker)).toBeDefined();
  });

  it('should hide datepicker on hide', () => {
    const datepicker = hideDatepicker(fixture);
    expect(getDatepickerContainer(datepicker)).toBeNull();
  });

  it('should select correct year when a month other than selected year is chosen', () => {
    const datepicker = showDatepicker(fixture);
    const datepickerContainerInstance = getDatepickerContainer(datepicker);
    const yearSelection: CalendarCellViewModel = { date: new Date(2017, 1, 1), label: 'label' };
    const monthSelection: CalendarCellViewModel = { date: new Date(2018, 1, 1), label: 'label' };
    datepickerContainerInstance.yearSelectHandler(yearSelection);
    datepickerContainerInstance.monthSelectHandler(monthSelection);
    fixture.detectChanges();
    datepickerContainerInstance[`_store`]
      .select(state => state.view)
      .subscribe(view => {
        expect(view.date.getFullYear()).toEqual(monthSelection.date.getFullYear());
      });
  });

  it('should select a week, when selectWeek flag is true', () => {
    const datepicker = showDatepicker(fixture);
    const datepickerContainerInstance = getDatepickerContainer(datepicker);
    datepickerContainerInstance.setViewMode('day');
    const weekSelection: WeekViewModel = {
      days: [
        { date: new Date(2019, 1, 6), label: 'label' },
        { date: new Date(2019, 1, 7), label: 'label' },
        { date: new Date(2019, 1, 8), label: 'label' },
        { date: new Date(2019, 1, 9), label: 'label' },
        { date: new Date(2019, 1, 10), label: 'label' },
        { date: new Date(2019, 1, 11), label: 'label' },
        { date: new Date(2019, 1, 12), label: 'label' }
      ], isHovered: true
    };
    datepickerContainerInstance.weekHoverHandler(weekSelection);
    fixture.detectChanges();
    datepickerContainerInstance[`_store`]
      .select(state => state.view)
      .subscribe(view => {
        expect(view.date.getDate()).not.toEqual((weekSelection.days[0].date.getDate()));
      });
  });

  it('should hide on esc', async(() => {
    const datepicker = showDatepicker(fixture);
    const spy = spyOn(datepicker, 'hide');
    const renderer = fixture.componentRef.injector.get<Renderer2>(Renderer2 as any);

    registerEscClick(renderer, {
      outsideEsc: true,
      target: fixture.nativeElement,
      hide: () => datepicker.hide()
    });

    // TODO: dispatchKeyboardEvent(document, 'keyup', 'Escape');

    expect(spy).toHaveBeenCalled();
  }));
});
