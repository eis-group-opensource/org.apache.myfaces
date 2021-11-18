/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.document;


/**
 * 
 * Document to enclose the whole document. If not otherwise possible you can use
 * state="start|end" to demarkate the document boundaries
 * 
 * @JSFComponent
 *   name = "t:document"
 *   tagClass = "org.apache.myfaces.custom.document.DocumentTag"
 * 
 * @author Mario Ivankovits (latest modification by $Author: skitching $)
 * @version $Revision: 673833 $ $Date: 2008-07-04 00:58:05 +0300 (Fri, 04 Jul 2008) $
 */
public class Document extends AbstractDocument
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.Document";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.Document";

    public Document()
    {
        super(DEFAULT_RENDERER_TYPE);
    }
}