/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * This tag associates a set of selection list items with the nearest
 * parent UIComponent. The set of SelectItem objects is retrieved via
 * a value-binding.
 * <p>
 * Unless otherwise specified, all attributes accept static values
 * or EL expressions.
 * </p>
 * <p>
 * UISelectItems should be nested inside a UISelectMany or UISelectOne component,
 * and results in  the addition of one ore more SelectItem instance to the list of available options
 * for the parent component
 * </p>
 */
@JSFComponent
(clazz = "javax.faces.component.UISelectItems",template=true,
 name = "f:selectItems",
 tagClass = "org.apache.myfaces.taglib.core.SelectItemsTag",
 bodyContent = "empty")
abstract class _UISelectItems extends UIComponentBase
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.SelectItems";
  static public final String COMPONENT_TYPE =
    "javax.faces.SelectItems";

  /**
   * Disable this property; although this class extends a base-class that
   * defines a read/write rendered property, this particular subclass
   * does not support setting it. Yes, this is broken OO design: direct
   * all complaints to the JSF spec group.
   */
  @JSFProperty(tagExcluded=true)
  @Override
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

}
