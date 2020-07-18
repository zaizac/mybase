import { TestBed } from '@angular/core/testing';

import { ToastrComponentsService } from './toastr.service';

describe('ToastrComponentsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ToastrComponentsService = TestBed.get(ToastrComponentsService);
    expect(service).toBeTruthy();
  });
});
