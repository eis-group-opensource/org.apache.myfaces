/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.aliasbean;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.faces.component.UIComponent;

/**
 * @author Sylvain Vieujot (latest modification by $Author: grantsmith $)
 * @version $Revision: 472630 $ $Date: 2006-11-08 22:40:03 +0200 (Wed, 08 Nov 2006) $
 */
public class AliasBeansScopeTag extends UIComponentTagBase
{
    private Log log = LogFactory.getLog(AliasBeansScopeTag.class);

    public String getComponentType()
    {
        return AliasBeansScope.COMPONENT_TYPE;
    }

    public String getRendererType()
    {
        return null;
    }


    public int doStartTag() throws JspException
    {
        int retVal = super.doStartTag();

        UIComponent comp = getComponentInstance();

        if(comp instanceof AliasBeansScope)
        {
            ((AliasBeansScope) comp).makeAliases(getFacesContext());
        }
        else
        {
            log.warn("associated component is no aliasBeansScope");
        }

        return retVal;
    }

    public int doEndTag() throws JspException
    {
        UIComponent comp = getComponentInstance();

        if(comp instanceof AliasBeansScope)
        {
            ((AliasBeansScope) comp).removeAliases(getFacesContext());
        }
        else
        {
            log.warn("associated component is no aliasBeansScope");
        }

        return super.doEndTag();
    }

}