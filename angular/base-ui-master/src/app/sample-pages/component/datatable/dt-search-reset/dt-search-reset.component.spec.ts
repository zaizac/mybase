import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DtSearchResetComponent } from './dt-search-reset.component';

describe('DtSearchResetComponent', () => {
  let component: DtSearchResetComponent;
  let fixture: ComponentFixture<DtSearchResetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DtSearchResetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DtSearchResetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
