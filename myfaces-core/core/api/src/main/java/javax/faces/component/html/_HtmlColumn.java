/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UIColumn;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * Creates a UIComponent that represents a single column of data within a parent UIData component.
 * <p>
 * This tag is commonly used as a child of the h:dataTable tag, to represent a column of
 * data within an html table. It can be decorated with nested "header" and "footer" facets
 * which cause the output of header and footer rows.
 * </p>
 * <p>
 * The non-facet child components of this column are re-rendered on each table row
 * to generate the content of the cell. Those child components can reference the "var"
 * attribute of the containing h:dataTable to generate appropriate output for each row.
 * </p>
 */
@JSFComponent
(name = "h:column",
clazz = "javax.faces.component.html.HtmlColumn",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlColumnTag")
abstract class _HtmlColumn extends UIColumn
{

  static public final String COMPONENT_FAMILY = "javax.faces.Column";
  static public final String COMPONENT_TYPE = "javax.faces.HtmlColumn";

  /**
   * CSS class to be used for the header.
   *
   * @return  the new headerClass value
   */
  @JSFProperty
  public abstract String getHeaderClass();

  /**
   * CSS class to be used for the footer.
   *
   * @return  the new footerClass value
   */
  @JSFProperty
  public abstract String getFooterClass();

}
