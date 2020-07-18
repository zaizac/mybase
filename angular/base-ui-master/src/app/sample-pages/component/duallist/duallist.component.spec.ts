import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DuallistComponent } from './duallist.component';

describe('DuallistComponent', () => {
  let component: DuallistComponent;
  let fixture: ComponentFixture<DuallistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DuallistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DuallistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
