import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MtncOauthClientDetailsComponent } from './mtnc-oauth-client-details.component';

describe('MtncOauthClientDetailsComponent', () => {
  let component: MtncOauthClientDetailsComponent;
  let fixture: ComponentFixture<MtncOauthClientDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MtncOauthClientDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MtncOauthClientDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
