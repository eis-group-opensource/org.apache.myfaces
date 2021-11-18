/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspAttribute;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspTag;
import org.apache.myfaces.shared_impl.renderkit.JSFAttr;
import org.apache.myfaces.shared_impl.taglib.UIComponentELTagBase;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;

/**
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 693358 $ $Date: 2008-09-09 06:54:29 +0300 (Tue, 09 Sep 2008) $
 */
@JSFJspTag(name="f:verbatim",bodyContent="JSP")
public class VerbatimTag
        extends UIComponentELTagBase
{
    //private static final Log log = LogFactory.getLog(VerbatimTag.class);

    public String getComponentType()
    {
        return "javax.faces.Output";
    }

    public String getRendererType()
    {
        return "javax.faces.Text";
    }

    // HtmlOutputText attributes
    private ValueExpression _escape;
    private ValueExpression _rendered;

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setBooleanProperty(component, JSFAttr.ESCAPE_ATTR, _escape, Boolean.FALSE);
        setBooleanProperty(component, JSFAttr.RENDERED, _rendered, Boolean.TRUE);

        //No need to save component state
        component.setTransient(true);
    }

    /**
     * If true, generated markup is escaped.  Default:  false.
     */
    @JSFJspAttribute(
            rtexprvalue=true,
            className="java.lang.Boolean")
    public void setEscape(ValueExpression escape)
    {
        _escape = escape;
    }

    /**
     * Flag indicating whether or not this component should be rendered
     * (during Render Response Phase), or processed on any subsequent
     * form submit.  The default value for this property is true.
     */
    @JSFJspAttribute(
            rtexprvalue=true,
            className="java.lang.Boolean")
    public void setRendered(ValueExpression rendered)
    {
        _rendered = rendered;
    }
    
    public int doAfterBody() throws JspException
    {
        BodyContent bodyContent = getBodyContent();
        if (bodyContent != null)
        {
            UIOutput component = (UIOutput)getComponentInstance();
            component.setValue(bodyContent.getString());
            bodyContent.clearBody();
        }
        return super.getDoAfterBodyValue();
    }
}
