/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UIOutput;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 * Renders as text, applying the child f:param values to the value attribute as a MessageFormat string.
 * 
 * If this element has an ID or CSS style properties, the text is wrapped in a span element.
 */
@JSFComponent
(name = "h:outputFormat",
clazz = "javax.faces.component.html.HtmlOutputFormat",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlOutputFormatTag",
defaultRendererType = "javax.faces.Format"
)
abstract class _HtmlOutputFormat extends UIOutput implements _StyleProperties, 
    _EscapeProperty, _TitleProperty, _UniversalProperties
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Output";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlOutputFormat";

}
