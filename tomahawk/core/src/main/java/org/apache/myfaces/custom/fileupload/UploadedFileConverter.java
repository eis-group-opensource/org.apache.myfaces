/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.fileupload;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * DOCUMENT ME!
 * @author Manfred Geiler (latest modification by $Author: grantsmith $)
 * @version $Revision: 472638 $ $Date: 2006-11-08 22:54:13 +0200 (Wed, 08 Nov 2006) $
 */
public class UploadedFileConverter
    implements Converter
{
    public Object getAsObject(FacesContext facescontext, UIComponent uicomponent, String s)
        throws ConverterException
    {
        return null;
    }

    public String getAsString(FacesContext facescontext, UIComponent uicomponent, Object obj)
        throws ConverterException
    {
        if (obj instanceof UploadedFile)
        {
            return ((UploadedFile)obj).getName();
        }
        else
        {
            return null;
        }
    }
}
