/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.html.ext;

import org.apache.myfaces.component.html.ext.HtmlInputHidden;
import org.apache.myfaces.shared_tomahawk.taglib.html.HtmlInputHiddenTagBase;

/**
 * @author Manfred Geiler (latest modification by $Author: mkienenb $)
 * @author Martin Marinschek
 * @version $Revision: 523868 $ $Date: 2007-03-30 02:02:29 +0300 (Fri, 30 Mar 2007) $
 */
public class HtmlInputHiddenTag
        extends HtmlInputHiddenTagBase
{
    public String getComponentType()
    {
        return HtmlInputHidden.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return HtmlInputHidden.DEFAULT_RENDERER_TYPE;
    }
}
