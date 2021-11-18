/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UIInput;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 *
 * Renders as an HTML input tag with its type set to "hidden".
 * Unless otherwise specified, all attributes accept static values
 * or EL expressions.
 *
 */
@JSFComponent
(name = "h:inputHidden",
tagClass = "org.apache.myfaces.taglib.html.HtmlInputHiddenTag",
defaultRendererType = "javax.faces.Hidden"
)
public class HtmlInputHidden extends UIInput
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Input";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlInputHidden";

  /**
   * Construct an instance of the HtmlInputHidden.
   */
  public HtmlInputHidden()
  {
    setRendererType("javax.faces.Hidden");
  }

  @Override
  public String getFamily()
  {
    return COMPONENT_FAMILY;
  }
}
