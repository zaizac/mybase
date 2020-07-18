package com.be.util;

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

import com.util.BaseUtil;
import com.util.DateUtil;
import com.util.constants.BaseConstants;

public class ValidationUtil extends ValidationUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationUtil.class);
	
	public static void rejectIfObjectNull(Errors errors, List<?> obj, String errNum){
		if(BaseUtil.isListNull(obj)){
			errors.rejectValue("occupation",errNum);
		}
	}
	
	public static void rejectIfObjectNull(Errors errors, String field, Object obj, String errNum){
		if(BaseUtil.isObjNull(obj)){
			errors.rejectValue(field, errNum);
		}
	}

	public static void isValidPost(Errors errors, String field, String errorCode, String currAddress, String billAddress){
		isValidPost(errors, field, errorCode, null, null, currAddress, billAddress );
	}
	
	public static void isValidPost(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage, String currAddress, String billAddress){
		if (currAddress == null && billAddress.isEmpty())
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
	}
	
	public static void isNumeric(Errors errors, String field, String errorCode){
		isNumeric(errors, field, errorCode, null, null);
	}
	
	public static void isNumeric(Errors errors, String field, String errorCode, String defaultMessage){
		isNumeric(errors, field, errorCode, null, defaultMessage);
	}
	
	public static void isNumeric(Errors errors, String field, String errorCode, Object[] errorArgs){
		isNumeric(errors, field, errorCode, errorArgs, null);
	}
	
	public static void isNumeric(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage){
		String strValidChars = "0123456789.-";
		char strChar;		
		Object numeric = errors.getFieldValue(field);
		String str = numeric.toString();
		if (str.length() == 0) 
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);

		for(int i = 0; i < str.length(); i++)
		{
			strChar = str.charAt(i);
			
			if (strValidChars.indexOf(strChar) == -1)
				errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}
	
	public static void rejectSameTwoStrings(Errors errors, String field1, String field2, String errorCode){
		rejectSameTwoStrings(errors, field1, field2, errorCode, null, null);
	}
	
	public static void rejectSameTwoStrings(Errors errors, String field1, String field2, String errorCode, Object[] errorArgs){
		rejectSameTwoStrings(errors, field1, field2, errorCode, errorArgs, null);
	}
	
	public static void rejectSameTwoStrings(Errors errors, String field1, String field2, String errorCode, String defaultMessage){
		rejectSameTwoStrings(errors, field1, field2, errorCode, null, defaultMessage);
	}
	
	public static void rejectSameNumbers(Errors errors, String field, List<String> numbers, String errorCode){
		rejectSameNumbers(errors, field, numbers, errorCode, null, null);
	}
	
	public static void rejectSameNumbers(Errors errors, String field, List<String> numbers, String errorCode, Object[] errorArgs, String defaultMessage){
		  for (int i=0; i< numbers.size(); i++)
		  {
			  if(numbers.get(i) != ""){
				  for (int x=i+1; x< numbers.size(); x++)
				  {
					  if( numbers.get(i).equals( numbers.get(x))){
						  errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
						  break;
					  }
				  }
			  }
			
		  }
	}
	
	public static void rejectSameTwoStrings(Errors errors, String field1, String field2, String errorCode, Object[] errorArgs, String defaultMessage){
		Object value1 = errors.getFieldValue(field1);
		Object value2 = errors.getFieldValue(field2);
		if (StringUtils.hasText(value1.toString()) && StringUtils.hasText(value2.toString())) {
			if(value1.toString().equals(value2.toString())){
				errors.rejectValue(field2, errorCode, errorArgs, defaultMessage);
			}
		}
	}
	
	public static void compareTwoStrings(Errors errors, String field1, String field2, String errorCode){
		compareTwoStrings(errors, field1, field2, errorCode, null, null);
	}
	
	public static void compareTwoStrings(Errors errors, String field1, String field2, String errorCode, Object[] errorArgs){
		compareTwoStrings(errors, field1, field2, errorCode, errorArgs, null);
	}
	
	public static void compareTwoStrings(Errors errors, String field1, String field2, String errorCode, String defaultMessage){
		compareTwoStrings(errors, field1, field2, errorCode, null, defaultMessage);
	}
	
	public static void compareTwoStrings(Errors errors, String field1, String field2, String errorCode, Object[] errorArgs, String defaultMessage){
		Assert.notNull(errors, "Errors object must not be null");
		Object value1 = errors.getFieldValue(field1);
		Object value2 = errors.getFieldValue(field2);
		if (StringUtils.hasText(value1.toString()) && StringUtils.hasText(value2.toString())) {
			if(!value1.toString().equals(value2.toString())){
				errors.rejectValue(field1, errorCode, errorArgs, defaultMessage);
			}
		}
	}
	public static void rejectIfLimitIsInvalid(Errors errors, String field, String errorCode, int length){
		rejectIfLimitIsInvalid(errors, field, errorCode, null, null, length);
	}
	public static void rejectIfLimitIsInvalid(Errors errors, String field, String errorCode, Object[] errorArgs, int length){
		rejectIfLimitIsInvalid(errors, field, errorCode, errorArgs, null, length);
	}
	
	

	public static void rejectIfAmountIsInvalid(Errors errors, String field, String errorCode){
		rejectIfAmountIsInvalid(errors, field, errorCode, null, null);
	}
	
	/**
	 * 
	 * @param errors
	 * @param field
	 * @param errorCode
	 * @param length
	 */
	public static void rejectIfLengthIsInvalid(Errors errors, String field, String errorCode, int length){
		rejectIfLengthIsInvalid(errors, field, errorCode, null, null, length);
	}
	
	/**
	 * 
	 * @param errors
	 * @param field
	 * @param errorCode
	 * @param errorArgs
	 * @param length
	 */
	public static void rejectIfLengthIsInvalid(Errors errors, String field, String errorCode, Object[] errorArgs, int length){
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
	public static void rejectIfLengthIsInvalid(Errors errors, String field, String errorCode, String defaultMessage, int length){
		rejectIfLengthIsInvalid(errors, field, errorCode, null, defaultMessage, length);
	}
	
	/**
	 * 
	 * @param errors
	 * @param field
	 * @param errorCode
	 * @param errorArgs
	 * @param defaultMessage
	 */
	public static void rejectIfAmountIsInvalid(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage){
		Assert.notNull(errors, "Errors object must not be null");
		Object amount = errors.getFieldValue(field);
		if(Double.parseDouble(amount.toString()) < 1000) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}
	
	public static void rejectIfLimitIsInvalid(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage, int length){
		Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		if (!BaseUtil.isObjNull(value)) {
			if(value.toString().length() > length){
				errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
			}
		}
	}
	
	public static void rejectIfLengthIsInvalid(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage, int length){
		Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		if (!BaseUtil.isObjNull(value)) {
			if(value.toString().length() < length){
				errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
			}
		}
	}
	



	/**
	 * 
	 * @param errors
	 * @param field
	 * @param errorCode
	 */
	public static void rejectIfInvalidEmailFormat(Errors errors, String field, String errorCode){
		rejectIfInvalidEmailFormat(errors, field, errorCode, null, null);
	}
	
	public static void rejectIfInvalidEmailFormat(Errors errors, String field, String errorCode, String defaultMessage){
		rejectIfInvalidEmailFormat(errors, field, errorCode, null, defaultMessage);
	}
	
	public static void rejectIfInvalidEmailFormat(Errors errors, String field, String errorCode, Object[] errorArgs){
		rejectIfInvalidEmailFormat(errors, field, errorCode, errorArgs, null);
	}
	
	public static void rejectIfInvalidEmailFormat(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage){
		Boolean isValid = Boolean.TRUE;
		Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		if (!BaseUtil.isObjNull(value)) {
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

			   if (matchFound && lastToken.length() >= 2 && mail.length() - 1 != lastToken.length()) {
				   isValid = Boolean.TRUE;
			   }else {
				   isValid = Boolean.FALSE;
			   }
			 
			 if(!isValid){
				 errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
			 }
		}
	}
	
	public static void rejectDates(Errors errors, String field1, String field2, String errorCode) {
		rejectDates(errors, field1, field2, errorCode, null, null);
	}
	
	public static void rejectDates(Errors errors, String field1, String field2, String errorCode, Object[] errorArgs, String defaultMessage) {
		Object startDate = errors.getFieldValue(field1);
		Object endDate = errors.getFieldValue(field2);
		LOGGER.info("startDate>>>" + startDate);
		LOGGER.info("endDate>>>" + endDate);
		if(startDate != null && endDate != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(BaseConstants.DT_YYYY_MM_DD_DASH);
				Date cvrtStartDate = sdf.parse(startDate.toString());
				Date cvrtEndDate = sdf.parse(endDate.toString());
				LOGGER.info("cvrtStartDate>>>" + cvrtStartDate);
				LOGGER.info("cvrtEndDate>>>" + cvrtEndDate);
				int value = DateUtil.compareDates(cvrtStartDate, cvrtEndDate);
				if(value == 1) {
					LOGGER.info("value>>>" + value);
					errors.rejectValue(field2, errorCode, errorArgs, defaultMessage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void rejectIfPassportIsInvalid(Errors errors, String field1, String field2, String errorCode, int minMonths) {
		String startDate = String.valueOf(errors.getFieldValue(field1));
		String endDate = String.valueOf(errors.getFieldValue(field2));
		int validity = DateUtil.getDateDiffJodaTimeInMonths(startDate, endDate, BaseConstants.DT_YYYY_MM_DD_DASH);
		if(validity < minMonths) {
			errors.rejectValue(field2, errorCode, null, null);
		}
	}
	
	public static void rejectIfNumericIsZero(Errors errors, String field, String errorCode) {
		rejectIfNumericIsZero(errors, field, errorCode, null, null);
	}
	
	public static void rejectIfNumericIsZero(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage) {
		Assert.notNull(errors, "Errors object must not be null");
		LOGGER.info("" + errors.getFieldValue(field));
		Integer value = Integer.valueOf(errors.getFieldValue(field).toString());
		if(value == 0) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}
	
	public static void rejectIfNumericIsNull(Errors errors, String field, String errorCode) {
		rejectIfNumericIsNull(errors, field, errorCode, null, null);
	}
	
	public static void rejectIfNumericIsNull(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage) {
		Assert.notNull(errors, "Errors object must not be null");
		LOGGER.info("" + errors.getFieldValue(field));
		Integer value = Integer.valueOf(errors.getFieldValue(field).toString());
		if(BaseUtil.isObjNull(value)) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}
	
	public static void rejectIfNumericIsNullZero(Errors errors, String field, String errorCode) {
		rejectIfNumericIsNullZero(errors, field, errorCode, null, null);
	}
	
	public static void rejectIfNumericIsNullZero(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage) {
		Assert.notNull(errors, "Errors object must not be null");
		LOGGER.info("" + errors.getFieldValue(field));
		Integer value = Integer.valueOf(errors.getFieldValue(field).toString());
		if(value == 0 || BaseUtil.isObjNull(value)) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}
	
	public static void rejectIfNumericIsGreater(Errors errors, String field, String errorCode, Integer comparator) {
		rejectIfNumericIsGreater(errors, field, errorCode, comparator, null, null);
	}
	
	public static void rejectIfNumericIsGreater(Errors errors, String field, String errorCode, Integer comparator, Object[] errorArgs, String defaultMessage){
		Assert.notNull(errors, "Errors object must not be null");
		LOGGER.info("" + errors.getFieldValue(field));
		Integer value = Integer.valueOf(errors.getFieldValue(field).toString());
		if(value > comparator) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}
	
	public static void rejectCheckBox(Errors errors, String field, String errorCode){
		rejectIfNotCheckedCheckBox(errors, field, errorCode);
	}
	
	public static void rejectIfNotCheckedCheckBox(Errors errors, String field, String errorCode) {
		Object value = errors.getFieldValue(field);
		if(value.toString().equals("false"))errors.rejectValue(field, errorCode);
	}
	
	public static void rejectIfLengthIsInvalidRemoveSpaces(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage, int length){
		Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		if (!BaseUtil.isObjNull(value)) {
			value = value.toString().replaceAll("[^\\w]", ""); 
			if(value.toString().length() < length){
				errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
			}
		}
	}

}
