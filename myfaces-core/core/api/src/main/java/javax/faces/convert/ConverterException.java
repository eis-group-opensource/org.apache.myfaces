/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.convert;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public class ConverterException
        extends FacesException
{
    private static final long serialVersionUID = 8668056061177480197L;
    // FIELDS
    private FacesMessage _facesMessage;

    // CONSTRUCTORS
    public ConverterException()
    {
        super();
    }

    public ConverterException(FacesMessage facesMessage)
    {
        super(facesMessage.getSummary());
        _facesMessage = facesMessage;
    }

    public ConverterException(FacesMessage facesMessage, Throwable cause)
    {
        super(cause);
        _facesMessage = facesMessage;
    }

    public ConverterException(String message)
    {
        super(message);
    }

    public ConverterException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ConverterException(Throwable cause)
    {
        super(cause);
    }

    // METHODS
    public FacesMessage getFacesMessage()
    {
        return _facesMessage;
    }

}
