/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.def;

/**
 * This control can by rendered as a graphical button(PDA) or item in menu(mobile phones).
 * The final view is depend on client browser. This element must be a childern of UIForm component.
 *
 * Decode Behavior
 * Obtain the Map from the "requestParameterMap" property of the ExternalContext. If the value in theMap for
 * the value of the "clientId" property of the component is not null, get the value of the "type" attribute,
 * and convert it to lower case. If the result is equal to the String "reset" (without the quotes), return fromdecode().
 * Otherwise, create ajavax.faces.event.ActionEvent around the component, and pass it to the queueEvent() method
 * of the component, which must be an instance ofUICommand.
 *
 * Encode Behavior
 * Wml don't contain submit button tag. This element is rendered using "do" and "go" wml tags.
 * If is the button attribute type set to "reset", then is command button rendered as "do" and "setvar" tags. For reset is not needed reload the page.
 *
 * @wapfaces.tag
 *       componentFamily="UICommand"
 *       rendererType="CommandButtonRenderer"
 *       tagName="commandButton"
 *       tagBaseClass="org.apache.myfaces.wap.base.ActionSourceTagBase"
 *       bodyContent="JSP"
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */


public class CommandButton extends javax.faces.component.UICommand {

    /**
     * Defines the type of the "do" element.This attribute tells to the browser how present this element.
     * Possible values: (accept | prev | help | reset | options | delete | unknown | x-* | vnd.*). Default value is "accept".
     *
     * @wapfaces.attribute
     *     initValue="\"accept\""
     *     valueBinding="true"
     */
    java.lang.String type;

    /**
     * Defines xml:lang for the "do" tag. The xml:lang attribute specifies the natural or formal language of an element or its attributes.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    //java.lang.String xmllang;

    /**
     * Defines style class for the "do" tag. Space-separated list of style class(es) to be applied when this element is rendered. This value must be passed through as the "class" attribute on generated markup.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    //java.lang.String styleClass;

    /**
     * Defines a name for the "do" element.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    //java.lang.String name;

    /**
     * If set to true, the browser ignores this element. If set to false, the browser does not ignore this element. Default is "false".
     *
     * @wapfaces.attribute
     *     initValue="false"
     *     valueBinding="true"
     */
    boolean optional;

        /**
     * Sets the value property of the UICommand. This is most often rendered as a label.
     *
     * @wapfaces.attribute
     *     valueBinding="true"
     */
    java.lang.Object value;


    // ============= ABSTARACT ATTRIBUTES ======================================
    /**
     * MethodBinding representing the application action to invoke when this component is activated by the user. The expression must evaluate to a public method that takes no parameters, and returns a String (the logical outcome) which is passed to the NavigationHandler for this application.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     abstract="true"
     */
    java.lang.String action;

    /**
     * MethodBinding representing an action listener method that will be notified when this component is activated by the user. The expression must evaluate to a public method that takes an ActionEvent parameter, with a return type of void.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     abstract="true"
     */
    java.lang.String actionListener;

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
     * Flag indicating that this component's value must be converted and validated immediately (that is, during Apply Request Values phase), rather than waiting until Process Validations phase.
     *
     * @wapfaces.attribute
     *     inherit="true"
     *     abstract="true"
     */
    boolean immediate;

}
