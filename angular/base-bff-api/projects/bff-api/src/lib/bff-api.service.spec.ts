import { TestBed } from '@angular/core/testing';

import { BffApiService } from './bff-api.service';

describe('BffApiService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: BffApiService = TestBed.get(BffApiService);
    expect(service).toBeTruthy();
  });
});
