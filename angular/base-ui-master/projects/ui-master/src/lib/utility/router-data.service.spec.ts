import { TestBed } from '@angular/core/testing';

import { RouterDataService } from './router-data.service';

describe('RouterDataService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RouterDataService = TestBed.get(RouterDataService);
    expect(service).toBeTruthy();
  });
});
