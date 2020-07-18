import { TestBed } from '@angular/core/testing';

import { WebInboxService } from './inbox.service';

describe('InboxService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WebInboxService = TestBed.get(WebInboxService);
    expect(service).toBeTruthy();
  });
});
