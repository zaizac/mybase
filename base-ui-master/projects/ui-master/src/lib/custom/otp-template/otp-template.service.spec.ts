import { TestBed } from '@angular/core/testing';

import { OtpTemplateService } from './otp-template.service';

describe('OtpService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OtpTemplateService = TestBed.get(OtpTemplateService);
    expect(service).toBeTruthy();
  });
});
