/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 *
 * UIMessage is the base component class for components
 * that display a single message on behalf of a component.
 */
@JSFComponent
(clazz = "javax.faces.component.UIMessage",template=true,
defaultRendererType = "javax.faces.Message"
)
abstract class _UIMessage extends UIComponentBase
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Message";
  static public final String COMPONENT_TYPE =
    "javax.faces.Message";

  /**
   * The ID of the component whose attached FacesMessage object (if present) 
   * should be diplayed by this component.
   * <p>
   * This is a required property on the component.
   * </p>
   *
   * @return  the new for value
   */
  @JSFProperty
  (required = true)
  public abstract String getFor();

  /**
   * Specifies whether the detailed information from the message should be shown. 
   * Default to true.
   *
   * @return  the new showDetail value
   */
  @JSFProperty
  (defaultValue = "true")
  public abstract boolean isShowDetail();

  /**
   * Specifies whether the summary information from the message should be shown.
   * Defaults to false.
   *
   * @return  the new showSummary value
   */
  @JSFProperty
  (defaultValue = "false")
  public abstract boolean isShowSummary();

}
