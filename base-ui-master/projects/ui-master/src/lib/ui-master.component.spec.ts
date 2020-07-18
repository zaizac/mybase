import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UiMasterComponent } from './ui-master.component';

describe('UiMasterComponent', () => {
  let component: UiMasterComponent;
  let fixture: ComponentFixture<UiMasterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [UiMasterComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UiMasterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
