/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 */
@JSFComponent
public class UIPanel extends UIComponentBase
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Panel";
  static public final String COMPONENT_TYPE =
    "javax.faces.Panel";

  /**
   * Construct an instance of the UIPanel.
   */
  public UIPanel()
  {
    setRendererType(null);
  }

  @Override
  public String getFamily()
  {
    return COMPONENT_FAMILY;
  }
}
