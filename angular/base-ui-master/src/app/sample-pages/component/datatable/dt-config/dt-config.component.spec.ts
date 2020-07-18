import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DtConfigComponent } from './dt-config.component';

describe('DtConfigComponent', () => {
  let component: DtConfigComponent;
  let fixture: ComponentFixture<DtConfigComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DtConfigComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DtConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
