import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DtCheckboxRadioComponent } from './dt-checkbox-radio.component';

describe('DtCheckboxRadioComponent', () => {
  let component: DtCheckboxRadioComponent;
  let fixture: ComponentFixture<DtCheckboxRadioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DtCheckboxRadioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DtCheckboxRadioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
