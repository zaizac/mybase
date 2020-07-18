import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BffApiComponent } from './bff-api.component';

describe('BffApiComponent', () => {
  let component: BffApiComponent;
  let fixture: ComponentFixture<BffApiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [BffApiComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BffApiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
