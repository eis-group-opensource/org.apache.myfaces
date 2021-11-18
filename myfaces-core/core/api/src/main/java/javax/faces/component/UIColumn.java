/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFacet;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;
/**
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 721588 $ $Date: 2008-11-29 02:03:35 +0200 (Sat, 29 Nov 2008) $
 */
@JSFComponent
public class UIColumn
        extends UIComponentBase
{
    private static final String FOOTER_FACET_NAME = "footer";
    private static final String HEADER_FACET_NAME = "header";
    
    public void setFooter(UIComponent footer)
    {
        getFacets().put(FOOTER_FACET_NAME, footer);
    }

    @JSFFacet
    public UIComponent getFooter()
    {
        return getFacet(FOOTER_FACET_NAME);
    }

    public void setHeader(UIComponent header)
    {
        getFacets().put(HEADER_FACET_NAME, header);
    }

    @JSFFacet
    public UIComponent getHeader()
    {
        return getFacet(HEADER_FACET_NAME);
    }

    /**
     * Get a string which uniquely identifies this UIComponent within the scope of the nearest ancestor NamingContainer
     * component. The id is not necessarily unique across all components in the current view.
     */
    @JSFProperty(literalOnly=true, rtexprvalue=false)
    @Override
    public String getId()
    {
        return super.getId();
    }

    //------------------ GENERATED CODE BEGIN (do not modify!) --------------------

    public static final String COMPONENT_TYPE = "javax.faces.Column";
    public static final String COMPONENT_FAMILY = "javax.faces.Column";


    public UIColumn()
    {
    }

    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }


    //------------------ GENERATED CODE END ---------------------------------------
}
