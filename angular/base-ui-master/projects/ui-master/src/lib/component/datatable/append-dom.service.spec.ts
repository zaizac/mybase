import { TestBed } from '@angular/core/testing';

import { AppendDomService } from './append-dom.service';

describe('AppendDomService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AppendDomService = TestBed.get(AppendDomService);
    expect(service).toBeTruthy();
  });
});
