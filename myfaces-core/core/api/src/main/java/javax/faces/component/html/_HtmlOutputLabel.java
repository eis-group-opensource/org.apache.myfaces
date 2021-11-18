/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UIOutput;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 * Renders a HTML label element.
 * 
 * In addition to the JSF specification, MyFaces allows it to directly give an output text via the "value" attribute.
 */
@JSFComponent
(name = "h:outputLabel",
clazz = "javax.faces.component.html.HtmlOutputLabel",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlOutputLabelTag",
defaultRendererType = "javax.faces.Label"
)
abstract class _HtmlOutputLabel extends UIOutput implements _FocusBlurProperties,
_EventProperties, _StyleProperties, _UniversalProperties, _AccesskeyProperty,
_TabindexProperty, _EscapeProperty
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Output";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlOutputLabel";

  /**
   * The client ID of the target input element of this label.
   * 
   * @JSFProperty
   */
  public abstract String getFor();

}
