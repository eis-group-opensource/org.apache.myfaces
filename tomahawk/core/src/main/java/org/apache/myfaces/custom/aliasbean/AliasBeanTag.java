/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.aliasbean;

import javax.faces.component.UIComponent;
import javax.servlet.jsp.JspException;

import org.apache.myfaces.shared_tomahawk.taglib.UIComponentTagBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Sylvain Vieujot (latest modification by $Author: lu4242 $)
 * @version $Revision: 671015 $ $Date: 2008-06-24 07:22:49 +0300 (Tue, 24 Jun 2008) $
 */
public class AliasBeanTag extends UIComponentTagBase {

    private Log log = LogFactory.getLog(AliasBeanTag.class);

    private String _alias;
    private String _valueExpression;

    public void release() {
        super.release();

        _alias=null;
        _valueExpression=null;

    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);

        setValueBinding(component, "alias", _alias);
        setStringProperty(component, "value", _valueExpression);
    }

    public String getComponentType() {
        return AliasBean.COMPONENT_TYPE;
    }

    public String getRendererType() {
        return null;
    }

    public void setAlias(String alias){
        _alias = alias;
    }

    public void setValue(String valueExpression){
        _valueExpression = valueExpression;
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
}