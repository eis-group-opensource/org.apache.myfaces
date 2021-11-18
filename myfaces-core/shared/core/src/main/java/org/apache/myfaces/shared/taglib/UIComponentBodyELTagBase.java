/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.taglib;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import java.io.IOException;
import java.io.Reader;

/**
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 673827 $ $Date: 2008-07-04 00:46:23 +0300 (Pn, 04 Lie 2008) $
 */
public abstract class UIComponentBodyELTagBase extends UIComponentELTag
{
    private static final Log log = LogFactory.getLog(UIComponentBodyTagBase.class);

    public int doEndTag() throws JspException
    {
        if (log.isWarnEnabled())
        {
            UIComponent component = getComponentInstance();
            if (component != null &&
                component.getRendersChildren() &&
                !isBodyContentEmpty())
            {
                log.warn("Component with id '" + component.getClientId(getFacesContext()) +
                         "' (" + getClass().getName() +
                         " tag) and path : "+org.apache.myfaces.shared.renderkit.RendererUtils.getPathToComponent(component)+"renders it's children, but has embedded JSP or HTML code. Use the <f:verbatim> tag for nested HTML. For comments use <%/* */%> style JSP comments instead of <!-- --> style HTML comments." +
                         "\n BodyContent:\n" + getBodyContent().getString().trim());
            }
        }
        return super.doEndTag();
    }

    /**
     * TODO: Ignore <!-- --> comments
     */
    private boolean isBodyContentEmpty()
    {
        BodyContent bodyContent = getBodyContent();
        if (bodyContent == null)
        {
            return true;
        }
        try
        {
            Reader reader = bodyContent.getReader();
            int c;
            while ((c = reader.read()) != -1)
            {
                if (!Character.isWhitespace((char)c))
                {
                    return false;
                }
            }
            return true;
        }
        catch (IOException e)
        {
            log.error("Error inspecting BodyContent", e);
            return false;
        }
    }

    //-------- rest is identical to UIComponentTagBase ------------------

    //UIComponent attributes
    private ValueExpression _forceId;
    private ValueExpression _forceIdIndex;
    private static final Boolean DEFAULT_FORCE_ID_INDEX_VALUE = Boolean.TRUE;

    //Special UIComponent attributes (ValueHolder, ConvertibleValueHolder)
    private ValueExpression _value;
    private ValueExpression _converter;
    //attributes id, rendered and binding are handled by UIComponentTag

    protected void setProperties(UIComponent component)
    {
        super.setProperties(component);

        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.FORCE_ID_ATTR, _forceId);
        setBooleanProperty(component, org.apache.myfaces.shared.renderkit.JSFAttr.FORCE_ID_INDEX_ATTR, _forceIdIndex, DEFAULT_FORCE_ID_INDEX_VALUE);

        //rendererType already handled by UIComponentTag

        setValueProperty(component, _value);
        setConverterProperty(component, _converter);
    }

    /**
     * Sets the forceId attribute of the tag.  NOTE: Not every tag that extends this class will
     * actually make use of this attribute.  Check the TLD to see which components actually
     * implement it.
     *
     * @param aForceId The value of the forceId attribute.
     */
    public void setForceId(ValueExpression aForceId)
    {
        _forceId = aForceId;
    }

    /**
     * Sets the forceIdIndex attribute of the tag.  NOTE: Not every tag that extends this class will
     * actually make use of this attribute.  Check the TLD to see which components actually implement it.
     *
     * @param aForceIdIndex The value of the forceIdIndex attribute.
     */
    public void setForceIdIndex(ValueExpression aForceIdIndex)
    {
        _forceIdIndex = aForceIdIndex;
    }

    public void setValue(ValueExpression value)
    {
        _value = value;
    }

    public void setConverter(ValueExpression converter)
    {
        _converter = converter;
    }



    // sub class helpers
    
    protected void setIntegerProperty(UIComponent component, String propName, ValueExpression value)
    {
        UIComponentELTagUtils.setIntegerProperty(component, propName, value);
    }
    
    @Deprecated
    protected void setIntegerProperty(UIComponent component, String propName, String value)
    {
        UIComponentTagUtils.setIntegerProperty(getFacesContext(), component, propName, value);
    }
    
    protected void setStringProperty(UIComponent component, String propName, ValueExpression value)
    {
        UIComponentELTagUtils.setStringProperty(component, propName, value);
    }

    @Deprecated
    protected void setStringProperty(UIComponent component, String propName, String value)
    {
        UIComponentTagUtils.setStringProperty(getFacesContext(), component, propName, value);
    }
    
    protected void setBooleanProperty(UIComponent component, String propName, ValueExpression value)
    {
        UIComponentELTagUtils.setBooleanProperty(component, propName, value);
    }
    
    protected void setBooleanProperty(UIComponent component, String propName, ValueExpression value, Boolean defaultValue)
    {
        UIComponentELTagUtils.setBooleanProperty(component, propName, value, defaultValue);
    }

    @Deprecated
    protected void setBooleanProperty(UIComponent component, String propName, String value)
    {
        UIComponentTagUtils.setBooleanProperty(getFacesContext(), component, propName, value);
    }
    
    private void setValueProperty(UIComponent component, ValueExpression value)
    {
        UIComponentELTagUtils.setValueProperty(getFacesContext(), component, value);
    }

    @Deprecated
    protected void setValueProperty(UIComponent component, String value)
    {
        UIComponentTagUtils.setValueProperty(getFacesContext(), component, value);
    }
    
    private void setConverterProperty(UIComponent component, ValueExpression value)
    {
        UIComponentELTagUtils.setConverterProperty(getFacesContext(), component, value);
    }


    @Deprecated
    private void setConverterProperty(UIComponent component, String value)
    {
        UIComponentTagUtils.setConverterProperty(getFacesContext(), component, value);
    }
    
    protected void addValidatorProperty(UIComponent component, MethodExpression value)
    {
        UIComponentELTagUtils.addValidatorProperty(getFacesContext(), component, value);
    }

    @Deprecated
    protected void setValidatorProperty(UIComponent component, String value)
    {
        UIComponentTagUtils.setValidatorProperty(getFacesContext(), component, value);
    }
    
    protected void setActionProperty(UIComponent component, MethodExpression action)
    {
        UIComponentELTagUtils.setActionProperty(getFacesContext(), component, action);
    }

    @Deprecated
    protected void setActionProperty(UIComponent component, String action)
    {
        UIComponentTagUtils.setActionProperty(getFacesContext(), component, action);
    }
    
    protected void setActionListenerProperty(UIComponent component, MethodExpression actionListener)
    {
        UIComponentELTagUtils.addActionListenerProperty(getFacesContext(), component, actionListener);
    }

    @Deprecated
    protected void setActionListenerProperty(UIComponent component, String actionListener)
    {
        UIComponentTagUtils.setActionListenerProperty(getFacesContext(), component, actionListener);
    }
    
    protected void addValueChangedListenerProperty(UIComponent component, MethodExpression valueChangedListener)
    {
        UIComponentELTagUtils.addValueChangedListenerProperty(getFacesContext(), component, valueChangedListener);
    }

    @Deprecated
    protected void setValueChangedListenerProperty(UIComponent component, String valueChangedListener)
    {
        UIComponentTagUtils.setValueChangedListenerProperty(getFacesContext(), component, valueChangedListener);
    }

    protected void setValueBinding(UIComponent component,
            String propName,
            ValueExpression value)
    {
        UIComponentELTagUtils.setValueBinding(getFacesContext(), component, propName, value);
    }

}
