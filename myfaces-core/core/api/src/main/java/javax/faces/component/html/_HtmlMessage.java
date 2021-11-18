/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UIMessage;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 *
 * Renders text displaying information about the first FacesMessage
 *           that is assigned to the component referenced by the "for" attribute.
 */
@JSFComponent
(name = "h:message",
clazz = "javax.faces.component.html.HtmlMessage",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlMessageTag",
defaultRendererType = "javax.faces.Message"
)
abstract class _HtmlMessage extends UIMessage implements _StyleProperties, 
    _MessageProperties, _UniversalProperties
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Message";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlMessage";

}
