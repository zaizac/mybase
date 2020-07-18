import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DtTemplateComponent } from './dt-template.component';

describe('DtTemplateComponent', () => {
  let component: DtTemplateComponent;
  let fixture: ComponentFixture<DtTemplateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DtTemplateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DtTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
