import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleMenuCreateComponent } from './role-menu-create.component';

describe('RoleMenuCreateComponent', () => {
  let component: RoleMenuCreateComponent;
  let fixture: ComponentFixture<RoleMenuCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RoleMenuCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleMenuCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
