/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/

package javax.faces.webapp;

import java.util.logging.Logger;

import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.tagext.JspTag;

/**
 * @author Dennis Byrne
 * @since 1.2
 */

public abstract class UIComponentTagBase extends Object implements JspTag
{

    protected static Logger log = Logger.getLogger("javax.faces.webapp");
    
    /**
     * @see http://java.sun.com/javaee/5/docs/api/javax/faces/webapp/UIComponentTagBase.html#addChild(javax.faces.component.UIComponent)
     * @param child
     */

    protected abstract void addChild(UIComponent child);

    /**
     * @see http://java.sun.com/javaee/5/docs/api/javax/faces/webapp/UIComponentTagBase.html#addFacet(java.lang.String)
     * @param name
     */

    protected abstract void addFacet(String name);

    /**
     * @see http://java.sun.com/javaee/5/docs/api/javax/faces/webapp/UIComponentTagBase.html#getComponentInstance()
     * @return
     */

    public abstract UIComponent getComponentInstance();

    /**
     * Specify the "component type name" used together with the component's
     * family and the Application object to create a UIComponent instance for
     * this tag. This method is called by other methods in this class, and is
     * intended to be overridden in subclasses to specify the actual component
     * type to be created.
     *
     * @see http://java.sun.com/javaee/5/docs/api/javax/faces/webapp/UIComponentTagBase.html#getComponentType()
     * @return a registered component type name, never null.
     */

    public abstract String getComponentType();

    /**
     * @see http://java.sun.com/javaee/5/docs/api/javax/faces/webapp/UIComponentTagBase.html#getCreated()
     * @return
     */

    public abstract boolean getCreated();

    /**
     * @see http://java.sun.com/javaee/5/docs/api/javax/faces/webapp/UIComponentTagBase.html#getELContext()
     * @return
     */

    protected ELContext getELContext()
    {

        FacesContext ctx = getFacesContext();

        if (ctx == null)
            throw new NullPointerException("FacesContext ctx");

        return getFacesContext().getELContext();
    }

    /**
     * @see http://java.sun.com/javaee/5/docs/api/javax/faces/webapp/UIComponentTagBase.html#getFacesContext()
     * @return
     */

    protected abstract FacesContext getFacesContext();

    /**
     * @see http://java.sun.com/javaee/5/docs/api/javax/faces/webapp/UIComponentTagBase.html#getIndexOfNextChildTag()
     * @return
     */

    protected abstract int getIndexOfNextChildTag();

    /**
     * Specify the "renderer type name" used together with the current
     * renderKit to get a Renderer instance for the corresponding UIComponent.
     * <p>
     * A JSP tag can return null here to use the default renderer type string.
     * If non-null is returned, then the UIComponent's setRendererType method
     * will be called passing this value, and this will later affect the
     * type of renderer object returned by UIComponent.getRenderer().
     * 
     * @see http://java.sun.com/javaee/5/docs/api/javax/faces/webapp/UIComponentTagBase.html#getRendererType()
     * @return
     */
    public abstract String getRendererType();

    /**
     * @see http://java.sun.com/javaee/5/docs/api/javax/faces/webapp/UIComponentTagBase.html#setId(java.lang.String)
     * @param id
     */

    public abstract void setId(String id);
}
