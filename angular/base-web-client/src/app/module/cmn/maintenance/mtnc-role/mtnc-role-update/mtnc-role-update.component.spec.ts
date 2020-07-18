import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MtncRoleUpdateComponent } from './mtnc-role-update.component';

describe('MtncRoleUpdateComponent', () => {
  let component: MtncRoleUpdateComponent;
  let fixture: ComponentFixture<MtncRoleUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MtncRoleUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MtncRoleUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
