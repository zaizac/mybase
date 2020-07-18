package com.bff.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.be.sdk.model.Document;
import com.bff.cmn.form.CustomMultipartFile;
import com.bff.cmn.form.FileUpload;
import com.bff.util.constants.MessageConstants;
import com.util.BaseUtil;
import com.util.DateUtil;
import com.util.constants.BaseConstants;


public class ValidationUtil extends ValidationUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationUtil.class);

	private static final String ASSERT_ERROR_NOT_NULL = "Errors object must not be null";

	private static final String PFX_DEFAULT = "default";


	public static void isValidPost(Errors errors, String field, String errorCode, String currAddress,
			String billAddress) {
		isValidPost(errors, field, errorCode, null, null, currAddress, billAddress);
	}


	public static void isValidPost(Errors errors, String field, String errorCode, Object[] errorArgs,
			String defaultMessage, String currAddress, String billAddress) {
		if (currAddress == null && billAddress.isEmpty()) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}


	public static void isNumeric(Errors errors, String field, String errorCode) {
		isNumeric(errors, field, errorCode, null, null);
	}


	public static void isNumeric(Errors errors, String field, String errorCode, String defaultMessage) {
		isNumeric(errors, field, errorCode, null, defaultMessage);
	}


	public static void isNumeric(Errors errors, String field, String errorCode, Object[] errorArgs) {
		isNumeric(errors, field, errorCode, errorArgs, null);
	}


	public static void isNumeric(Errors errors, String field, String errorCode, Object[] errorArgs,
			String defaultMessage) {
		String strValidChars = "0123456789.-";
		char strChar;
		Object numeric = errors.getFieldValue(field);
		String str = numeric.toString();
		if (str.length() == 0) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}

		for (int i = 0; i < str.length(); i++) {
			strChar = str.charAt(i);

			if (strValidChars.indexOf(strChar) == -1) {
				errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
			}
		}
	}


	public static void rejectSameTwoStrings(Errors errors, String field1, String field2, String errorCode) {
		rejectSameTwoStrings(errors, field1, field2, errorCode, null, null);
	}


	public static void rejectSameTwoStrings(Errors errors, String field1, String field2, String errorCode,
			Object[] errorArgs) {
		rejectSameTwoStrings(errors, field1, field2, errorCode, errorArgs, null);
	}


	public static void rejectSameTwoStrings(Errors errors, String field1, String field2, String errorCode,
			String defaultMessage) {
		rejectSameTwoStrings(errors, field1, field2, errorCode, null, defaultMessage);
	}


	public static void rejectSameNumbers(Errors errors, String field, List<String> numbers, String errorCode) {
		rejectSameNumbers(errors, field, numbers, errorCode, null, null);
	}


	public static void rejectSameNumbers(Errors errors, String field, List<String> numbers, String errorCode,
			Object[] errorArgs, String defaultMessage) {
		for (int i = 0; i < numbers.size(); i++) {
			if (numbers.get(i) != "") {
				for (int x = i + 1; x < numbers.size(); x++) {
					if (numbers.get(i).equals(numbers.get(x))) {
						errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
						break;
					}
				}
			}

		}
	}


	public static void rejectSameTwoStrings(Errors errors, String field1, String field2, String errorCode,
			Object[] errorArgs, String defaultMessage) {
		Object value1 = errors.getFieldValue(field1);
		Object value2 = errors.getFieldValue(field2);
		if (StringUtils.hasText(value1.toString()) && StringUtils.hasText(value2.toString())
				&& value1.toString().equals(value2.toString())) {
			errors.rejectValue(field2, errorCode, errorArgs, defaultMessage);
		}
	}


	public static void compareTwoStrings(Errors errors, String field1, String field2, String errorCode) {
		compareTwoStrings(errors, field1, field2, errorCode, null, null);
	}


	public static void compareTwoStrings(Errors errors, String field1, String field2, String errorCode,
			Object[] errorArgs) {
		compareTwoStrings(errors, field1, field2, errorCode, errorArgs, null);
	}


	public static void compareTwoStrings(Errors errors, String field1, String field2, String errorCode,
			String defaultMessage) {
		compareTwoStrings(errors, field1, field2, errorCode, null, defaultMessage);
	}


	public static void compareTwoStrings(Errors errors, String field1, String field2, String errorCode,
			Object[] errorArgs, String defaultMessage) {
		Assert.notNull(errors, ASSERT_ERROR_NOT_NULL);
		Object value1 = errors.getFieldValue(field1);
		Object value2 = errors.getFieldValue(field2);
		if (StringUtils.hasText(value1.toString()) && StringUtils.hasText(value2.toString())
				&& !value1.toString().equals(value2.toString())) {
			errors.rejectValue(field1, errorCode, errorArgs, defaultMessage);
		}
	}


	public static void rejectIfLimitIsInvalid(Errors errors, String field, String errorCode, int length) {
		rejectIfLimitIsInvalid(errors, field, errorCode, null, null, length);
	}


	public static void rejectIfAmountIsInvalid(Errors errors, String field, String errorCode) {
		rejectIfAmountIsInvalid(errors, field, errorCode, null, null);
	}


	/**
	 *
	 * @param errors
	 * @param field
	 * @param errorCode
	 * @param length
	 */
	public static void rejectIfLengthIsInvalid(Errors errors, String field, String errorCode, int length) {
		rejectIfLengthIsInvalid(errors, field, errorCode, null, null, length);
	}


	/**
	 *
	 * @param errors
	 * @param field
	 * @param errorCode
	 * @param minLength
	 * @param maxLength
	 */
	public static void rejectIfLengthIsInvalid(Errors errors, String field, String errorCode, int minLength,
			int maxLength) {
		rejectIfLengthIsInvalid(errors, field, errorCode, null, null, minLength, maxLength);
	}


	/**
	 *
	 * @param errors
	 * @param field
	 * @param errorCode
	 * @param errorArgs
	 * @param length
	 */
	public static void rejectIfLengthIsInvalid(Errors errors, String field, String errorCode, Object[] errorArgs,
			int length) {
		rejectIfLengthIsInvalid(errors, field, errorCode, errorArgs, null, length);
	}


	/**
	 *
	 * @param errors
	 * @param field
	 * @param errorCode
	 * @param defaultMessage
	 * @param length
	 */
	public static void rejectIfLengthIsInvalid(Errors errors, String field, String errorCode, String defaultMessage,
			int length) {
		rejectIfLengthIsInvalid(errors, field, errorCode, null, defaultMessage, length);
	}


	/**
	 *
	 * @param errors
	 * @param field
	 * @param errorCode
	 * @param errorArgs
	 * @param defaultMessage
	 * @param length
	 */
	public static void rejectIfAmountIsInvalid(Errors errors, String field, String errorCode, Object[] errorArgs,
			String defaultMessage) {
		Assert.notNull(errors, ASSERT_ERROR_NOT_NULL);
		Object amount = errors.getFieldValue(field);
		if (Double.parseDouble(amount.toString()) < 1000) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}


	public static void rejectIfLimitIsInvalid(Errors errors, String field, String errorCode, Object[] errorArgs,
			String defaultMessage, int length) {
		Assert.notNull(errors, ASSERT_ERROR_NOT_NULL);
		Object value = errors.getFieldValue(field);
		if (StringUtils.hasText(value.toString()) && value.toString().length() > length) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}


	public static void rejectIfLengthIsInvalid(Errors errors, String field, String errorCode, Object[] errorArgs,
			String defaultMessage, int length) {
		Assert.notNull(errors, ASSERT_ERROR_NOT_NULL);
		Object value = errors.getFieldValue(field);
		if (StringUtils.hasText(value.toString()) && value.toString().length() < length) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}


	public static void rejectIfLengthIsInvalid(Errors errors, String field, String errorCode, Object[] errorArgs,
			String defaultMessage, int minLength, int maxLength) {
		Assert.notNull(errors, ASSERT_ERROR_NOT_NULL);
		Object value = errors.getFieldValue(field);
		if (StringUtils.hasText(value.toString()) && value.toString().length() < minLength
				|| value.toString().length() > maxLength) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}


	public static void rejectIfInvalidFile(Errors errors, List<Document> refDocLst, FileUpload fu, String field) {
		rejectIfInvalidFile(errors, refDocLst, fu, field, PFX_DEFAULT, null, null, false);
	}


	public static void rejectIfInvalidFile(Errors errors, List<Document> refDocLst, FileUpload fu, String field,
			boolean checkCompulsary) {
		rejectIfInvalidFile(errors, refDocLst, fu, field, PFX_DEFAULT, null, null, checkCompulsary);
	}


	public static void rejectIfInvalidFile(Errors errors, List<Document> refDocLst, FileUpload fu, String field,
			String errorCode, Object[] errorArgs, String defaultMessage, boolean checkCompulsary) {
		CustomMultipartFile file = (fu.getFile() instanceof CustomMultipartFile) ? fu.getFile() : null;
		Object value = errors.getFieldValue(field);
		LOGGER.debug("value: {}", value);
		for (Document refDoc : refDocLst) {
			LOGGER.debug("Document: {} - {}", refDoc.getDocId(), refDoc.isCompulsary());
			boolean isCompulsary = refDoc.isCompulsary();
			if (refDoc.getDocId() == fu.getDocId()) {
				if ((isCompulsary || checkCompulsary) && (BaseUtil.isObjNull(file) || file.getSize() == 0)) {
					if (BaseUtil.isEqualsCaseIgnore(PFX_DEFAULT, errorCode)) {
						errors.rejectValue(field, MessageConstants.ERROR_DOC_ATTCH, null, null);
					} else {
						errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
					}
				}
				break;
			}
		}
	}


	public static void rejectIfInvalidFileSize(Errors errors, List<Document> refDocLst, FileUpload fu,
			String field) {
		rejectIfInvalidFileSize(errors, refDocLst, fu, field, PFX_DEFAULT, null, null);
	}


	public static void rejectIfInvalidFileSize(Errors errors, List<Document> refDocLst, FileUpload fu,
			String field, String errorCode, Object[] errorArgs, String defaultMessage) {
		CustomMultipartFile file = (fu.getFile() instanceof CustomMultipartFile) ? fu.getFile() : null;
		for (Document refDoc : refDocLst) {
			if (refDoc.getDocId() == fu.getDocId()) {
				if (file != null && file.getSize() > refDoc.getSize()) {
					if (BaseUtil.isEqualsCaseIgnore(PFX_DEFAULT, errorCode)) {
						int size = refDoc.getSize() / 1000;
						errors.rejectValue(field, MessageConstants.ERROR_DOC_SIZE, new Object[] { size + " KB" },
								null);
					} else {
						errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
					}
				}
				break;
			}
		}
	}


	public static void rejectIfInvalidContentType(Errors errors, List<Document> refDocLst, FileUpload fu,
			String field) {
		rejectIfInvalidContentType(errors, refDocLst, fu, field, PFX_DEFAULT, null, null);
	}


	public static void rejectIfInvalidContentType(Errors errors, List<Document> refDocLst, FileUpload fu,
			String field, String errorCode, Object[] errorArgs, String defaultMessage) {
		CustomMultipartFile file = (fu.getFile() instanceof CustomMultipartFile) ? fu.getFile() : null;
		for (Document refDoc : refDocLst) {
			if (refDoc.getDocId() == fu.getDocId() && file != null && !BaseUtil.isObjNull(file.getContentType())) {
				if (!(BaseUtil.getStrUpper(refDoc.getType())
						.contains(BaseUtil.getStrUpper(file.getContentType())))) {
					if (BaseUtil.isEqualsCaseIgnore(PFX_DEFAULT, errorCode)) {
						errors.rejectValue(field, MessageConstants.ERROR_DOC_CONTENT_TYPE, null, null);
					} else {
						errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
					}
				}
				break;
			}
		}
	}


	/**
	 *
	 * @param errors
	 * @param field
	 * @param errorCode
	 */
	public static void rejectIfInvalidEmailFormat(Errors errors, String field, String errorCode) {
		rejectIfInvalidEmailFormat(errors, field, errorCode, null, null);
	}


	public static void rejectIfInvalidEmailFormat(Errors errors, String field, String errorCode,
			String defaultMessage) {
		rejectIfInvalidEmailFormat(errors, field, errorCode, null, defaultMessage);
	}


	public static void rejectIfInvalidEmailFormat(Errors errors, String field, String errorCode, Object[] errorArgs) {
		rejectIfInvalidEmailFormat(errors, field, errorCode, errorArgs, null);
	}


	public static void rejectIfInvalidEmailFormat(Errors errors, String field, String errorCode, Object[] errorArgs,
			String defaultMessage) {
		Boolean isValid = Boolean.TRUE;
		Assert.notNull(errors, ASSERT_ERROR_NOT_NULL);
		Object value = errors.getFieldValue(field);
		if (StringUtils.hasText(value.toString())) {
			String mail = value.toString();

			// Set the email pattern string
			Pattern p = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");

			// Match the given string with the pattern
			Matcher m = p.matcher(mail);

			// check whether match is found
			boolean matchFound = m.matches();

			StringTokenizer st = new StringTokenizer(mail, ".");
			String lastToken = null;
			while (st.hasMoreTokens()) {
				lastToken = st.nextToken();
			}

			if (matchFound && lastToken != null && lastToken.length() >= 2
					&& mail.length() - 1 != lastToken.length()) {
				isValid = Boolean.TRUE;
			} else {
				isValid = Boolean.FALSE;
			}

			if (!isValid) {
				errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
			}
		}
	}


	public static void rejectDates(Errors errors, String field1, String field2, String errorCode) {
		rejectDates(errors, field1, field2, errorCode, null, null);
	}


	public static void rejectDates(Errors errors, String field1, String field2, String errorCode, Object[] errorArgs,
			String defaultMessage) {
		Object startDate = errors.getFieldValue(field1);
		Object endDate = errors.getFieldValue(field2);
		if (startDate != null && endDate != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.DT_YYYY_MM_DD_DASH);
				Date cvrtStartDate = sdf.parse(startDate.toString());
				Date cvrtEndDate = sdf.parse(endDate.toString());
				int value = DateUtil.compareDates(cvrtStartDate, cvrtEndDate);
				if (value == 1) {
					errors.rejectValue(field2, errorCode, errorArgs, defaultMessage);
				}
			} catch (Exception e) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			}
		}
	}


	/*
	 * Added for Validating the Date Ranges if greater than 1 year
	 */
	public static void rejectDateDifference(Errors errors, Date field1, Date field2, String fldName) {
		if (field1 != null && field2 != null) {
			try {
				int diffInDays = DateUtil.getDateDiffJodaTimeInDays(field1, field2);

				if (diffInDays > 365) {
					errors.rejectValue(fldName, MessageConstants.ERROR_DATE_DIFF,
							"Range of date selection must not be more than 1 year");
				}
			} catch (Exception e) {
				LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
			}
		}

	}


	public static void rejectIfPassportIsInvalid(Errors errors, String field1, String field2, String errorCode,
			int minMonths) {
		String startDate = String.valueOf(errors.getFieldValue(field1));
		String endDate = String.valueOf(errors.getFieldValue(field2));
		int validity = DateUtil.getDateDiffJodaTimeInMonths(startDate, endDate, BaseConstants.DT_YYYY_MM_DD_DASH);
		if (validity < minMonths) {
			errors.rejectValue(field2, errorCode, null, null);
		}
	}


	public static void rejectIfNotAdult(Errors errors, String field, String errorCode) {

		Assert.notNull(errors, ASSERT_ERROR_NOT_NULL);

		try {
			Date dob = DateUtil.getFormatDate(String.valueOf(errors.getFieldValue(field)),
					BaseConstants.DT_DD_MM_YYYY_SLASH);
			int diffYear = DateUtil.getDateDiffJodaTimeInYears(dob);
			if (diffYear < 18) {
				errors.rejectValue(field, errorCode, null, null);
			}
		} catch (ParseException e) {
			LOGGER.error(BaseConstants.LOG_EXCEPTION, e.getMessage());
		}

	}


	public static void rejectIfNumericIsZero(Errors errors, String field, String errorCode) {
		rejectIfNumericIsZero(errors, field, errorCode, null, null);
	}


	public static void rejectIfNumericIsZero(Errors errors, String field, String errorCode, Object[] errorArgs,
			String defaultMessage) {
		Assert.notNull(errors, ASSERT_ERROR_NOT_NULL);
		Integer value = Integer.valueOf(errors.getFieldValue(field).toString());
		if (value == 0) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}


	public static void rejectIfNumericIsGreater(Errors errors, String field, String errorCode, Integer comparator) {
		rejectIfNumericIsGreater(errors, field, errorCode, comparator, null, null);
	}


	public static void rejectIfNumericIsGreater(Errors errors, String field, String errorCode, Integer comparator,
			Object[] errorArgs, String defaultMessage) {
		Assert.notNull(errors, ASSERT_ERROR_NOT_NULL);
		Integer value = Integer.valueOf(errors.getFieldValue(field).toString());
		if (value > comparator) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}


	public static void rejectCheckBox(Errors errors, String field, String errorCode) {
		rejectIfNotCheckedCheckBox(errors, field, errorCode);
	}


	public static void rejectIfNotCheckedCheckBox(Errors errors, String field, String errorCode) {
		Object value = errors.getFieldValue(field);
		if (value.toString().equals("false")) {
			errors.rejectValue(field, errorCode);
		}
	}


	public static void rejectIfLengthIsInvalidRemoveSpaces(Errors errors, String field, String errorCode,
			Object[] errorArgs, String defaultMessage, int length) {
		Assert.notNull(errors, ASSERT_ERROR_NOT_NULL);
		Object value = errors.getFieldValue(field);
		if (StringUtils.hasText(value.toString())) {
			value = value.toString().replaceAll("[^\\w]", "");
			if (value.toString().length() < length) {
				errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
			}
		}
	}


	public static boolean validatePhoneNumber(String phoneNo) {
		// validate phone numbers of format "1234567890"
		if (phoneNo.matches("\\d{11}")) {
			return true;
		} else if (phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) {
			return true;
		} else if (phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) {
			return true;
		} else if (phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) {
			return true;
		} else {
			return false;
		}

	}


	public static void rejectIfInvalidAgeLessFormat(Errors errors, String field, String errorCode) {
		rejectIfInvalidAgeLessFormat(errors, field, errorCode, null, null);
	}


	public static void rejectIfInvalidAgeLessFormat(Errors errors, String field, String errorCode, Object[] errorArgs,
			String defaultMessage) {
		Boolean isValid = Boolean.TRUE;
		Assert.notNull(errors, ASSERT_ERROR_NOT_NULL);
		Object value = errors.getFieldValue(field);
		if (StringUtils.hasText(value.toString())) {
			String mail = value.toString();

			// Set the email pattern string
			Pattern p = Pattern.compile("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");

			// Match the given string with the pattern
			Matcher m = p.matcher(mail);

			// check whether match is found
			boolean matchFound = m.matches();

			StringTokenizer st = new StringTokenizer(mail, ".");
			String lastToken = null;
			while (st.hasMoreTokens()) {
				lastToken = st.nextToken();
			}

			if (lastToken != null && matchFound && lastToken.length() >= 2
					&& mail.length() - 1 != lastToken.length()) {
				isValid = Boolean.TRUE;
			} else {
				isValid = Boolean.FALSE;
			}

			if (!isValid) {
				errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
			}
		}
	}

}