/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UIMessages;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 * Renders all or some FacesMessages depending on the "for" and
 * "globalOnly" attributes.
 *
 * <ul>
 * <li>If globalOnly = true, only global messages, that have no
 * associated clientId, will be displayed.</li>
 * <li>else if there is a "for" attribute, only messages that are
 * assigned to the component referenced by the "for" attribute
 * are displayed.</li>
 * <li>else all messages are displayed.</li>
 * </ul>
 */
@JSFComponent
(name = "h:messages",
clazz = "javax.faces.component.html.HtmlMessages",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlMessagesTag",
defaultRendererType = "javax.faces.Messages"
)
abstract class _HtmlMessages extends UIMessages implements _StyleProperties, 
_MessageProperties, _UniversalProperties
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Messages";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlMessages";

  /**
   * The layout: "table" or "list". Default: list
   * 
   * @JSFProperty
   *   defaultValue = "list"
   */
  public abstract String getLayout();
  
}
