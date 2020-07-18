import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DtbasicComponent } from './dtbasic.component';

describe('DtbasicComponent', () => {
  let component: DtbasicComponent;
  let fixture: ComponentFixture<DtbasicComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DtbasicComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DtbasicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
