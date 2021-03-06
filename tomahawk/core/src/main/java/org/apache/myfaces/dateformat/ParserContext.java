/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.dateformat;

/**
 * A simple object that contains the current parsing state when parsing
 * a string into a date.
 * <p>
 * This encapsulates all the properties of a SimpleDateFormatter which
 * are modified during the parsing of a specific input string.
 * 
 * @since 1.1.7
 */
public class ParserContext
{
    /**
     * Set during various string-parsing operations to indicate the
     * offset of the next unparsed character.  
     */
    int newIndex;

    /**
     * Set during string-parsing operations if a parsing error occurred.
     * Normally, an error status is also returned from the method.
     */ 
    boolean invalid = false;

    public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public int getDay() {
		return day;
	}

	/**
     * Controls how "weekYear" and "weekOfWeekYear" map to ymd dates.
     * <p>
     * Values are 0=sunday, 1=monday, 6=saturday (the java.util.Date
     * return values for getDay). Note that java.util.Calendar uses
     * 1=sunday, 2=monday, 7=saturday.
     * <p>
     * This value is a mandatory parameter to the constructor of 
     * this class. Normally, callers will pass 1 (the ISO standard).
     */
    int firstDayOfWeek;

    /**
     * Set to true if the input string had a year specifier of less
     * than 4 digits, meaning we have to guess the century.
     */
    boolean ambiguousYear;

    /**
     * Set to true if the input string had a weekYear specifier of less
     * than 4 digits, meaning we have to guess the century.
     */
    boolean ambiguousWeekYear;

    // --------------------------------------------------
    // standard properties parsed out of the input string
    // --------------------------------------------------
    
    /**
     * Year is relative to 0AD, unless ambiguousYear is set.
     */
    int year;

    /** Month is in range 0..11 */
    int month;

    /** Day is in range 1..31 */
    int day = 1;

    /** mon=1, sun=7 */
    int dayOfWeek;

    /** The hour value used for formatter "H", in range 00-23. */
    int hour;
    
    /**
     * The hour value used for "h" formatter; in range 1..12.
     * <ul>
     * <li>00:00 is 12:00 am (this field is 12)
     * <li>01:00 is 01:00 am (this field is 1)
     * <li>11:59 is 11:59 am (this field is 11)
     * <li>12:00 is 12:00 pm (this field is 12)
     * <li>13:00 is 01:00 pm (this field is 1)
     * <li>23:59 is 11:59 pm (this field is 11)
     * </ul>
     */
    int hourAmpm;

    /** minutes in range 0-59 */
    int min;
    
    /** seconds in range 0-59 */
    int sec;
    
    /** 0 = am, 1 = pm (index into ampm strings in symbols class) */
    int ampm;

    /**
     * The year in which the weekOfWeekYear value lies.
     * Note that date yyyy-01-01 may be week 5n of the previous year.
     */
    int weekYear;
    
    /** The week number (1..53). */
    int weekOfWeekYear;

    public ParserContext(int dow) {
        firstDayOfWeek = dow;
    }
}
