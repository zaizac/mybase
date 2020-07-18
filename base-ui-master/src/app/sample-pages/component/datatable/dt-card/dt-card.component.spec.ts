import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DtCardComponent } from './dt-card.component';

describe('DtCardComponent', () => {
  let component: DtCardComponent;
  let fixture: ComponentFixture<DtCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DtCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DtCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
