/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.application;

/**
 * @author Mathias Broekelmann (latest modification by $Author: mbr $)
 * @version $Revision: 517404 $ $Date: 2007-03-12 23:17:24 +0200 (Mon, 12 Mar 2007) $
 */
public class InvalidViewIdException extends RuntimeException
{

    /**
     * 
     */
    public InvalidViewIdException()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public InvalidViewIdException(String message)
    {
        super(message);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public InvalidViewIdException(Throwable cause)
    {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public InvalidViewIdException(String message, Throwable cause)
    {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

}
