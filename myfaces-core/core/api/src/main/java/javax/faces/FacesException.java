/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public class FacesException
        extends RuntimeException
{
    private static final long serialVersionUID = 6592152487577416317L;

    public FacesException()
    {
        super();
    }

    public FacesException(Throwable cause)
    {
        super(cause);
    }

    public FacesException(String message)
    {
        super(message);
    }

    public FacesException(String message,
                          Throwable cause)
    {
        super(message, cause);
    }

    public Throwable getCause()
    {
        return super.getCause();
    }
}
