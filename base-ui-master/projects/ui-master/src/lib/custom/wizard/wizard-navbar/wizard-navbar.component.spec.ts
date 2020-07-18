import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WizardNavbarComponent } from './wizard-navbar.component';

describe('WizardNavbarComponent', () => {
  let component: WizardNavbarComponent;
  let fixture: ComponentFixture<WizardNavbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WizardNavbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WizardNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
