import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserGroupUpdateComponent } from './user-group-update.component';

describe('UserGroupUpdateComponent', () => {
  let component: UserGroupUpdateComponent;
  let fixture: ComponentFixture<UserGroupUpdateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserGroupUpdateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserGroupUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
