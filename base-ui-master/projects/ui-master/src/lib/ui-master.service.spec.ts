import { TestBed } from '@angular/core/testing';

import { UiMasterService } from './ui-master.service';

describe('UiMasterService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UiMasterService = TestBed.get(UiMasterService);
    expect(service).toBeTruthy();
  });
});
