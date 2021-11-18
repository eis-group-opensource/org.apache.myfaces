/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.document;

import java.io.IOException;

import javax.faces.context.FacesContext;

import org.apache.myfaces.component.html.util.StreamingAddResource;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;

/**
 * Document to enclose the document head. If not otherwise possible you can use
 * state="start|end" to demarkate the document boundaries
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.Data"
 *   type = "org.apache.myfaces.DocumentHead"
 * 
 * @author Mario Ivankovits (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public class DocumentHeadRenderer extends AbstractDocumentRenderer
{
    public static final String RENDERER_TYPE = "org.apache.myfaces.DocumentHead";

    protected String getHtmlTag()
    {
        return "head";
    }

    protected Class getDocumentClass()
    {
        return DocumentHead.class;
    }

    protected void writeBeforeEnd(FacesContext facesContext) throws IOException
    {
        super.writeBeforeEnd(facesContext);
        
        AddResource addResource = AddResourceFactory.getInstance(facesContext);
        if (addResource instanceof StreamingAddResource)
        {
            ((StreamingAddResource) addResource).addStyleLoaderHere(facesContext, DocumentHead.class);
        }
    }
}