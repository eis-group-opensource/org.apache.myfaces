/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.updateactionlistener;

import javax.faces.application.Application;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Registers an org.apache.myfaces.custom.updateactionlistener.UpdateActionListener 
 * at the parent component (which must be an ActionSource). 
 * 
 * When the parent's action fires the specified value is evaluated, 
 * then written into the specified property. 
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions. 
 * 
 * JSF 1.2 introduces a "setPropertyActionListener" with the same functionality like this. 
 *
 * @JSFJspTag
 *   name="t:updateActionListener"
 *   bodyContent="JSP"
 *   tagHandler="org.apache.myfaces.custom.updateactionlistener.UpdateActionListenerTagHandler"
 *   
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public class UpdateActionListenerTag
        extends TagSupport
{
    private static final long serialVersionUID = -6916153064327074092L;
    //private static final Log log = LogFactory.getLog(UpdateActionListenerTag.class);
    private String _property;
    private String _value;
    private String _converter;

    public UpdateActionListenerTag()
    {
    }

    /**
     * A value-binding that specifies a property to be updated when 
     * the parent's action occurs.
     * 
     * @JSFJspAttribute
     *   required="true"
     */
    public void setProperty(String property)
    {
        _property = property;
    }

    /**
     *  A literal value or value-binding that specifies what 
     *  will be assigned to the destination specified by the 
     *  property attribute.
     * 
     * @JSFJspAttribute
     *   required="true"
     */
    public void setValue(String value)
    {
        _value = value;
    }

    /**
     * The name of a registered Converter object which will be 
     * invoked to convert the value into an appropriate datatype 
     * for assigning to the specified property. If not specified 
     * then an appropriate converter will be selected automatically.
     * 
     * @JSFJspAttribute
     */
    public void setConverter(String converter)
    {
        _converter = converter;
    }

    public int doStartTag() throws JspException
    {
        if (_property == null) throw new JspException("property attribute not set");
        if (_value == null) throw new JspException("value attribute not set");
        if (!UIComponentTag.isValueReference(_property)) throw new JspException("property attribute is no valid value reference: " + _property);

        //Find parent UIComponentTag
        UIComponentTag componentTag = UIComponentTag.getParentUIComponentTag(pageContext);
        if (componentTag == null)
        {
            throw new JspException("UpdateActionListenerTag has no UIComponentTag ancestor");
        }

        if (componentTag.getCreated())
        {
            //Component was just created, so we add the Listener
            UIComponent component = componentTag.getComponentInstance();
            if (component instanceof ActionSource)
            {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                Application application = facesContext.getApplication();
                UpdateActionListener al = new UpdateActionListener();
                al.setPropertyBinding(application.createValueBinding(_property));
                if (UIComponentTag.isValueReference(_value))
                {
                    al.setValueBinding(application.createValueBinding(_value));
                }
                else
                {
                    al.setValue(_value);
                }
                if (_converter != null)
                {
                    Converter converter = application.createConverter(_converter);
                    al.setConverter(converter);
                }
                ((ActionSource)component).addActionListener(al);
            }
            else
            {
                throw new JspException("Component " + component.getId() + " is no ActionSource");
            }
        }

        return Tag.SKIP_BODY;
    }

    public void release()
    {
        _property = null;
        _converter = null;
        _value = null;
    }

}