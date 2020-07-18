/**
 * Copyright 2019. 
 */
package com.util.constants;


/**
 * The Enum ChkSumDayEnum.
 *
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2017
 */
public enum ChkSumDayEnum {

	/** The day 1. */
	DAY_1("0"),
	
	/** The day 2. */
	DAY_2("F"),
	
	/** The day 3. */
	DAY_3("J"),
	
	/** The day 4. */
	DAY_4("5"),
	
	/** The day 5. */
	DAY_5("G"),
	
	/** The day 6. */
	DAY_6("O"),
	
	/** The day 7. */
	DAY_7("Z"),
	
	/** The day 8. */
	DAY_8("W"),
	
	/** The day 9. */
	DAY_9("4"),
	
	/** The day 10. */
	DAY_10("8"),
	
	/** The day 11. */
	DAY_11("7"),
	
	/** The day 12. */
	DAY_12("3"),
	
	/** The day 13. */
	DAY_13("9"),
	
	/** The day 14. */
	DAY_14("Y"),
	
	/** The day 15. */
	DAY_15("V"),
	
	/** The day 16. */
	DAY_16("Q"),
	
	/** The day 17. */
	DAY_17("C"),
	
	/** The day 18. */
	DAY_18("K"),
	
	/** The day 19. */
	DAY_19("S"),
	
	/** The day 20. */
	DAY_20("L"),
	
	/** The day 21. */
	DAY_21("2"),
	
	/** The day 22. */
	DAY_22("H"),
	
	/** The day 23. */
	DAY_23("M"),
	
	/** The day 24. */
	DAY_24("P"),
	
	/** The day 25. */
	DAY_25("B"),
	
	/** The day 26. */
	DAY_26("6"),
	
	/** The day 27. */
	DAY_27("U"),
	
	/** The day 28. */
	DAY_28("X"),
	
	/** The day 29. */
	DAY_29("A"),
	
	/** The day 30. */
	DAY_30("E"),
	
	/** The day 31. */
	DAY_31("D");

	/** The seq. */
	private final String seq;

	/** The Constant DAY_PFX. */
	private static final String DAY_PFX = "DAY_";


	/**
	 * Instantiates a new chk sum day enum.
	 *
	 * @param seq the seq
	 */
	private ChkSumDayEnum(String seq) {
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
		for (ChkSumDayEnum e : ChkSumDayEnum.values()) {
			if (e.seq.equalsIgnoreCase(seq)) {
				return (e.name()).replace(DAY_PFX, "");
			}
		}
		return null;
	}


	/**
	 * Gets the seq by day.
	 *
	 * @param day the day
	 * @return the seq by day
	 */
	public static String getSeqByDay(int day) {
		return ChkSumDayEnum.valueOf(DAY_PFX + day).getSeq();
	}

}