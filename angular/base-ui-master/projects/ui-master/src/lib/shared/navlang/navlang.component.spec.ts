import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavlangComponent } from './navlang.component';

describe('NavlangComponent', () => {
  let component: NavlangComponent;
  let fixture: ComponentFixture<NavlangComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavlangComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavlangComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
