import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MtncUgbListComponent } from './mtnc-ugb-list.component';

describe('MtncUgbListComponent', () => {
  let component: MtncUgbListComponent;
  let fixture: ComponentFixture<MtncUgbListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MtncUgbListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MtncUgbListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
