import { TestBed } from '@angular/core/testing';

import { WizardCommunicationService } from './wizard-communication.service';

describe('WizardCommunicationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WizardCommunicationService = TestBed.get(WizardCommunicationService);
    expect(service).toBeTruthy();
  });
});
