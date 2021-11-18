/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.sortheader;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.component.UserRoleUtils;
import org.apache.myfaces.component.html.ext.HtmlDataTable;
import org.apache.myfaces.shared_tomahawk.renderkit.RendererUtils;
import org.apache.myfaces.renderkit.html.ext.HtmlLinkRenderer;

/**
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 659874 $ $Date: 2008-05-24 23:59:15 +0300 (Sat, 24 May 2008) $
 * $Log: HtmlSortHeaderRenderer.java,v $
 * Revision 1.6  2004/10/13 11:50:58  matze
 * renamed packages to org.apache
 *
 * Revision 1.5  2004/07/01 21:53:10  mwessendorf
 * ASF switch
 *
 * Revision 1.4  2004/06/04 12:10:35  royalts
 * added check on isArrow
 *
 * Revision 1.3  2004/05/18 14:31:38  manolito
 * user role support completely moved to components source tree
 *
 * Revision 1.2  2004/04/22 09:20:55  manolito
 * derive from HtmlLinkRendererBase instead of HtmlLinkRenderer
 *
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Command"
 *   type = "org.apache.myfaces.SortHeader"
 *
 */
public class HtmlSortHeaderRenderer
        extends HtmlLinkRenderer
{
    private static final String FACET_ASCENDING = "ascending";
    private static final String FACET_DESCENDING = "descending";

    //private static final Log log = LogFactory.getLog(HtmlSortHeaderRenderer.class);

    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException
    {
        RendererUtils.checkParamValidity(facesContext, component, HtmlCommandSortHeader.class);
        if (UserRoleUtils.isEnabledOnUserRole(component))
        {
            HtmlCommandSortHeader sortHeader = (HtmlCommandSortHeader)component;
            HtmlDataTable dataTable = sortHeader.findParentDataTable();
            
            if (sortHeader.getColumnName().equals(dataTable.getSortColumn()))
            {
                UIComponent img = (dataTable.isSortAscending())
                                  ? sortHeader.getFacet(FACET_ASCENDING)
                                  : sortHeader.getFacet(FACET_DESCENDING);
                // render directional image
                if (img != null)
                {
                    RendererUtils.renderChild(facesContext, img);
                }
                // render directional character
                if (sortHeader.isArrow())
                {
                    ResponseWriter writer = facesContext.getResponseWriter();
                    writer.write((dataTable.isSortAscending()) ? "&#x2191;" : "&#x2193;");
                }
            }            
        }
        super.encodeEnd(facesContext, component);
    }

}
