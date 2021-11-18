/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UIOutput;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 * Renders a HTML a element.
 * 
 * Child f:param elements are added to the href attribute as query parameters.  Other children
 * are rendered as the link text or image.
 */
@JSFComponent
(name = "h:outputLink",
clazz = "javax.faces.component.html.HtmlOutputLink",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlOutputLinkTag",
defaultRendererType = "javax.faces.Link"
)
abstract class _HtmlOutputLink extends UIOutput implements _AccesskeyProperty,
_UniversalProperties, _FocusBlurProperties, _EventProperties, _StyleProperties,
_TabindexProperty, _LinkProperties
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Output";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlOutputLink";

}
