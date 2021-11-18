/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.test.utils;

import javax.faces.context.FacesContext;

import org.apache.shale.test.mock.MockViewHandler;

/**
 * This class is a temporary workaround for test cases
 * that need a MockViewHandler that doesn't throw an
 * UnsupportedOperationException inside writeState().
 * This is not needed anymore once the fix for
 * SHALE-468 is released.
 */
public class MockTestViewHandler extends MockViewHandler
{
    public void writeState(FacesContext context)
    {
        //do nothing
    }
}
