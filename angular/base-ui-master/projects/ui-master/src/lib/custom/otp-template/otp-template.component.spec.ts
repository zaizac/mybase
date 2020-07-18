import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OtpTemplateComponent } from './otp-template.component';

describe('OtpTemplateComponent', () => {
  let component: OtpTemplateComponent;
  let fixture: ComponentFixture<OtpTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OtpTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OtpTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
