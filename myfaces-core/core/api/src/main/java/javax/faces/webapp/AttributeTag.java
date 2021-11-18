/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.webapp;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 * @deprecated the implementation of this clazz is now an implementation detail.
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public class AttributeTag
        extends TagSupport
{
    private static final long serialVersionUID = 3147657100171678632L;
    private String _name;
    private String _value;

    /**
     * @deprecated
     * @param name
     */
    public void setName(String name)
    {
        _name = name;
    }

    /**
     * @deprecated
     * @param value
     */
    public void setValue(String value)
    {
        _value = value;
    }

    /**
     * @deprecated
     */
    public int doStartTag() throws JspException
    {
        UIComponentTag componentTag = UIComponentTag.getParentUIComponentTag(pageContext);
        if (componentTag == null)
        {
            throw new JspException("no parent UIComponentTag found");
        }
        UIComponent component = componentTag.getComponentInstance();
        if (component == null)
        {
            throw new JspException("parent UIComponentTag has no UIComponent");
        }
        String name = getName();
        if (component.getAttributes().get(name) == null)
        {
            Object value = getValue();

            if(value != null)
                component.getAttributes().put(name, value);
        }
        return Tag.SKIP_BODY;
    }

    /**
     * @deprecated
     */
    public void release()
    {
        super.release();
        _name = null;
        _value = null;
    }


    private String getName()
    {
        if (UIComponentTag.isValueReference(_name))
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ValueBinding vb = facesContext.getApplication().createValueBinding(_name);
            return (String)vb.getValue(facesContext);
        }
        
        return _name;
    }

    private Object getValue()
    {
        if (UIComponentTag.isValueReference(_value))
        {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ValueBinding vb = facesContext.getApplication().createValueBinding(_value);
            return vb.getValue(facesContext);
        }
        
        return _value;
    }

}
