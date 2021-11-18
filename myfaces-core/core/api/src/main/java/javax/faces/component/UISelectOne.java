/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import javax.faces.context.FacesContext;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 * Component for choosing one option out of a set of possibilities.
 * <p>
 * This component is expected to have children of type UISelectItem or UISelectItems; these define
 * the set of possible options that the user can choose from.
 * </p>
 * <p>
 * See the javadoc for this class in the
 * <a href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/api/index.html">JSF Specification</a>
 * for further details.
 * </p>
 */
@JSFComponent(defaultRendererType = "javax.faces.Menu")
public class UISelectOne extends UIInput
{
    public static final String COMPONENT_TYPE = "javax.faces.SelectOne";
    public static final String COMPONENT_FAMILY = "javax.faces.SelectOne";

    public static final String INVALID_MESSAGE_ID = "javax.faces.component.UISelectOne.INVALID";

    public UISelectOne()
    {
        setRendererType("javax.faces.Menu");
    }

    @Override
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    /**
     * Verify that the result of converting the newly submitted value is <i>equal</i> to the value
     * property of one of the child SelectItem objects. If this is not true, a validation error is
     * reported.
     * 
     * @see javax.faces.component.UIInput#validateValue(javax.faces.context.FacesContext,java.lang.Object)
     */
    protected void validateValue(FacesContext context, Object value)
    {
        super.validateValue(context, value);

        if (!isValid() || value == null)
        {
            return;
        }

        _SelectItemsUtil._ValueConverter converter = new _SelectItemsUtil._ValueConverter()
        {
            public Object getConvertedValue(FacesContext context, String value)
            {
                return UISelectOne.this.getConvertedValue(context, value);
            }
        };

        // selected value must match to one of the available options
        if (!_SelectItemsUtil.matchValue(context, value, new _SelectItemsIterator(this), converter))
        {
            _MessageUtils.addErrorMessage(context, this, INVALID_MESSAGE_ID, new Object[]
            { _MessageUtils.getLabel(context, this) });
            setValid(false);
        }
    }
}
