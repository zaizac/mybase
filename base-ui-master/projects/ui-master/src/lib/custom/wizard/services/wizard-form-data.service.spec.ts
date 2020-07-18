import { TestBed } from '@angular/core/testing';

import { WizardFormDataService } from './wizard-form-data.service';

describe('WizardFormDataService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WizardFormDataService = TestBed.get(WizardFormDataService);
    expect(service).toBeTruthy();
  });
});
