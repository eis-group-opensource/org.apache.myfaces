/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.component.html;

import javax.faces.component.UIData;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * This component renders an HTML table element.
 * <p>
 * This component may have nested facets with names "header" and "footer"
 * to specify header and footer rows.
 * </p>
 * <p>
 * The non-facet children of this component are expected to be
 * h:column components which describe the columns of the table.
 * </p>
 */
@JSFComponent
(name = "h:dataTable",
clazz = "javax.faces.component.html.HtmlDataTable",template=true,
tagClass = "org.apache.myfaces.taglib.html.HtmlDataTableTag",
defaultRendererType = "javax.faces.Table"
)
abstract class _HtmlDataTable extends UIData
    implements _EventProperties, _StyleProperties, _UniversalProperties 
{

  static public final String COMPONENT_FAMILY =
    "javax.faces.Data";
  static public final String COMPONENT_TYPE =
    "javax.faces.HtmlDataTable";

  /**
   * HTML: The background color of this element.
   * 
   */
  @JSFProperty
  public abstract String getBgcolor();

  /**
   * HTML: Specifies the width of the border of this element, in pixels.  Deprecated in HTML 4.01.
   * 
   */
  @JSFProperty(defaultValue="Integer.MIN_VALUE")
  public abstract int getBorder();

  /**
   * HTML: Specifies the amount of empty space between the cell border and
   * its contents.  It can be either a pixel length or a percentage.
   * 
   */
  @JSFProperty
  public abstract String getCellpadding();

  /**
   * HTML: Specifies the amount of space between the cells of the table.
   * It can be either a pixel length or a percentage of available 
   * space.
   * 
   */
  @JSFProperty
  public abstract String getCellspacing();

  /**
   * A comma separated list of CSS class names to apply to td elements in
   * each column.
   * 
   */
  @JSFProperty
  public abstract String getColumnClasses();

  /**
   * The CSS class to be applied to footer cells.
   * 
   */
  @JSFProperty
  public abstract String getFooterClass();

  /**
   * HTML: Controls what part of the frame that surrounds a table is 
   * visible.  Values include:  void, above, below, hsides, lhs, 
   * rhs, vsides, box, and border.
   * 
   */
  @JSFProperty
  public abstract String getFrame();

  /**
   * The CSS class to be applied to header cells.
   * 
   */
  @JSFProperty
  public abstract String getHeaderClass();

  /**
   * A comma separated list of CSS class names to apply to td elements in
   * each row.
   * 
   */
  @JSFProperty
  public abstract String getRowClasses();

  /**
   * HTML: Controls how rules are rendered between cells.  Values include:
   * none, groups, rows, cols, and all.
   * 
   */
  @JSFProperty
  public abstract String getRules();

  /**
   * HTML: Provides a summary of the contents of the table, for
   * accessibility purposes.
   * 
   */
  @JSFProperty
  public abstract String getSummary();

  /**
   * HTML: Specifies the desired width of the table, as a pixel length or
   * a percentage of available space.
   * 
   */
  @JSFProperty
  public abstract String getWidth();
  
  /**
   * A comma separated list of CSS class names to apply to all captions.
   * If there are less classes than the number of rows, apply the same
   * sequence of classes to the remaining captions, so the pattern is repeated.
   * More than one class can be applied to a row by separating the classes
   * with a space.
   *
   * @return  the new captionClass value
   */
  @JSFProperty
  public abstract String getCaptionClass();
  
  /**
   * Gets The CSS class to be applied to the Caption.
   *
   * @return  the new captionStyle value
   */
  @JSFProperty
  public abstract String getCaptionStyle();

}
