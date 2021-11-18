/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * This tag associates a parameter name-value pair with the nearest
 * parent UIComponent. A UIComponent is created to represent this
 * name-value pair, and stored as a child of the parent component; what
 * effect this has depends upon the renderer of that parent component.
 * <p>
 * Unless otherwise specified, all attributes accept static values
 * or EL expressions.
 * </p>
 */
@JSFComponent
(clazz = "javax.faces.component.UIParameter",template=true,
name = "f:param",
tagClass="org.apache.myfaces.taglib.core.ParamTag")
abstract class _UIParameter extends UIComponentBase
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Parameter";
  static public final String COMPONENT_TYPE =
    "javax.faces.Parameter";

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
   * The value of this component.
   *
   * @return  the new value value
   */
  @JSFProperty
  public abstract Object getValue();

  /**
   * The name under which the value is stored.
   *
   * @return  the new name value
   */
  @JSFProperty
  public abstract String getName();

}
