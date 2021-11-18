/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.fieldset;

import org.apache.myfaces.custom.htmlTag.HtmlTag;

/**
 * Renders an HTML Fieldset
 * 
 * @JSFComponent
 *   name = "t:fieldset"
 *   class = "org.apache.myfaces.custom.fieldset.Fieldset"
 *   tagClass = "org.apache.myfaces.custom.fieldset.FieldsetTag"
 *   
 * @JSFJspProperty name="value" tagExcluded = "true"
 * @JSFJspProperty name="converter" tagExcluded = "true"
 * 
 * @author svieujot (latest modification by $Author: cagatay $)
 * @version $Revision: 756917 $ $Date: 2009-03-21 13:26:06 +0200 (Sat, 21 Mar 2009) $
 */
public abstract class AbstractFieldset extends HtmlTag {
  public static final String COMPONENT_TYPE = "org.apache.myfaces.Fieldset";
  public static final String COMPONENT_FAMILY = "javax.faces.Output";
  private static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.FieldsetRenderer";

  /**
   * @JSFProperty
   *   tagExcluded = "true"
   */
  public Object getValue() {
    return "fieldset";
  }
  
  /**
   * The fieldset's legend.
   * 
   * @JSFProperty
   */
  public abstract String getLegend();
  
}