/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UISelectOne;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 * Allow the user to choose one option from a set of options.
 * <p>
 * Renders as an HTML table element, containing an input element for
 * each child f:selectItem or f:selectItems elements.  The input
 * elements are rendered as type radio.
 * </p>
 * <p>
 * The value attribute of this component is read to determine
 * which of the available options is initially selected; its value should
 * match the "value" property of one of the child SelectItem objects.
 * </p>
 * <p>
 * On submit of the enclosing form, the value attribute's bound property
 * is updated to contain the "value" property from the chosen SelectItem.
 * </p>
 *
 */
@JSFComponent
(name = "h:selectOneRadio",
clazz = "javax.faces.component.html.HtmlSelectOneRadio",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlSelectOneRadioTag",
defaultRendererType = "javax.faces.Radio"
)
abstract class _HtmlSelectOneRadio extends UISelectOne implements
_AccesskeyProperty, _UniversalProperties, _DisabledReadonlyProperties,
_FocusBlurProperties, _ChangeSelectProperties, _EventProperties,
_StyleProperties, _TabindexProperty, _DisabledClassEnabledClassProperties,
_LabelProperty
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.SelectOne";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlSelectOneRadio";

  /**
   * Width in pixels of the border to be drawn around the table containing the options list.
   * 
   * @JSFProperty
   *   defaultValue="Integer.MIN_VALUE"
   */
  public abstract int getBorder();

  /**
   * Orientation of the options list. Valid values are 
   * "pageDirection" for a vertical layout, or "lineDirection" for
   * horizontal. The default value is "lineDirection". 
   * 
   * @JSFProperty
   */
  public abstract String getLayout();

}
