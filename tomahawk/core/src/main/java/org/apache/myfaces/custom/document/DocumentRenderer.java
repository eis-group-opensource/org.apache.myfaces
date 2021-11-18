/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.document;

/**
 * Document to enclose the whole document. If not otherwise possible you can use
 * state="start|end" to demarkate the document boundaries
 * 
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.Data"
 *   type = "org.apache.myfaces.Document"
 * 
 * @author Mario Ivankovits (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public class DocumentRenderer extends AbstractDocumentRenderer
{
    public static final String RENDERER_TYPE = "org.apache.myfaces.Document";

    protected String getHtmlTag()
    {
        return "html";
    }

    protected Class getDocumentClass()
    {
        return Document.class;
    }
}