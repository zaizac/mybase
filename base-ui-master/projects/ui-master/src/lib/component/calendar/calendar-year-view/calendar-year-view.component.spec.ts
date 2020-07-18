import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CalendarYearViewComponent } from './calendar-year-view.component';

describe('CalendarYearViewComponent', () => {
  let component: CalendarYearViewComponent;
  let fixture: ComponentFixture<CalendarYearViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CalendarYearViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CalendarYearViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
