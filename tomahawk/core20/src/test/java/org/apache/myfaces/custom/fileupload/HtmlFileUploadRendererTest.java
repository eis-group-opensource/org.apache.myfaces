/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.fileupload;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.myfaces.test.AbstractTomahawkViewControllerTestCase;
import org.apache.myfaces.test.mock.MockResponseWriter;
import org.apache.myfaces.test.utils.HtmlCheckAttributesUtil;
import org.apache.myfaces.test.utils.HtmlRenderedAttr;
import org.easymock.MockControl;

public class HtmlFileUploadRendererTest extends AbstractTomahawkViewControllerTestCase
{
    private HtmlInputFileUpload fileUpload; 

    public HtmlFileUploadRendererTest(String name)
    {
        super(name);
    }
    
    public static Test suite() 
    {
        return new TestSuite(HtmlFileUploadRendererTest.class);
    }

    
 
    public void setUp() throws Exception
    {
        super.setUp();
        fileUpload = new HtmlInputFileUpload();       
    }
    
    public void tearDown() throws Exception 
    {
        super.tearDown();
        fileUpload = null;
    }
    
    public void testHtmlPropertyPassTru() throws Exception
    {
        HtmlRenderedAttr[] attrs = HtmlCheckAttributesUtil.generateBasicAttrs();
        
        MockResponseWriter writer = (MockResponseWriter)facesContext.getResponseWriter();
        HtmlCheckAttributesUtil.checkRenderedAttributes(
                fileUpload, facesContext, writer, attrs);
        if(HtmlCheckAttributesUtil.hasFailedAttrRender(attrs)) {
            fail(HtmlCheckAttributesUtil.constructErrorMessage(attrs, writer.getWriter().toString()));
        }
    }
    
    public void testUploadedFileDefaultFileImplSerializable() throws Exception
    {
        String fieldName = "inputFile";
        String contentType = "someType";
        boolean isFormField = true;
        String fileName = "tempFile";
        int sizeThreshold = 10000;
        File repository = new File(System.getProperty("java.io.tmpdir"));
        DiskFileItem item = new DiskFileItem(fieldName, contentType, isFormField, fileName, sizeThreshold, repository);

        UploadedFileDefaultFileImpl original = new UploadedFileDefaultFileImpl(item);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(original);
        oos.close();

        byte[] serializedArray = out.toByteArray();
        InputStream in = new ByteArrayInputStream(serializedArray);
        ObjectInputStream ois = new ObjectInputStream(in);
        UploadedFileDefaultFileImpl copy = (UploadedFileDefaultFileImpl) ois.readObject();
        
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getContentType(), copy.getContentType());
        assertEquals(copy.getSize(),0);
        
        copy.getStorageStrategy().deleteFileContents();
    }
    
    public void testUploadedFileDefaultMemoryImplSerializable() throws Exception
    {
        String fieldName = "inputFile";
        String contentType = "someType";
        
        MockControl control = MockControl.createControl(FileItem.class);
        FileItem item = (FileItem) control.getMock();
        
        item.getName();
        control.setReturnValue(fieldName,1);
        item.getContentType();
        control.setReturnValue(contentType,1);
        item.getSize();
        control.setReturnValue(0,1);
        item.getInputStream();
        control.setReturnValue(new InputStream()
        {
            public int read() throws IOException
            {
                return -1;
            }
        },1);

        item.delete();
        control.setVoidCallable(1);
        
        control.replay();
        
        UploadedFileDefaultMemoryImpl original = new UploadedFileDefaultMemoryImpl(item);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(original);
        oos.close();

        byte[] serializedArray = out.toByteArray();
        InputStream in = new ByteArrayInputStream(serializedArray);
        ObjectInputStream ois = new ObjectInputStream(in);
        UploadedFileDefaultMemoryImpl copy = (UploadedFileDefaultMemoryImpl) ois.readObject();
        
        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getContentType(), copy.getContentType());
        assertEquals(copy.getSize(),0);
        
        copy.getStorageStrategy().deleteFileContents();
    }

}
