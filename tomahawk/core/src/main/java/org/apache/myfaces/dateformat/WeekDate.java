/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.dateformat;


/**
 * A simple class that holds a date represented as a (year, week) pair
 * rather than the more traditional (year, month, day).
 * 
 * @since 1.1.7
 */
public class WeekDate
{
    private int year, week;

    public WeekDate(int y, int w)
    {
        year = y;
        week = w;
    }

    public int getYear() 
    {
        return year;
    }
    
    public int getWeek()
    {
        return week;
    }
}
