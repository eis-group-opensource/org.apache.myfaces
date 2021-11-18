/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.shared.util.serial;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.apache.myfaces.shared.util.MyFacesObjectInputStream;

/**
 * @author Dennis C. Byrne
 */

public class DefaultSerialFactory implements SerialFactory
{

    public ObjectOutputStream getObjectOutputStream(OutputStream outputStream) throws IOException
    {
        return new ObjectOutputStream(outputStream);
    }

    public ObjectInputStream getObjectInputStream(InputStream inputStream) throws IOException
    {
        return new MyFacesObjectInputStream(inputStream);
    }
    
}
