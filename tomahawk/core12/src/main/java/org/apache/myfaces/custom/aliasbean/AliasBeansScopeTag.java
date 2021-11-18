/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.aliasbean;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Sylvain Vieujot (latest modification by $Author: lu4242 $)
 * @version $Revision: 664402 $ $Date: 2008-06-08 00:59:02 +0300 (Sun, 08 Jun 2008) $
 */
public class AliasBeansScopeTag extends UIComponentELTag
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