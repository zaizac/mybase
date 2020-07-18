import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DaterangecustomComponent } from './daterangecustom.component';

describe('DaterangecustomComponent', () => {
  let component: DaterangecustomComponent;
  let fixture: ComponentFixture<DaterangecustomComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DaterangecustomComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DaterangecustomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
