import { TestBed } from '@angular/core/testing';

import { WfwReferenceService } from './wfw-reference.service';

describe('WfwReferenceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WfwReferenceService = TestBed.get(WfwReferenceService);
    expect(service).toBeTruthy();
  });
});
