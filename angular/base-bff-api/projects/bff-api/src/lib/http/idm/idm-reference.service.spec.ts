import { TestBed } from '@angular/core/testing';

import { IdmReferenceService } from './idm-reference.service';

describe('IdmReferenceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: IdmReferenceService = TestBed.get(IdmReferenceService);
    expect(service).toBeTruthy();
  });
});
