import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormWizardGuideComponent } from './form-wizard-guide.component';

describe('FormWizardGuideComponent', () => {
  let component: FormWizardGuideComponent;
  let fixture: ComponentFixture<FormWizardGuideComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormWizardGuideComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormWizardGuideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
