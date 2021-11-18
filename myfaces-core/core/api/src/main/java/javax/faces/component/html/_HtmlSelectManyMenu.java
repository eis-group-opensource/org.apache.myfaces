/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UISelectMany;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 * Allow the user to select zero or more items from a set of available
 * options. This is presented as a drop-down "combo-box" which allows
 * multiple rows in the list to be selected simultaneously.
 * <p>
 * The set of available options is defined by adding child
 * f:selectItem or f:selectItems components to this component.
 * </p>
 * <p>
 * Renders as an HTML select element, with the choices made up of
 * child f:selectItem or f:selectItems elements. The multiple
 * attribute is set and the size attribute is set to 1.
 * </p>
 * <p>
 * The value attribute must be a value-binding expression to a
 * property of type List, Object array or primitive array. That
 * "collection" is expected to contain objects of the same type as
 * SelectItem.getValue() returns for the child SelectItem objects.
 * On rendering, any child whose value is in the list will be
 * selected initially. During the update phase, the property is set
 * to contain a "collection" of values for those child SelectItem
 * objects that are currently selected.
 * </p>
 *
 */
@JSFComponent
(name = "h:selectManyMenu",
clazz = "javax.faces.component.html.HtmlSelectManyMenu",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlSelectManyMenuTag",
defaultRendererType = "javax.faces.Menu"
)
abstract class _HtmlSelectManyMenu extends UISelectMany implements
_AccesskeyProperty, _UniversalProperties, _DisabledReadonlyProperties,
_FocusBlurProperties, _ChangeSelectProperties, _EventProperties,
_StyleProperties, _TabindexProperty, _DisabledClassEnabledClassProperties,
_LabelProperty
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.SelectMany";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlSelectManyMenu";

}
