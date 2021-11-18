/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.navmenu.jscookmenu;

import javax.faces.component.UICommand;

import org.apache.myfaces.component.LibraryLocationAware;
import org.apache.myfaces.component.LocationAware;
import org.apache.myfaces.component.UserRoleAware;
import org.apache.myfaces.component.UserRoleUtils;

/**
 * Renders a Javascript Menu. Nested NavigationMenuItem(s) are rendered 
 * as Javascript Menu. 
 *
 * <p>
 * This component is based based on the excellent
 * <a href="http://jscook.sourceforge.net/JSCookMenu">JSCookMenu</a>
 * by Heng Yuan.
 * </p>
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * @JSFComponent
 *   name = "t:jscookMenu"
 *   class = "org.apache.myfaces.custom.navmenu.jscookmenu.HtmlCommandJSCookMenu"
 *   tagClass = "org.apache.myfaces.custom.navmenu.jscookmenu.HtmlJSCookMenuTag"
 * 
 * @JSFJspProperty name = "value" tagExcluded = "true"
 * @JSFJspProperty name = "actionListener" tagExcluded = "true"
 * @JSFJspProperty name = "action" tagExcluded = "true"
 * @since 1.1.7
 * @author Thomas Spiegl (latest modification by $Author: lu4242 $)
 * @version $Revision: 691856 $ $Date: 2008-09-03 21:40:30 -0500 (03 sep 2008) $
 */
public abstract class AbstractHtmlCommandJSCookMenu
    extends UICommand
    implements UserRoleAware, LocationAware, LibraryLocationAware
{
    //private static final Log log = LogFactory.getLog(HtmlCommandJSCookMenu.class);

    public static final String COMPONENT_TYPE = "org.apache.myfaces.JSCookMenu";
    public static final String COMPONENT_FAMILY = "javax.faces.Command";
    private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.JSCookMenu";
    
    /**
     * @JSFProperty
     *   required = "true"
     */
    public abstract String getLayout();

    /**
     * @JSFProperty
     *   required = "true"
     */
    public abstract String getTheme();

    public boolean isRendered()
    {
        if (!UserRoleUtils.isVisibleOnUserRole(this)) return false;
        return super.isRendered();
    }

}
