import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegDetailsComponent } from './reg-details.component';

describe('RegDetailsComponent', () => {
  let component: RegDetailsComponent;
  let fixture: ComponentFixture<RegDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
