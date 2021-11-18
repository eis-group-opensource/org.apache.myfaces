/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 *
 * UIMessage is the base component class for components
 * that display a multiple messages on behalf of a component.
 */
@JSFComponent
(clazz = "javax.faces.component.UIMessages",template=true,
defaultRendererType = "javax.faces.Messages"
)
abstract class _UIMessages extends UIComponentBase
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Messages";
  static public final String COMPONENT_TYPE =
    "javax.faces.Messages";

  /**
   * Specifies whether only messages (FacesMessage objects) not associated with a
   * specific component should be displayed, ie whether messages should be ignored
   * if they are attached to a particular component. Defaults to false.
   *
   * @return  the new globalOnly value
   */
  @JSFProperty
  (defaultValue = "false")
  public abstract boolean isGlobalOnly();

  /**
   * Specifies whether the detailed information from the message should be shown. 
   * Default to false.
   *
   * @return  the new showDetail value
   */
  @JSFProperty
  (defaultValue = "false")
  public abstract boolean isShowDetail();

  /**
   * Specifies whether the summary information from the message should be shown.
   * Defaults to true.
   *
   * @return  the new showSummary value
   */
  @JSFProperty
  (defaultValue = "true")
  public abstract boolean isShowSummary();
  
}
