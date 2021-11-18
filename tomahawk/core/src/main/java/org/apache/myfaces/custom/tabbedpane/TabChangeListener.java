/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tabbedpane;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesListener;

/**
 * @author Manfred Geiler (latest modification by $Author: grantsmith $)
 * @version $Revision: 472638 $ $Date: 2006-11-08 22:54:13 +0200 (Wed, 08 Nov 2006) $
 */
public interface TabChangeListener
        extends FacesListener
{
    public abstract void processTabChange(TabChangeEvent tabChangeEvent)
        throws AbortProcessingException;
}
