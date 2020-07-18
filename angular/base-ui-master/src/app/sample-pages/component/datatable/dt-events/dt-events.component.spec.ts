import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DtEventsComponent } from './dt-events.component';

describe('DtEventsComponent', () => {
  let component: DtEventsComponent;
  let fixture: ComponentFixture<DtEventsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DtEventsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DtEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
