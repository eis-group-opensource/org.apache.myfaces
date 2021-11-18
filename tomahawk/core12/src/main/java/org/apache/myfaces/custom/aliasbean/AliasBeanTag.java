/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.aliasbean;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Sylvain Vieujot (latest modification by $Author: lu4242 $)
 * @version $Revision: 664402 $ $Date: 2008-06-08 00:59:02 +0300 (Sun, 08 Jun 2008) $
 */
public class AliasBeanTag extends UIComponentELTag
{

    private Log log = LogFactory.getLog(AliasBeanTag.class);
    /**
     * Construct an instance of the AliasBeanELTag.
     */
    public AliasBeanTag()
    {
    }
    
    public int doStartTag() throws JspException
    {
        int retVal= super.doStartTag();

        UIComponent comp = getComponentInstance();

        if(comp instanceof AliasBean)
        {
            ((AliasBean) comp).makeAlias(getFacesContext());
        }
        else
        {
            log.warn("associated component is no aliasBean");
        }

        return retVal;
    }

    public int doEndTag() throws JspException
    {
        UIComponent comp = getComponentInstance();

        if(comp instanceof AliasBean)
        {
            ((AliasBean) comp).removeAlias(getFacesContext());
        }
        else
        {
            log.warn("associated component is no aliasBean");
        }

        return super.doEndTag();
    }
    
    @Override
    public String getComponentType()
    {
        return "org.apache.myfaces.AliasBean";
    }

    public String getRendererType()
    {
        return null;
    }

    private ValueExpression _alias;

    public void setAlias(ValueExpression alias)
    {
        _alias = alias;
    }

    private ValueExpression _value;

    public void setValue(ValueExpression value)
    {
        _value = value;
    }

    @Override
    protected void setProperties(UIComponent component)
    {
        if (!(component instanceof AliasBean))
        {
            throw new IllegalArgumentException("Component "
                    + component.getClass().getName() + " is no AliasBean");
        }
        AliasBean comp = (AliasBean) component;

        super.setProperties(component);

        if (_alias != null)
        {
            comp.setValueExpression("alias", _alias);
        }
        if (_value != null)
        {
            comp.setValueExpression("value", _value);
        }
    }

    @Override
    public void release()
    {
        super.release();
        _alias = null;
        _value = null;
    }
}
