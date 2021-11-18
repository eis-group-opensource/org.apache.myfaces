/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UIInput;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * Renders a HTML input element.
 *
 */
@JSFComponent
(name = "h:inputText",
clazz = "javax.faces.component.html.HtmlInputText",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlInputTextTag",
defaultRendererType = "javax.faces.Text"
)
abstract class _HtmlInputText extends UIInput
    implements _AccesskeyProperty,
    _AltProperty, _UniversalProperties, _DisabledReadonlyProperties,
    _FocusBlurProperties, _ChangeSelectProperties, _EventProperties,
    _StyleProperties, _TabindexProperty, _LabelProperty
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Input";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlInputText";

  /**
   * HTML: The maximum number of characters allowed to be entered.
   * 
   * @JSFProperty
   *   defaultValue = "Integer.MIN_VALUE"
   */
  public abstract int getMaxlength();

  /**
   * HTML: The initial width of this control, in characters.
   * 
   * @JSFProperty
   *   defaultValue = "Integer.MIN_VALUE"
   */
  public abstract int getSize();

  /**
   * If the value of this attribute is "off", render "off" as the value of the attribute.
   * This indicates that the browser should disable its autocomplete feature for this component.
   * This is useful for components that perform autocompletion and do not want the browser interfering.
   * If this attribute is not set or the value is "on", render nothing.
   *
   * @return  the new autocomplete value
   */
  @JSFProperty
  public abstract String getAutocomplete();

}
