/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package org.apache.myfaces.renderkit.html.util;

/**
 * @author Martin Marinschek
 * @version $Revision: 472792 $ $Date: 2006-11-09 08:34:47 +0200 (Thu, 09 Nov 2006) $
 *          <p/>
 *          $Log: $
 */
public interface CallbackListener
{
    void openedStartTag(int charIndex, int tagIdentifier);
    void closedStartTag(int charIndex, int tagIdentifier);
    void openedEndTag(int charIndex, int tagIdentifier);
    void closedEndTag(int charIndex, int tagIdentifier);
    void attribute(int charIndex, int tagIdentifier, String key, String value);
}
