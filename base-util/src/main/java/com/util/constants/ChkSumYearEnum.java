/**
 * Copyright 2019. 
 */
package com.util.constants;


/**
 * The Enum ChkSumYearEnum.
 *
 * @author Mary Jane Buenaventura
 * @since Nov 1, 2017
 */
public enum ChkSumYearEnum {

	/** The year 2015. */
	YEAR_2015("C"),
	
	/** The year 2016. */
	YEAR_2016("S"),
	
	/** The year 2017. */
	YEAR_2017("2"),
	
	/** The year 2018. */
	YEAR_2018("6"),
	
	/** The year 2019. */
	YEAR_2019("0"),
	
	/** The year 2020. */
	YEAR_2020("E"),
	
	/** The year 2021. */
	YEAR_2021("T"),
	
	/** The year 2022. */
	YEAR_2022("Y"),
	
	/** The year 2023. */
	YEAR_2023("B"),
	
	/** The year 2024. */
	YEAR_2024("J"),
	
	/** The year 2025. */
	YEAR_2025("4"),
	
	/** The year 2026. */
	YEAR_2026("G"),
	
	/** The year 2027. */
	YEAR_2027("W"),
	
	/** The year 2028. */
	YEAR_2028("X"),
	
	/** The year 2029. */
	YEAR_2029("K"),
	
	/** The year 2030. */
	YEAR_2030("7"),
	
	/** The year 2031. */
	YEAR_2031("Q"),
	
	/** The year 2032. */
	YEAR_2032("3"),
	
	/** The year 2033. */
	YEAR_2033("1"),
	
	/** The year 2034. */
	YEAR_2034("U"),
	
	/** The year 2035. */
	YEAR_2035("I"),
	
	/** The year 2036. */
	YEAR_2036("F"),
	
	/** The year 2037. */
	YEAR_2037("5"),
	
	/** The year 2038. */
	YEAR_2038("P"),
	
	/** The year 2039. */
	YEAR_2039("O"),
	
	/** The year 2040. */
	YEAR_2040("H"),
	
	/** The year 2041. */
	YEAR_2041("9"),
	
	/** The year 2042. */
	YEAR_2042("V"),
	
	/** The year 2043. */
	YEAR_2043("Z"),
	
	/** The year 2044. */
	YEAR_2044("8"),
	
	/** The year 2045. */
	YEAR_2045("R"),
	
	/** The year 2046. */
	YEAR_2046("L"),
	
	/** The year 2047. */
	YEAR_2047("N"),
	
	/** The year 2048. */
	YEAR_2048("A"),
	
	/** The year 2049. */
	YEAR_2049("D"),
	
	/** The year 2050. */
	YEAR_2050("M");

	/** The seq. */
	private final String seq;

	/** The Constant YEAR_PFX. */
	private static final String YEAR_PFX = "YEAR_";


	/**
	 * Instantiates a new chk sum year enum.
	 *
	 * @param seq the seq
	 */
	private ChkSumYearEnum(String seq) {
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
		for (ChkSumYearEnum e : ChkSumYearEnum.values()) {
			if (e.seq.equalsIgnoreCase(seq)) {
				return (e.name()).replace(YEAR_PFX, "");
			}
		}
		return null;
	}


	/**
	 * Gets the seq by year.
	 *
	 * @param day the day
	 * @return the seq by year
	 */
	public static String getSeqByYear(int day) {
		return ChkSumYearEnum.valueOf(YEAR_PFX + day).getSeq();
	}

}