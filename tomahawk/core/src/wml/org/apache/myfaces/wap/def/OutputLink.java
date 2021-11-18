/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.def;

/**
 * Render an WML "a" anchor element. The value of the component is rendered as the value of the "href" attribute. Any child UIParameter components are appended to the String to be output as the value of the "href" attribute as query parameters before rendering. The entire "href" string must be passed through a call to the encodeResourceURL() method of theExternalContext. The name of the UIParameter goes on the left hand side, and the value of the UIParameter on the right hand side. The name and the value must be URLEncoded. Each UIParameter instance is separeted by an ampersand, as dictated in the URL spec. If the "styleClass" attribute is specified, render its value as the value of the "class" attribute.
 *
 * @wapfaces.tag
 *       componentFamily="UIOutput"
 *       rendererType="OutputLinkRenderer"
 *       tagName="outputLink"
 *       tagBaseClass="org.apache.myfaces.wap.base.ValueHolderTagBase"
 *       bodyContent="JSP"
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */ 


public class OutputLink extends javax.faces.component.UIOutput {

    /**
     * Defines a text identifying the link.
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

    /**
     * Space-separated list of style class(es) to be applied when this element is rendered. This value must be passed through as the "class" attribute on generated markup.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.String styleClass;


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
