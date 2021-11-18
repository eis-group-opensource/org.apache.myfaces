/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.def;

/**
 * Render a single message for a specific component.
 *
 * Set-up for Rendering
 * Obtain the "summary" and "detail" properties fromUIMessage component. If not present, keep the empty string as the value, respectively. Obtain the firstFacesMessage to render from the component, using the "for" property of the UIMessage. This will be the only message we render.
 *
 * Rendering
 * For the message renderer, we only render one row, for the first message. For the messages renderer, we render as many rows as we have messages.
 *
 * @wapfaces.tag
 *       componentFamily="UIMessage"
 *       rendererType="MessageRenderer"
 *       tagName="message"
 *       tagBaseClass="org.apache.myfaces.wap.base.MessageTagBase"
 *       bodyContent="JSP"
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */


public class Message extends javax.faces.component.UIMessage {

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
     * Client identifier of the component for which to display messages.
     *
     * @wapfaces.attribute
     *     required="true"
     *     abstract="true"
     *     inherit="true"
     *     replaceWith="for"
     */
    java.lang.String forComponent;

     /**
     * Flag indicating whether the summary portion of displayed messages should be included. Default value is "true".
     *
     * @wapfaces.attribute
     *     initValue="true"
     *     abstract="true"
     *     inherit="true"
     */
    boolean showDetail;

     /**
     * Flag indicating whether the summary portion of displayed messages should be included. Default value is "false".
     *
     * @wapfaces.attribute
     *     initValue="false"
     *     abstract="true"
     *     inherit="true"
     */
    boolean showSummary;

}
