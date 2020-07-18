import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DtRenderComponent } from './dt-render.component';

describe('DtRenderComponent', () => {
  let component: DtRenderComponent;
  let fixture: ComponentFixture<DtRenderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DtRenderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DtRenderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
