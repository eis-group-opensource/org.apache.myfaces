/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.fileupload;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;


/**
 * @author Sylvain Vieujot (latest modification by $Author: lu4242 $)
 * @version $Revision: 782291 $ $Date: 2009-06-06 21:03:52 +0300 (Sat, 06 Jun 2009) $
 */
public class UploadedFileDefaultMemoryImpl extends UploadedFileDefaultImplBase
{
    private static final long serialVersionUID = -6006333070975059090L;
    private byte[] bytes;
    private StorageStrategy storageStrategy;
    private transient FileItem fileItem = null;

    public UploadedFileDefaultMemoryImpl(final FileItem fileItem) throws IOException
    {
        super(fileItem.getName(), fileItem.getContentType());
        int sizeInBytes = (int)fileItem.getSize();
        bytes = new byte[sizeInBytes];
        this.fileItem = fileItem;
        fileItem.getInputStream().read(bytes);
        this.storageStrategy = new DefaultMemoryStorageStrategy();
    }
    
    private class DefaultMemoryStorageStrategy 
        extends StorageStrategy implements Serializable
    {
        private static final long serialVersionUID = 3610866246514636068L;

        public void deleteFileContents()
        {
          // UploadedFileDefaultMemoryImpl.this.fileItem becomes null 
          // when the parent class is serialized and deserialized.
          // In this case, the instance contained by the original
          // object is garbage collected, so we don't have to 
          // worry about it.
          if (UploadedFileDefaultMemoryImpl.this.fileItem != null)
          {
              UploadedFileDefaultMemoryImpl.this.fileItem.delete();
          }
          bytes = null;
        }
    }

    /**
     * Answer the uploaded file contents.
     *
     * @return file contents
     */
    public byte[] getBytes()
    {
        return bytes;
    }


    /**
     * Answer the uploaded file contents input stream
     *
     * @return InputStream
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException
    {
        return new ByteArrayInputStream( bytes );
    }

    /**
     * Answer the size of this file.
     * @return long
     */
    public long getSize() {
        if( bytes == null )
            return 0;
        return bytes.length;
    }

    public StorageStrategy getStorageStrategy() {
      return storageStrategy;
    }    
}
