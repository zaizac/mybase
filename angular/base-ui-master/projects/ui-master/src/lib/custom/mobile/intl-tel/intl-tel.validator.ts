import { FormControl } from '@angular/forms';
import * as lpn from 'google-libphonenumber';

export const phoneNumberValidator = (control: FormControl) => {
	const isCheckValidation = document.getElementById('phone').getAttribute('validation');
	if (isCheckValidation == 'true') {		
		const error = { validatePhoneNumber: { valid: false } };
		let number: lpn.PhoneNumber;

		// Validate if control is an object else ignore
		if (typeof control.value == 'object') {
			try {
				number = lpn.PhoneNumberUtil.getInstance().parse(control.value.number, control.value.countryCode);
			} catch (e) {
				// DO NOTHING
			}

			if (control.value && control.value != null) {			
				if (!number) {
					return error;
				} else {
					if (!lpn.PhoneNumberUtil.getInstance().isValidNumberForRegion(number, control.value.countryCode)) {
						return error;
					}
				}
			}
		}
	} else if (isCheckValidation == 'false') {
		control.clearValidators();
	}
	return;

};
