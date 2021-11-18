/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UIOutput;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 * Renders the value of the associated UIOutput component.
 * 
 * If this element has an ID or CSS style properties, the text is wrapped in a span element.
 */
@JSFComponent
(name = "h:outputText",
clazz = "javax.faces.component.html.HtmlOutputText",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlOutputTextTag",
defaultRendererType = "javax.faces.Text"
)
abstract class _HtmlOutputText extends UIOutput implements _EscapeProperty, 
_StyleProperties, _UniversalProperties
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Output";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlOutputText";

}
