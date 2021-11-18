/* Copyright © 2016 EIS Group and/or one of its affiliates. All rights reserved. Unpublished work under U.S. copyright laws.
 CONFIDENTIAL AND TRADE SECRET INFORMATION. No portion of this work may be copied, distributed, modified, or incorporated into any other media without EIS Group prior written consent.*/
package javax.faces.webapp;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * This tag adds its child as a facet of the nearest parent UIComponent.
 * A child consisting of multiple elements should be nested within a
 * container component (i.e., within an h:panelGroup for HTML library
 * components).
 * 
 * Unless otherwise specified, all attributes accept static values or EL expressions.
 * 
 * see Javadoc of <a href="http://java.sun.com/javaee/javaserverfaces/1.2/docs/api/index.html">JSF Specification</a>
 *
 * @JSFJspTag
 *   name="f:facet"
 *   bodyContent="JSP"
 *   
 * @author Manfred Geiler (latest modification by $Author: lu4242 $)
 * @version $Revision: 693358 $ $Date: 2008-09-09 06:54:29 +0300 (Tue, 09 Sep 2008) $
 */
public class FacetTag
        extends TagSupport
{
    private static final long serialVersionUID = -5254277925259361302L;
    private String _name;

    public String getName()
    {
        return _name;
    }

    /**
     * The name of the facet to be created.  This must be a static value.
     * 
     * @JSFJspAttribute
     *   required="true"
     */
    public void setName(String name)
    {
        _name = name;
    }

    public void release()
    {
        super.release();
        _name = null;
    }

    public int doStartTag()
            throws JspException
    {
        return EVAL_BODY_INCLUDE;
    }

}
