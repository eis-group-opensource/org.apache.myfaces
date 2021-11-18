/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UIInput;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * Renders as an HTML input tag with its type set to "password".
 *
 */
@JSFComponent
(name = "h:inputSecret",
clazz = "javax.faces.component.html.HtmlInputSecret",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlInputSecretTag",
defaultRendererType = "javax.faces.Secret"
)
abstract class _HtmlInputSecret extends UIInput implements _AccesskeyProperty,
    _AltProperty, _UniversalProperties, _FocusBlurProperties, _EventProperties,
    _StyleProperties, _TabindexProperty, _ChangeSelectProperties, 
    _DisabledReadonlyProperties, _LabelProperty
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Input";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlInputSecret";

  /**
   * HTML: The maximum number of characters allowed to be entered.
   * 
   * @JSFProperty
   *   defaultValue = "Integer.MIN_VALUE"
   */
  public abstract int getMaxlength();
  
  /**
   * If true, the value will be re-sent (in plaintext) when the form
   * is rerendered (see JSF.7.4.4). Default is false.
   * 
   * @JSFProperty
   *   defaultValue = "false"
   */
  public abstract boolean isRedisplay();
  
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
   * @since 1.2
   */
  @JSFProperty
  public abstract String getAutocomplete();
  
}
