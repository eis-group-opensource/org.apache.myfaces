/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.fileupload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;


/**
 * @author Sylvain Vieujot (latest modification by $Author: lu4242 $)
 * @version $Revision: 782291 $ $Date: 2009-06-06 21:03:52 +0300 (Sat, 06 Jun 2009) $
 */
public class UploadedFileDefaultFileImpl extends UploadedFileDefaultImplBase
{
  private static final long serialVersionUID = -6401426361519246443L;
  private transient DiskFileItem fileItem = null;
  private StorageStrategy storageStrategy;

    public UploadedFileDefaultFileImpl(final FileItem fileItem) throws IOException
    {
        super(fileItem.getName(), fileItem.getContentType());
        this.fileItem = (DiskFileItem) fileItem;
        storageStrategy = new DefaultDiskStorageStrategy();
    }

    private class DefaultDiskStorageStrategy 
        extends DiskStorageStrategy implements Serializable
    {
        private static final long serialVersionUID = 5191237379179109587L;
        
        public DefaultDiskStorageStrategy()
        {
        }

        public File getTempFile()
        {
            if (UploadedFileDefaultFileImpl.this.fileItem != null)
            {
                return UploadedFileDefaultFileImpl.this.fileItem.getStoreLocation();
            }
            else
            {
                return null;
            }
        }

        public void deleteFileContents()
        {
            // UploadedFileDefaultFileImpl.this.fileItem becomes null 
            // when the parent class is serialized and deserialized.
            // In this case, the instance contained by the original
            // object is garbage collected, so we don't have to 
            // worry about it.
            if (UploadedFileDefaultFileImpl.this.fileItem != null)
            {
                UploadedFileDefaultFileImpl.this.fileItem.delete();
            }
        }
    }

    /**
     * Answer the uploaded file contents.
     *
     * @return file contents
     */
    public byte[] getBytes() throws IOException
    {
        byte[] bytes = new byte[(int)getSize()];
        if (fileItem != null) fileItem.getInputStream().read(bytes);
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
        return fileItem != null
               ? fileItem.getInputStream()
               : new ByteArrayInputStream(new byte[0]);
    }


    /**
     * Answer the size of this file.
     * @return long
     */
    public long getSize()
    {
        return fileItem != null ? fileItem.getSize() : 0;
    }


    public StorageStrategy getStorageStrategy() {
      return storageStrategy;
    }
}
