/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.fileupload;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;



/**
 * @author Manfred Geiler (latest modification by $Author: mmarinschek $)
 * @version $Revision: 599022 $ $Date: 2007-11-28 17:14:17 +0200 (Wed, 28 Nov 2007) $
 */
public interface UploadedFile extends Serializable
{


    /**
     * Answer the uploaded file contents.
     *
     * @return file contents
     */
    byte[] getBytes() throws IOException;


    /**
     * Answer the uploaded file contents input stream
     *
     * @throws IOException
     * @return InputStream
     */
    InputStream getInputStream() throws IOException;


    /**
     * @return Returns the contentType.
     */
    String getContentType();



    /**
     * @return Returns the name.
     */
    String getName();


    /**
     * Returns the size of this file.
     * @return long
     */
    long getSize();
    
    /**Allows to get more information/interact more with the file, depending
     * on where it is stored
     * 
     * @return StorageStrategy the storage strategy of this file, 
     */
    StorageStrategy getStorageStrategy();
}
