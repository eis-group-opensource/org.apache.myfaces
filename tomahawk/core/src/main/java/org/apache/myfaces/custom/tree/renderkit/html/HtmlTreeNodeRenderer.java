/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.tree.renderkit.html;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.tree.HtmlTreeNode;
import org.apache.myfaces.renderkit.html.jsf.ExtendedHtmlLinkRenderer;
import org.apache.myfaces.renderkit.html.util.DummyFormUtils;
import org.apache.myfaces.shared_tomahawk.renderkit.html.HtmlRendererUtils;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Command"
 *   type = "org.apache.myfaces.HtmlTreeNode"
 * 
 * @author <a href="mailto:oliver@rossmueller.com">Oliver Rossmueller</a>
 * @version $Revision: 659874 $ $Date: 2008-05-24 23:59:15 +0300 (Sat, 24 May 2008) $
 */
public class HtmlTreeNodeRenderer
    extends ExtendedHtmlLinkRenderer {

    public void decode(FacesContext facesContext, UIComponent component) {
        super.decode(facesContext, component);
        String clientId = component.getClientId(facesContext);
        String reqValue = (String) facesContext
            .getExternalContext()
            .getRequestParameterMap()
            .get(HtmlRendererUtils
                .getHiddenCommandLinkFieldName(DummyFormUtils
                .findNestingForm(component, facesContext)));
        if (reqValue != null && reqValue.equals(clientId)) {
            HtmlTreeNode node = (HtmlTreeNode) component;

            node.setSelected(true);
        }
    }
}
