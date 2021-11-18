/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.def;

/**
 * If the "styleClass" attribute is present, render its value as the value of the "class" attribute. If the "escape" attribute is not present, or it is present and its value is "true" all angle brackets should be converted to the ampersand xx semicolon syntax when rendering the value of the "value" attribute as the value of the component. If the "escape" attribute is present and is "false" the value of the component should be rendered as text without escaping.
 *
 * @wapfaces.tag
 *       componentFamily="UIOutput"
 *       rendererType="OutputTextRenderer"
 *       tagName="outputText"
 *       tagBaseClass="org.apache.myfaces.wap.base.ValueHolderTagBase"
 *       bodyContent="JSP"
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */


public class OutputText extends javax.faces.component.UIOutput {

    /**
     * Flag indicating that characters that are sensitive in WML and XML markup must be escaped. This flag is set to "true" by default.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     *     initValue="true"
     */
    boolean escape;

    /**
     * CSS style(s) to be applied when this component is rendered.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    //java.lang.String style;

    /**
     * Space-separated list of style class(es) to be applied when this element is rendered. This value must be passed through as the "class" attribute on generated markup.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    //java.lang.String styleClass;


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

    /**
     * Converter instance registered with this component.
     *
     * @wapfaces.attribute
     *     abstract="true"
     *     inherit="true"
     */
    java.lang.String converter;

    /**
     * The current value of this component.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     abstract="true"
     */
    java.lang.Object value;
}
