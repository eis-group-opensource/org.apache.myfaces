/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UIPanel;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * This element is used to group other components where the specification requires one child element.
 * 
 * If any of the HTML or CSS attributes are set, its content is rendered within a span element.
 */
@JSFComponent
(name = "h:panelGroup",
clazz = "javax.faces.component.html.HtmlPanelGroup",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlPanelGroupTag",
defaultRendererType = "javax.faces.Group"
)
abstract class _HtmlPanelGroup extends UIPanel implements _StyleProperties
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Panel";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlPanelGroup";

  /**
   * The type of layout markup to use when rendering this group. If the value is "block"
   * the renderer must produce an HTML "div" element. Otherwise HTML "span" element must be produced.
   *
   * @return  the new layout value
   */
  @JSFProperty
  public abstract String getLayout();

}
