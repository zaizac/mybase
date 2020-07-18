import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MtncUgbUpdateComponent } from './mtnc-ugb-update.component';

describe('MtncUgbUpdateComponent', () => {
  let component: MtncUgbUpdateComponent;
  let fixture: ComponentFixture<MtncUgbUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MtncUgbUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MtncUgbUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
