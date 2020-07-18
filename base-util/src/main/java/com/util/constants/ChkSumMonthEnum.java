/**
 * Copyright 2019. 
 */
package com.util.constants;


/**
 * The Enum ChkSumMonthEnum.
 *
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2017
 */
public enum ChkSumMonthEnum {

	/** The month 1. */
	MONTH_1("K"),
	
	/** The month 2. */
	MONTH_2("W"),
	
	/** The month 3. */
	MONTH_3("U"),
	
	/** The month 4. */
	MONTH_4("Z"),
	
	/** The month 5. */
	MONTH_5("X"),
	
	/** The month 6. */
	MONTH_6("T"),
	
	/** The month 7. */
	MONTH_7("S"),
	
	/** The month 8. */
	MONTH_8("B"),
	
	/** The month 9. */
	MONTH_9("Q"),
	
	/** The month 10. */
	MONTH_10("C"),
	
	/** The month 11. */
	MONTH_11("A"),
	
	/** The month 12. */
	MONTH_12("R");

	/** The seq. */
	private final String seq;

	/** The Constant MONTH_PFX. */
	private static final String MONTH_PFX = "MONTH_";


	/**
	 * Instantiates a new chk sum month enum.
	 *
	 * @param seq the seq
	 */
	private ChkSumMonthEnum(String seq) {
		this.seq = seq;
	}


	/**
	 * Gets the seq.
	 *
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}


	/**
	 * Gets the enum by seq.
	 *
	 * @param seq the seq
	 * @return the enum by seq
	 */
	public static String getEnumBySeq(String seq) {
		for (ChkSumMonthEnum e : ChkSumMonthEnum.values()) {
			if (e.seq.equalsIgnoreCase(seq)) {
				return (e.name()).replace(MONTH_PFX, "");
			}
		}
		return null;
	}


	/**
	 * Gets the seq by month.
	 *
	 * @param day the day
	 * @return the seq by month
	 */
	public static String getSeqByMonth(int day) {
		return ChkSumMonthEnum.valueOf(MONTH_PFX + day).getSeq();
	}
}