/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.taglib.core;

import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.JspException;
import javax.faces.webapp.UIComponentClassicTagBase;
import javax.faces.webapp.UIComponentELTag;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.el.ValueExpression;
import javax.el.ELContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspAttribute;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFJspTag;

/**
 * This tag associates an attribute with the nearest parent
 * UIComponent.
 * <p>
 * When the value is not an EL expression, this tag has the same effect
 * as calling component.getAttributes.put(name, value). When the attribute
 * name specified matches a standard property of the component, that
 * property is set. However it is also valid to assign attributes
 * to components using any arbitrary name; the component itself won't
 * make any use of these but other objects such as custom renderers,
 * validators or action listeners can later retrieve the attribute
 * from the component by name.
 * </p>
 * <p>
 * When the value is an EL expression, this tag has the same effect
 * as calling component.setValueBinding. A call to method
 * component.getAttributes().get(name) will then cause that
 * expression to be evaluated and the result of the expression is
 * returned, not the original EL expression string.
 * </p>
 * <p>
 * See the javadoc for UIComponent.getAttributes for more details.
 * </p>
 * <p>
 * Unless otherwise specified, all attributes accept static values
 * or EL expressions.
 * </p>
 * 
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @author Bruno Aranda (JSR-252)
 * @version $Revision: 693358 $ $Date: 2008-09-09 06:54:29 +0300 (Tue, 09 Sep 2008) $
 */
@JSFJspTag(
        name="f:attribute",
        bodyContent="empty")
public class AttributeTag
        extends TagSupport
{
    private static final long serialVersionUID = 31476300171678632L;
    private ValueExpression _nameExpression;
    private ValueExpression _valueExpression;

    /**
     * The name of the attribute.
     * 
     * @param nameExpression
     */
    @JSFJspAttribute(
            rtexprvalue=true,
            className="java.lang.String")
    public void setName(ValueExpression nameExpression)
    {
        _nameExpression = nameExpression;
    }

    /**
     * The attribute's value.
     * 
     * @param valueExpression
     */
    @JSFJspAttribute(
            rtexprvalue=true,
            className="java.lang.Object")
    public void setValue(ValueExpression valueExpression)
    {
        _valueExpression = valueExpression;
    }


    public int doStartTag() throws JspException
    {
        UIComponentClassicTagBase componentTag = UIComponentELTag.getParentUIComponentClassicTagBase(pageContext);
        if (componentTag == null)
        {
            throw new JspException("no parent UIComponentTag found");
        }
        UIComponent component = componentTag.getComponentInstance();
        if (component == null)
        {
            throw new JspException("parent UIComponentTag has no UIComponent");
        }

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();

        String name = null;
        Object value = null;
        boolean isLiteral = false;

        if (_nameExpression != null)
        {
             name = (String) _nameExpression.getValue(elContext);
        }

        if (_valueExpression != null)
        {
            isLiteral = _valueExpression.isLiteralText();
            value = _valueExpression.getValue(elContext);
        }

        if (name != null)
        {
            if (component.getAttributes().get(name) == null)
            {
                if (isLiteral)
                {
                   component.getAttributes().put(name, value);
                }
                else
                {
                    component.setValueExpression(name, _valueExpression);
                }
            }
        }

        return SKIP_BODY;
    }

    /**
     * @deprecated
     */
    public void release()
    {
        super.release();
        _nameExpression = null;
        _valueExpression = null;
    }

}
