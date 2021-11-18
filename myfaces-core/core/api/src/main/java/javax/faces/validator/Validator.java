/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.util.EventListener;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @author Thomas Spiegl
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public interface Validator
        extends EventListener
{
    /**
     * @deprecated
     */
    public static final String NOT_IN_RANGE_MESSAGE_ID = "javax.faces.validator.NOT_IN_RANGE";

    public void validate(FacesContext context,
                         UIComponent component,
                         Object value)
            throws ValidatorException;
}
