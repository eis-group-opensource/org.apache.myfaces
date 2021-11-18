/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.component;

import javax.faces.component.UIComponent;

/**
 * NewsPaperTable interface for allowing a UIData component to provide NewsPaperTable attributes.
 *
 * @author Mike Kienenberger (latest modification by $Author: skitching $)
 */

public interface NewspaperTable {
    public static final String NEWSPAPER_HORIZONTAL_ORIENTATION = "horizontal";
    public int getNewspaperColumns();
    public UIComponent getSpacer();
    public String getNewspaperOrientation();

}
