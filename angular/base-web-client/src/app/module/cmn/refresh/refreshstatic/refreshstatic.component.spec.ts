import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RefreshstaticComponent } from './refreshstatic.component';

describe('RefreshstaticComponent', () => {
  let component: RefreshstaticComponent;
  let fixture: ComponentFixture<RefreshstaticComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RefreshstaticComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RefreshstaticComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
