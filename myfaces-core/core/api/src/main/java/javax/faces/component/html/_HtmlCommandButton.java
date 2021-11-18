/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UICommand;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * This tag renders as an HTML input element.
 *
 */
@JSFComponent
(name = "h:commandButton",
clazz = "javax.faces.component.html.HtmlCommandButton",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlCommandButtonTag",
defaultRendererType = "javax.faces.Button"
)
abstract class _HtmlCommandButton extends UICommand
    implements _FocusBlurProperties, 
    _EventProperties, _StyleProperties, _UniversalProperties,
    _AccesskeyProperty, _TabindexProperty, _AltProperty, 
    _ChangeSelectProperties, _DisabledReadonlyProperties,
    _LabelProperty
{

    static public final String COMPONENT_FAMILY = "javax.faces.Command";
    static public final String COMPONENT_TYPE = "javax.faces.HtmlCommandButton";

    /**
     * HTML: The URL of an image that renders in place of the button.
     * 
     */
    @JSFProperty
    public abstract String getImage();

    /**
     * HTML: A hint to the user agent about the content type of the linked resource.
     * 
     * @JSFProperty
     *   defaultValue = "submit"
     */
    @JSFProperty(defaultValue = "submit")
    public abstract String getType();

}
