import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MtncRoleCreateComponent } from './mtnc-role-create.component';

describe('MtncRoleCreateComponent', () => {
  let component: MtncRoleCreateComponent;
  let fixture: ComponentFixture<MtncRoleCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MtncRoleCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MtncRoleCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
