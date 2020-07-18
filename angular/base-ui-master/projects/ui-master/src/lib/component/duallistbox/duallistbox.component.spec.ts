import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DuallistboxComponent } from './duallistbox.component';

describe('DuallistboxComponent', () => {
  let component: DuallistboxComponent;
  let fixture: ComponentFixture<DuallistboxComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DuallistboxComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DuallistboxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
