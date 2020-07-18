import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MtncUgbCreateComponent } from './mtnc-ugb-create.component';

describe('MtncUgbCreateComponent', () => {
  let component: MtncUgbCreateComponent;
  let fixture: ComponentFixture<MtncUgbCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MtncUgbCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MtncUgbCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
