/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UISelectOne;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 * Allow the user to choose one option from a set of options.
 * <p>
 * Rendered as a listbox with the MULTIPLE attribute set to false.
 * </p>
 * <p>
 * The available choices are defined via child f:selectItem or
 * f:selectItems elements. The size of the listbox defaults to the
 * number of available choices; if size is explicitly set to a
 * smaller value, then scrollbars will be rendered. If size is set
 * to 1 then a "drop-down menu" (aka "combo-box") is rendered, though
 * if this is the intent then selectOneMenu should be used instead.
 * </p>
 * <p>
 * The value attribute of this component is read to determine
 * which of the available options is initially selected; its value
 * should match the "value" property of one of the child SelectItem
 * objects.
 * </p>
 * <p>
 * On submit of the enclosing form, the value attribute's bound
 * property is updated to contain the "value" property from the
 * chosen SelectItem.
 * </p>
 *
 */
@JSFComponent
(name = "h:selectOneListbox",
clazz = "javax.faces.component.html.HtmlSelectOneListbox",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlSelectOneListboxTag",
defaultRendererType = "javax.faces.Listbox"
)
abstract class _HtmlSelectOneListbox extends UISelectOne implements
_AccesskeyProperty, _UniversalProperties, _DisabledReadonlyProperties,
_FocusBlurProperties, _ChangeSelectProperties, _EventProperties,
_StyleProperties, _TabindexProperty, _DisabledClassEnabledClassProperties,
_LabelProperty
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.SelectOne";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlSelectOneListbox";

  /**
   * see JSF Spec.
   * 
   * @JSFProperty
   *   defaultValue="Integer.MIN_VALUE"
   */
  public abstract int getSize();

}
