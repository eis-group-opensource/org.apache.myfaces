/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * Tried to deploy v0.4.2 on JBoss 3.2.1 and had a classloading problem again.
 * The problem seemed to be with JspInfo, line 98. We are using an
 * ObjectInputStream Class, which then cannot find the classes to deserialize
 * the input stream.  The solution appears to be to subclass ObjectInputStream
 * (eg. CustomInputStream), and specify a different class-loading mechanism.
 *
 * @author Robert Gothan <robert@funkyjazz.net> (latest modification by $Author: schof $)
 * @version $Revision: 382015 $ $Date: 2006-03-01 08:47:11 -0500 (Wed, 01 Mar 2006) $
 */
public class MyFacesObjectInputStream
    extends ObjectInputStream
{
    public MyFacesObjectInputStream(InputStream in) throws IOException
    {
        super(in);
    }

    protected Class resolveClass(ObjectStreamClass desc)
        throws ClassNotFoundException, IOException
    {
        try
        {
            return ClassUtils.classForName(desc.getName());
        }
        catch (ClassNotFoundException e)
        {
            return super.resolveClass(desc);
        }
    }
}
