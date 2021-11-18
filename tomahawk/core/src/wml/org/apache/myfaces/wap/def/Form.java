/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.wap.def;

/**
 * Represents form element.
 *
 * Decode Behavior
 * Obtain the Map from the "requestParameterMap" property of the ExternalContext. If the map contains an entry for the "clientId" of this UIForm component, call setSubmitted(true) on the form, otherwise callsetSubmitted(false) on the form.
 *
 * Encode Behavior
 * Form element is not writed to the generated wml file. All input's data are posted by commandLink or commandButton elements.
 * Encode end method assures writing state marker.
 *
 *
 * @wapfaces.tag
 *       componentFamily="UIForm"
 *       rendererType="FormRenderer"
 *       tagName="form"
 *       tagBaseClass="org.apache.myfaces.wap.base.ComponentTagBase"
 *       bodyContent="JSP"
 *
 * @author  <a href="mailto:Jiri.Zaloudek@ivancice.cz">Jiri Zaloudek</a> (latest modification by $Author: grantsmith $)
 * @version $Revision: 472719 $ $Date: 2006-11-09 02:41:54 +0200 (Thu, 09 Nov 2006) $
 */ 


public class Form extends javax.faces.component.UIForm {

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
