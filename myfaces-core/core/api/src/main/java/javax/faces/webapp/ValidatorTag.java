/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.webapp;

import javax.faces.application.Application;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 *
 * @deprecated replaced by {@link ValidatorELTag}
 */
public class ValidatorTag
        extends TagSupport
{
    private static final long serialVersionUID = 8794036166323016663L;
    private String _validatorId;
    private String _binding;

    public void setValidatorId(String validatorId)
    {
        _validatorId = validatorId;
    }

    public int doStartTag()
            throws javax.servlet.jsp.JspException
    {
        UIComponentTag componentTag = UIComponentTag.getParentUIComponentTag(pageContext);
        if (componentTag == null)
        {
            throw new JspException("no parent UIComponentTag found");
        }
        if (!componentTag.getCreated())
        {
            return Tag.SKIP_BODY;
        }

        Validator validator = createValidator();

        UIComponent component = componentTag.getComponentInstance();
        if (component == null)
        {
            throw new JspException("parent UIComponentTag has no UIComponent");
        }
        if (!(component instanceof EditableValueHolder))
        {
            throw new JspException("UIComponent is no ValueHolder");
        }
        ((EditableValueHolder)component).addValidator(validator);

        return Tag.SKIP_BODY;
    }

    public void release()
    {
        super.release();
        _validatorId = null;
        _binding = null;
    }

    protected Validator createValidator()
            throws JspException
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        
        if(_binding != null) {
            ValueBinding vb = application.createValueBinding(_binding);
            if(vb != null) {
                Validator validator = (Validator) vb.getValue(facesContext);
                if(validator != null) {
                    return validator;
                }
            }
        }
        
        if (UIComponentTag.isValueReference(_validatorId))
        {
            ValueBinding vb = facesContext.getApplication().createValueBinding(_validatorId);
            return application.createValidator((String)vb.getValue(facesContext));
        }

        return application.createValidator(_validatorId);
        
    }
    
    /**
     * 
     * @param binding
     * @throws javax.servlet.jsp.JspException
     * 
     * @deprecated
     */
    public void setBinding(java.lang.String binding)
            throws javax.servlet.jsp.JspException
    {
        if (binding != null && !UIComponentTag.isValueReference(binding))
        {
            throw new IllegalArgumentException("not a valid binding: " + binding);
        }
        _binding = binding;
    }
}
