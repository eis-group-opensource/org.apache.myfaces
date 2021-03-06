/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package org.apache.myfaces.custom.toggle;

import javax.faces.component.html.HtmlOutputLink;

/**
 * Extends standard outputLink but links to a dynamically rendered resource (image, file, ...).
 * 
 * Should be nested within an HtmlToggleGroup component. Controls nested within
 * this component will be displayed in 'view' mode, controls outside this
 * component (within the parent HtmlToggleGroup) will be displayed in 'edit'
 * mode.
 * 
 * @JSFComponent
 *   name = "t:toggleLink"
 *   class = "org.apache.myfaces.custom.toggle.ToggleLink"
 *   tagClass = "org.apache.myfaces.custom.toggle.ToggleLinkTag"
 *   
 * @author Sharath Reddy
 */
public abstract class AbstractToggleLink extends HtmlOutputLink
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.ToggleLink";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.ToggleLink";
    private static final boolean DEFAULT_DISABLED = false;

    /**
     * The class which implements 
     * org.apache.myfaces.custom.dynamicResources.ResourceRenderer. 
     * The resource renderer is responsible for resource the image. 
     * The class must have a default constructor. 
     * Any request scoped attribute or managed bean is not available when 
     * this resource renderer is instantiated and used. 
     * The resource renderer must render the binary data for the resource 
     * by using the parameters passed by nested f:param elements and/or 
     * using session or application scoped beans.
     * 
     * @JSFProperty
     *   required="true"
     *   literalOnly = "true"
     * @return
     */
    public abstract String getFor();

    /**
     * HTML: When true, this element cannot receive focus.
     * 
     * @JSFProperty
     *   defaultValue = "false"
     * @return
     */
    public abstract boolean isDisabled();
    
    /**
     * Id of the component that will be focused when toggleLink is clicked
     * 
     * @JSFProperty
     *   literalOnly = "true"
     * @return
     */
    public abstract String getOnClickFocusId();
    
    public AbstractToggleLink()
    {
        super();
        setRendererType(AbstractToggleLink.DEFAULT_RENDERER_TYPE);
        setValue( "#" );
    }

    /**
     * If true, this component will force the use of the specified id when rendering.
     * 
     * @JSFProperty
     *   literalOnly = "true"
     *   defaultValue = "false"
     *   
     * @return
     */
    public abstract Boolean getForceId();
    
    public abstract void setForceId(Boolean forceId);
    
    /**
     *  If false, this component will not append a '[n]' suffix 
     *  (where 'n' is the row index) to components that are 
     *  contained within a "list." This value will be true by 
     *  default and the value will be ignored if the value of 
     *  forceId is false (or not specified.)
     * 
     * @JSFProperty
     *   literalOnly = "true"
     *   defaultValue = "true"
     *   
     * @return
     */
    public abstract Boolean getForceIdIndex();
    
    public abstract void setForceIdIndex(Boolean forceIdIndex);
    
    /**
     * If user is in given role, this component will be rendered 
     * normally. If not, no hyperlink is rendered but all nested 
     * tags (=body) are rendered.
     * 
     * @JSFProperty
     * @return
     */
    public abstract String getEnabledOnUserRole();
    
    public abstract void setEnabledOnUserRole(String userRole);

    /**
     *  If user is in given role, this component will be rendered 
     *  normally. If not, nothing is rendered and the body of this 
     *  tag will be skipped.
     * 
     * @JSFProperty
     * @return
     */
    public abstract String getVisibleOnUserRole();
    
    public abstract void setVisibleOnUserRole(String userRole);
    
    
}