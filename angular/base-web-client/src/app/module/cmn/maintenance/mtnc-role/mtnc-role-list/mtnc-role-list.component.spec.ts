import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MtncRoleListComponent } from './mtnc-role-list.component';

describe('MtncRoleListComponent', () => {
  let component: MtncRoleListComponent;
  let fixture: ComponentFixture<MtncRoleListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MtncRoleListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MtncRoleListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
