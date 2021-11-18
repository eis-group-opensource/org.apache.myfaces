/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UISelectBoolean;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 * Allow the user to choose a "true" or "false" value, presented as a
 * checkbox.
 * <p>
 * Renders as an HTML input tag with its type set to "checkbox", and
 * its name attribute set to the id. The "checked" attribute is rendered
 * if the value of this component is true.
 * </p>
 *
 */
@JSFComponent
(name = "h:selectBooleanCheckbox",
clazz = "javax.faces.component.html.HtmlSelectBooleanCheckbox",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlSelectBooleanCheckboxTag",
defaultRendererType = "javax.faces.Checkbox"
)
abstract class _HtmlSelectBooleanCheckbox extends UISelectBoolean implements
_AccesskeyProperty, _UniversalProperties, _DisabledReadonlyProperties,
_FocusBlurProperties, _ChangeSelectProperties, _EventProperties,
_StyleProperties, _TabindexProperty, _LabelProperty
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.SelectBoolean";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlSelectBooleanCheckbox";

}
