import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IntlTelComponent } from './intl-tel.component';

describe('IntlTelComponent', () => {
  let component: IntlTelComponent;
  let fixture: ComponentFixture<IntlTelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IntlTelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IntlTelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
