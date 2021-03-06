/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.def;

/**
 * Renders an WML table element. Render the "header" and a "footer" facet if is presented at least in one column. Header and footer is rendered as the first or the last row of the table. Each nested column element represents one column. Number of rows is determined by structure of value property.
 *
 * @wapfaces.tag
 *       componentFamily="UIData"
 *       rendererType="TableRenderer"
 *       tagName="dataTable"
 *       tagBaseClass="org.apache.myfaces.wap.base.ComponentTagBase"
 *       bodyContent="JSP"
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */


public class DataTable extends javax.faces.component.UIData {

/**
     * Aligns the text in a column. Specify a list of the align values, one for each column. Posible are values: "C", "L" or "R". Example: aling="RRCL" - the first column is align to right, second right, third center and the fourth column to left. Default aling is left.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String align;

    /**
     * The attribute styleClass affiliates an element with one or more classes. Multiple elements can be given the same styleClass name.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String styleClass;

    /**
     * Sets a title for the table.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String title;

    /**
     * The xml:lang attribute specifies the natural or formal language of an element or its attributes.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String xmllang;

    // ============= INHERIT ATTRIBUTES ======================================
    /**
     * Zero-relative row number of the first row to be displayed. If this property is set to zero, rendering will begin with the first row of the underlying data.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     valueBinding="true"
     */
    int first;

    /**
     * The number of rows to display, starting with the one identified by the "first" property. If this value is set to zero, all available rows in the underlying data model will be displayed.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     valueBinding="true"
     */
    int rows;

    /**
     * Name of a request-scope attribute under which the model data for the row selected by the current value of the "rowIndex" property (i.e. also the current value of the "rowData" property) will be exposed.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     valueBinding="true"
     */
    java.lang.String var;

    /**
     * The current value of this component.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     valueBinding="true"
     */
    java.lang.String value;

    // ============= ABSTARACT ATTRIBUTES ======================================
    /**
     * The component identifier for the associated component.
     *
     * @wapfaces.attribute
     *     abstract="true"
     *     inherit="true"
     */
    java.lang.String id;

    /**
     * Flag indicating whether or not this component should be rendered (during Render Response Phase), or processed on any subsequent form submit.
     *
     * @wapfaces.attribute
     *     abstract="true"
     *     inherit="true"
     */
    boolean rendered;

    /**
     * The value binding expression linking this component to a property in a backing bean.
     *
     * @wapfaces.attribute
     *     abstract="true"
     *     inherit="true"
     */
    java.lang.String binding;

}
