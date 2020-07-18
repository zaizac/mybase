import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DemodateComponent } from './demodate.component';

describe('DemodateComponent', () => {
  let component: DemodateComponent;
  let fixture: ComponentFixture<DemodateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DemodateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DemodateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
