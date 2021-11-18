/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * This tag associates a single SelectItem with the nearest
 * parent UIComponent. The item represents a single option
 * for a component such as an h:selectBooleanCheckbox or h:selectOneMenu.
 * See also component selectItems.
 * <p>
 * Unless otherwise specified, all attributes accept static values 
 * or EL expressions.
 * </p>
 * <p>
 * UISelectItem should be nestetd inside a UISelectMany or UISelectOne component,
 * and results in  the addition of a SelectItem instance to the list of available options
 * for the parent component
 * </p>
 */
@JSFComponent
(clazz = "javax.faces.component.UISelectItem",template=true,
 name = "f:selectItem",
 tagClass = "org.apache.myfaces.taglib.core.SelectItemTag",
 bodyContent = "empty")
abstract class _UISelectItem extends UIComponentBase
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.SelectItem";
  static public final String COMPONENT_TYPE =
    "javax.faces.SelectItem";

  /**
   * Disable this property; although this class extends a base-class that
   * defines a read/write rendered property, this particular subclass
   * does not support setting it. Yes, this is broken OO design: direct
   * all complaints to the JSF spec group.
   */
  @JSFProperty(tagExcluded=true)
  public void setRendered(boolean state) {
      super.setRendered(state);
      //call parent method due TCK problems
      //throw new UnsupportedOperationException();
  }
  
  /**
   * The initial value of this component.
   *
   * @return  the new value value
   */
  @JSFProperty
  public abstract Object getValue();

  /**
   * Determine whether this item can be chosen by the user.
   * When true, this item cannot be chosen by the user. If this method is
   * ever called, then any EL-binding for the disabled property will be ignored.
   *
   * @return  the new itemDisabled value
   */
  @JSFProperty(defaultValue="false")
  public abstract boolean isItemDisabled();

  /**
   * The escape setting for the label of this selection item.
   *
   * @return  the new itemEscaped value
   */
  @JSFProperty(defaultValue="false", jspName="escape")
  public abstract boolean isItemEscaped();

  /**
   * For use in development tools.
   *
   * @return  the new itemDescription value
   */
  @JSFProperty
  public abstract String getItemDescription();

  /**
   * The string which will be presented to the user for this option.
   *
   * @return  the new itemLabel value
   */
  @JSFProperty
  public abstract String getItemLabel();

  /**
   * The value for this Item.
   *
   * @return  the new itemValue value
   */
  @JSFProperty
  public abstract Object getItemValue();

}
