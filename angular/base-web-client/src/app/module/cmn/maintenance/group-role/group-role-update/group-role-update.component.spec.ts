import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GroupRoleUpdateComponent } from './group-role-update.component';

describe('GroupRoleUpdateComponent', () => {
  let component: GroupRoleUpdateComponent;
  let fixture: ComponentFixture<GroupRoleUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GroupRoleUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GroupRoleUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
