/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UICommand;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * This tag renders as an HTML a element.
 *
 */
@JSFComponent
(name = "h:commandLink",
clazz = "javax.faces.component.html.HtmlCommandLink",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlCommandLinkTag",
defaultRendererType = "javax.faces.Link"
)
abstract class _HtmlCommandLink extends UICommand
    implements _EventProperties, _UniversalProperties, _StyleProperties,
    _FocusBlurProperties, _AccesskeyProperty, _TabindexProperty,
    _LinkProperties
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Command";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlCommandLink";

  /**
   * When true, this element cannot receive focus.
   *
   * @return  the new disabled value
   */
  @JSFProperty
  (defaultValue = "false")
  public abstract boolean isDisabled();

}
