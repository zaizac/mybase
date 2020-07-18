import { TestBed } from '@angular/core/testing';

import { InboxCommunicationService } from './inbox-communication.service';

describe('InboxCommunicationService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: InboxCommunicationService = TestBed.get(InboxCommunicationService);
    expect(service).toBeTruthy();
  });
});
