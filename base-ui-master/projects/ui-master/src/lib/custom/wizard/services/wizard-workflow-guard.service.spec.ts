import { TestBed } from '@angular/core/testing';

import { WizardWorkflowGuardService } from './wizard-workflow-guard.service';

describe('WizardWorkflowGuardService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WizardWorkflowGuardService = TestBed.get(WizardWorkflowGuardService);
    expect(service).toBeTruthy();
  });
});
