/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UIInput;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 * Renders a HTML textarea element.
 *
 */
@JSFComponent
(name = "h:inputTextarea",
clazz = "javax.faces.component.html.HtmlInputTextarea",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlInputTextareaTag",
defaultRendererType = "javax.faces.Textarea"
)
abstract class _HtmlInputTextarea extends UIInput implements _AccesskeyProperty,
    _UniversalProperties, _FocusBlurProperties, _ChangeSelectProperties,
    _EventProperties, _StyleProperties, _TabindexProperty, 
    _DisabledReadonlyProperties, _LabelProperty
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Input";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlInputTextarea";

  /**
   * HTML: The width of this element, in characters.
   * 
   * @JSFProperty
   *   defaultValue = "Integer.MIN_VALUE"
   */
  public abstract int getCols();
  
  /**
   * HTML: The height of this element, in characters.
   * 
   * @JSFProperty
   *   defaultValue = "Integer.MIN_VALUE"
   */
  public abstract int getRows();

}
