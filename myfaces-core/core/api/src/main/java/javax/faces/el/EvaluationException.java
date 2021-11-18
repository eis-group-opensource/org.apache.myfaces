/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.el;

import javax.faces.FacesException;

/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Thomas Spiegl (latest modification by $Author: skitching $)
 * @version $Revision: 676298 $ $Date: 2008-07-13 13:31:48 +0300 (Sun, 13 Jul 2008) $
 * @deprecated
 */
public class EvaluationException extends FacesException
{
  private static final long serialVersionUID = 4668524591042216006L;
    // FIELDS

  // CONSTRUCTORS
        /**
         * @deprecated
         */
    public EvaluationException()
    {
        super();
    }

        /**
         * @deprecated
         */
    public EvaluationException(String message)
    {
        super(message);
    }

        /**
         * @deprecated
         */
    public EvaluationException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
        /**
         * @deprecated
         */
    public EvaluationException(Throwable cause)
    {
        super(cause);
    }
}
