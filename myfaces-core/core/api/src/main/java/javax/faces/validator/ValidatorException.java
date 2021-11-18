/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.validator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @author Thomas Spiegl
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public class ValidatorException
        extends FacesException
{
    private static final long serialVersionUID = 5965885122446047949L;
    private FacesMessage _facesMessage;

    public ValidatorException(FacesMessage message)
    {
        super(facesMessageToString(message));
        _facesMessage = message;
    }

    public ValidatorException(FacesMessage message,
                              Throwable cause)
    {
        super(facesMessageToString(message), cause);
        _facesMessage = message;
    }

    public FacesMessage getFacesMessage()
    {
        return _facesMessage;

    }

    private static String facesMessageToString(FacesMessage message)
    {
        final String summary = message.getSummary();
        final String detail = message.getDetail();
        
        if (summary != null)
        {
            if (detail != null)
            {
                return summary + ": " + detail;
            }
            
            return summary;
        }
        
        return detail != null ? detail : "";
    }

}
