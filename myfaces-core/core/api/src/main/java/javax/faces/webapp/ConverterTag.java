/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.webapp;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 * @deprecated replaced by {@link ConverterELTag}
 */
public class ConverterTag
        extends TagSupport
{
    private static final long serialVersionUID = -6168345066829108081L;
    private String _converterId;
    private String _binding;

    public ConverterTag()
    {
        super();
    }

    public void setConverterId(String converterId)
    {
        _converterId = converterId;
    }

    public int doStartTag()
            throws JspException
    {

        UIComponentClassicTagBase componentTag = UIComponentClassicTagBase.getParentUIComponentClassicTagBase(pageContext);

        if (componentTag == null)
        {
            throw new JspException("no parent UIComponentTag found");
        }
        if (!componentTag.getCreated())
        {
            return Tag.SKIP_BODY;
        }

        Converter converter = createConverter();

        UIComponent component = componentTag.getComponentInstance();
        if (component == null)
        {
            throw new JspException("parent UIComponentTag has no UIComponent");
        }
        if (!(component instanceof ValueHolder))
        {
            throw new JspException("UIComponent is no ValueHolder");
        }
        ((ValueHolder)component).setConverter(converter);

        return Tag.SKIP_BODY;
    }

    public void release()
    {
        super.release();
        _converterId = null;
        _binding = null;
    }

    protected Converter createConverter()
            throws JspException
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();

        if(_binding != null) {
            ValueBinding vb = application.createValueBinding(_binding);
            if(vb != null) {
                Converter converter = (Converter) vb.getValue(facesContext);
                if(converter != null) {
                    return converter;
                }
            }
        }

        if (UIComponentTag.isValueReference(_converterId))
        {
            ValueBinding vb = facesContext.getApplication().createValueBinding(_converterId);
            return application.createConverter((String)vb.getValue(facesContext));
        }

        return application.createConverter(_converterId);

    }

    public void setBinding(String binding) throws javax.servlet.jsp.JspException
    {
        _binding = binding;
    }
}
