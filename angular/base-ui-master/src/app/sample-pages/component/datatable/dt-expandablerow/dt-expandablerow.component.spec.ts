import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DtExpandablerowComponent } from './dt-expandablerow.component';

describe('DtExpandablerowComponent', () => {
  let component: DtExpandablerowComponent;
  let fixture: ComponentFixture<DtExpandablerowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DtExpandablerowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DtExpandablerowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
