/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.renderkit.html.ext;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent;
import org.apache.myfaces.component.html.ext.HtmlPanelGroup;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HTML;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlGridRendererBase;

/**
 * X-CHECKED: tlddoc h:panelGrid 1.0 final
 *
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC"
 *   family = "javax.faces.Panel"
 *   type = "org.apache.myfaces.Grid"
 *
 * @author Martin Marinschek (latest modification by $Author: grantsmith $)
 * @version $Revision: 169655 $ $Date: 2005-05-11 18:45:06 +0200 (Wed, 11 May 2005) $
 */
public class HtmlGridRenderer
    extends HtmlGridRendererBase
{
    protected int childAttributes(FacesContext context,
            ResponseWriter writer,
            UIComponent component,
            int columnIndex)
        throws IOException
    {
        if (component instanceof HtmlPanelGroup && ((HtmlPanelGroup)component).getColspan() != HtmlPanelGroup.DEFAULT_COLSPAN) {
            int colspan = ((HtmlPanelGroup)component).getColspan();
            writer.writeAttribute(HTML.COLSPAN_ATTR, "" + colspan, null);
            columnIndex += (colspan - 1);
        }
        return columnIndex;
    }
}
