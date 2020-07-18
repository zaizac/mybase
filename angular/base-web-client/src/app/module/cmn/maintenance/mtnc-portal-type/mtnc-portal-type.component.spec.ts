import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MtncPortalTypeComponent } from './mtnc-portal-type.component';

describe('MtncPortalTypeComponent', () => {
  let component: MtncPortalTypeComponent;
  let fixture: ComponentFixture<MtncPortalTypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MtncPortalTypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MtncPortalTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
