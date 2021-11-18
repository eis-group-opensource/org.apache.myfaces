/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.event;

import javax.faces.FacesException;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 */
public class AbortProcessingException extends FacesException
{
  private static final long serialVersionUID = 612682812558934753L;
    // FIELDS

  // CONSTRUCTORS
    public AbortProcessingException()
    {
        super();
    }

    public AbortProcessingException(String message)
    {
        super(message);
    }

    public AbortProcessingException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public AbortProcessingException(Throwable cause)
    {
        super(cause);
    }
}
