import { TestBed } from '@angular/core/testing';

import { IntlTelService } from './intl-tel.service';

describe('IntlTelService', () => {
	beforeEach(() => TestBed.configureTestingModule({}));

	it('should be created', () => {
		const service: IntlTelService = TestBed.get(IntlTelService);
		expect(service).toBeTruthy();
	});
});
